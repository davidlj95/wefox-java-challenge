package com.api.sample.restful.helpers;

import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ResultSetService {

    /**
     * Converts a ResultSet object to a list of HashMaps representing each row
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public List<HashMap<String, Object>> toHashMapList(ResultSet resultSet) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        while (resultSet.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), resultSet.getObject(i));
            }
            list.add(row);
        }
        return list;
    }
}
