package com.api.sample.restful.repository;

import com.api.sample.restful.helpers.ResultSetService;
import com.api.sample.restful.helpers.SqliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonRepository {

    private final SqliteService sqliteService;

    private final ResultSetService resultSetService;

    @Value("pokemon.sqlite")
    private String POKEMON_SQLITE_FILE;

    private Connection connection;

    @PostConstruct
    private void connect() throws Exception {
        connection = sqliteService
                .getInMemoryDBFromResource(POKEMON_SQLITE_FILE);
    }

    public List<HashMap<String, Object>> findByIdentifierStartsWith(String identifier) {
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM pokemon WHERE identifier LIKE ?";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, identifier + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetService.toHashMapList(resultSet);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }


}
