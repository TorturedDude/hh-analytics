package com.itjobmarketanalytics.webui.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itjobmarketanalytics.webui.dto.*;
import com.itjobmarketanalytics.webui.dto.analytics.AverageSalaryData;
import com.itjobmarketanalytics.webui.dto.analytics.QueryData;
import com.itjobmarketanalytics.webui.exception.RestApiException;
import com.itjobmarketanalytics.webui.exception.RestApiUnauthorizedException;
import com.itjobmarketanalytics.webui.exception.RestApiUnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@Slf4j
public class RestApiClientService {
    RestTemplate restTemplate;

    public RestApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final Gson gson = new Gson();

    @Value("${app.hostname}")
    String host;

    private static final String SING_IN = "/auth/signin";
    private static final String SING_UP = "/auth/signup";
    private static final String USER = "/user/";
    private static final String CURRENT_SUBSCRIPTIONS = "/subscriptions";
    private static final String AVAILABLE_SUBSCRIPTIONS = "/subscriptions/allAvailable";


    private static final String ANALYTICS_BY_QUERY = "/analytics/byQuery";
    private static final String AVERAGE_SALARY_BY_QUERY = "/analytics/history/";


    public void signUp(SignUpDto dto) throws RestApiException {
        String url = host + SING_UP;

        try {
            restTemplate.postForEntity(url, dto, String.class);
            log.debug("Sign up request successfully executed");
        } catch (HttpClientErrorException e) {
            throw convertException(e);
        }
    }

    public SignInResponseDto signIn(SignInDto dto) throws RestApiException {
        String url = host + SING_IN;

        try {
            HttpEntity<SignInDto> request = new HttpEntity<>(dto);
            ResponseEntity<SignInResponseDto> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, SignInResponseDto.class
            );

            log.debug("Sign in request successfully executed");

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw convertException(e);
        }
    }

    public UserDto getUser(String token) throws RestApiException {
        String url = host + USER;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, UserDto.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw convertException(e);
        }
    }

    public List<QueryData> analyticsByQuery() throws RestApiException {
        String url = host + ANALYTICS_BY_QUERY;
        try {
            ResponseEntity<List<QueryData>> rateResponse =
                    restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<QueryData>>() {
                    });

            return rateResponse.getBody();
        } catch (HttpClientErrorException e) {
            throw convertException(e);
        }
    }

    public UserDto updateUser(Long telegramChatId, String token) throws RestApiException {
        String url = host + USER;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        UserUpdateDto userUpdateDto = new UserUpdateDto(telegramChatId);
        HttpEntity<UserUpdateDto> entity = new HttpEntity<>(userUpdateDto, headers);

        try {
            ResponseEntity<UserDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, UserDto.class);
            log.info("UpdateUser successfully executed");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw convertException(e);
        }
    }

    public List<UserSubscriptionsDto> getCurrentSubscriptions(String token) throws RestApiException {
        String url = host + CURRENT_SUBSCRIPTIONS;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<UserSubscriptionsDto>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<UserSubscriptionsDto>>() {
                    });
            log.info("Get current subscriptions successfully executed");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw convertException(e);
        }
    }

    public List<UserSubscriptionsDto> getAvailableSubscriptions() throws RestApiException {
        String url = host + AVAILABLE_SUBSCRIPTIONS;
        try {
            ResponseEntity<List<UserSubscriptionsDto>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserSubscriptionsDto>>() {
                    });
            log.info("Get all available subscriptions successfully executed");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw convertException(e);
        }
    }

    public List<UserSubscriptionsDto> addSubscriptions(String query, String token) throws RestApiException {
        String url = host + CURRENT_SUBSCRIPTIONS + "/" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<UserSubscriptionsDto>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<UserSubscriptionsDto>>() {
                    });
            log.info("Add subscriptions successfully executed");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw convertException(e);
        }
    }

    public List<UserSubscriptionsDto> removeSubscriptions(String query, String token) throws RestApiException {
        String url = host + CURRENT_SUBSCRIPTIONS + "/" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<UserSubscriptionsDto>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    new ParameterizedTypeReference<List<UserSubscriptionsDto>>() {
                    });
            log.info("Remove subscriptions successfully executed");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw convertException(e);
        }
    }

    private RestApiException convertException(Exception e) {
        if (e instanceof HttpServerErrorException) {
            return new RestApiUnknownException("Unknown error");
        }

        if (!(e instanceof HttpClientErrorException clientException)) {
            return new RestApiUnknownException("Unknown error");
        }

        if (clientException instanceof HttpClientErrorException.Unauthorized) {
            String exceptionMessage = extractErrorMessage(clientException, "Unauthorized");

            return new RestApiUnauthorizedException(exceptionMessage);
        } else if (clientException.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            return new RestApiUnknownException("Unknown error");
        }

        String exceptionMessage = extractErrorMessage(clientException, "Unknown error");
        log.info("Exception message -> {} ; status code -> {}", exceptionMessage, clientException.getStatusCode());

        return new RestApiException(exceptionMessage);
    }

    private String getResponseMessageValue(String responseMessage) {
        return gson.fromJson(responseMessage, JsonObject.class).get("message").getAsString();
    }

    private String extractErrorMessage(HttpClientErrorException exception, String defaultMessage) {
        String responseMessage = exception.getResponseBodyAsString();
        try {
            return getResponseMessageValue(responseMessage);
        } catch (Exception ex) {
            return defaultMessage;
        }
    }

    public List<AverageSalaryData> averageSalaryByQuery(String query) throws RestApiException {
        String url = host + AVERAGE_SALARY_BY_QUERY + query;

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("depth", 30);
        try {
            ResponseEntity<List<AverageSalaryData>> rateResponse =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<AverageSalaryData>>() {
                            }
                    );
            return rateResponse.getBody();
        } catch (HttpClientErrorException e) {
            throw convertException(e);
        }
    }

}
