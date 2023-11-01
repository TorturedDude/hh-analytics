CREATE TABLE Vacancy_analytics
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    date             DATE NOT NULL,
    query            VARCHAR(255),
    vacancy_count    INT,
    average_salary   decimal
);

-- Create UNIQUE composite index for date+query
CREATE UNIQUE INDEX date_query_idx ON Vacancy_analytics (date, query);