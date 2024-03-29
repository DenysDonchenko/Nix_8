package ua.com.alevel.config.jdbc;

import java.sql.Connection;
import java.sql.Statement;

public interface JdbcConfig {
    void connect();
    Connection getConnection();
    Statement getStatement();
}
