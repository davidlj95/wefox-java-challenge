package com.api.sample.restful.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
public class SqliteService {

    private final JARResourceService jarResourceService;

    @PostConstruct
    private void loadSQLiteClass() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    public Connection getInMemoryDBFromResource(String resource) throws Exception {
        String resourceFilename = this.jarResourceService.export(resource);
        File resourceFile = new File(resourceFilename);

        String JDBC_SQLITE_IN_MEMORY_URI = "jdbc:sqlite::memory:";
        Connection connection = DriverManager.getConnection(JDBC_SQLITE_IN_MEMORY_URI);

        Statement statement = connection.createStatement();
        String query = String.format("restore from %s", resourceFile.getAbsolutePath());
        statement.executeUpdate(query);

        resourceFile.delete();

        return connection;
    }

}
