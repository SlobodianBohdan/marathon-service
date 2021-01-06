package com.softserve.sprint16;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class BaseDaoTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    void clearTables(String... tableNames) {
        final String[] cleanupQueries = new String[tableNames.length];
        for (int index = 0; index < tableNames.length; index++) {
            cleanupQueries[index] = "TRUNCATE TABLE " + tableNames[index] + " CASCADE";
        }
        jdbcTemplate.batchUpdate(cleanupQueries);
    }

}
