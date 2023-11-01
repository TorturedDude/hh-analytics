ALTER TABLE Vacancy_analytics
    MODIFY COLUMN average_salary decimal(10, 2);

UPDATE Vacancy_analytics
SET average_salary = ROUND(average_salary, 2);