package com.example.schedulerservice.scheduler;

import com.example.schedulerservice.dto.AnalyticsBuilderServiceTaskDto;
import com.example.schedulerservice.dto.VacancyImportScheduledTaskDto;
import com.example.schedulerservice.rabbitmq.Producer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TaskScheduler {
    private final Producer producer;
    private final QueriesTaskConfig queriesTaskConfig;

    public TaskScheduler(Producer producer, QueriesTaskConfig queriesTaskConfig) {
        this.producer = producer;
        this.queriesTaskConfig = queriesTaskConfig;
    }

    @Value("${vacancyImportScheduledTaskDto.pageSize}")
    private int pageSize;

    @Value("${vacancyImportScheduledTaskDto.pageNumber}")
    private int pageNumber;

    @Value("${application.schedule.delayMinutes}")
    private int scheduleDelayMinutes;

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    @PostConstruct
    public void scheduleTasks() {
        scheduleAnalyticsBuilderTask();
        scheduleVacancyImportTasks();
    }

    private void scheduleAnalyticsBuilderTask() {
        log.info("Scheduling analytics builder task");

        executor.scheduleWithFixedDelay(() -> {
            AnalyticsBuilderServiceTaskDto analyticsBuilderServiceTaskDto = new AnalyticsBuilderServiceTaskDto();
            producer.sendMessage(analyticsBuilderServiceTaskDto);
        }, 0, scheduleDelayMinutes, TimeUnit.MINUTES);
    }

    private void scheduleVacancyImportTasks() {
        log.info("Scheduling vacancy import tasks");

        List<String> queries = queriesTaskConfig.getQueries();

        executor.scheduleWithFixedDelay(() -> {
            for (String query : queries) {
                log.info("Creating tasks for importing pages with vacancies for query = " + query);

                for (int i = 0; i < pageNumber; i++) {
                    VacancyImportScheduledTaskDto vacancyImportScheduledTaskDto = new VacancyImportScheduledTaskDto(pageSize, i, query);
                    producer.sendMessage(vacancyImportScheduledTaskDto);
                }
            }
        }, 0, scheduleDelayMinutes, TimeUnit.MINUTES);
    }
}
