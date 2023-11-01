CREATE TABLE Subscriptions
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    query   VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE INDEX idx_query ON Subscriptions (query);
