Feature: the application is healthy
    Scenario: client makes a GET call to /health
        When the client makes a GET call to /health
        Then the client receives status code of 200