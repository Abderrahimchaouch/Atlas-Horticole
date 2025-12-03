package com.atlashorticole.product_service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest // Launch the entire Spring context
@ActiveProfiles("test") // laod application-test.yml
@Transactional //important : Cancel everything after the test
public abstract class AbstractIntegrationTest {
    
    @Autowired
    protected DataSource dataSource;
    
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
    @BeforeEach
    void setUp() throws SQLException {
        // Verify that the database connection is working
        try (var connection = dataSource.getConnection()) {
            System.out.println("Test database connected: " + connection.getMetaData().getURL());
        }
    }
    
    @AfterEach
    void tearDown() {
        // Clean Up the tables if necessairy
        jdbcTemplate.execute("DROP ALL OBJECTS");
    }
}