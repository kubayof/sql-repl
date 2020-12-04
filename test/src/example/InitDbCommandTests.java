package example;

import example.command.InitDbCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitDbCommandTests {
    @BeforeAll
    public static void init() {
        new InitDbCommand().execute();
    }

    @Test
    public void selectFromProducts() {
        selectTest(
                "products",
                list(1, 2, 3, 4, 5),
                list("Milk", "Apples", "Bananas", "Bread", "Eggs")
        );
    }

    @Test
    public void selectFromCustomers() {
        selectTest(
                "customers",
                list(1, 2),
                list("First", "Second"),
                list("User", "One")
        );
    }

    @Test
    public void selectFromSales() {
        selectTest(
                "sales",
                list(1, 2, 3, 4, 5, 6, 7),
                list(1, 2, 4, 3, 3, 4, 1),
                list(1, 1, 1, 2, 2, 2, 2),
                list("2020-01-01 14:00:01",
                        "2020-02-01 17:10:01",
                        "2020-04-01 11:00:01",
                        "2020-04-01 12:00:01",
                        "2020-04-01 10:00:01",
                        "2020-05-01 12:00:01",
                        "2020-05-01 11:00:01")
        );
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    private final void selectTest(String tableName, List<String>... expected) {
        try (Connection connection = DbUtils.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(MessageFormat.format("SELECT * FROM {0}", tableName));

            ResultSetMetaData rsmd = rs.getMetaData();
            List<String>[] actual = Stream.generate((Supplier<ArrayList<String>>) ArrayList::new)
                    .limit(rsmd.getColumnCount())
                    .toArray(ArrayList[]::new);
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    actual[i - 1].add(rs.getString(i));
                }
            }
            for (int i = 0; i < expected.length; i++) {
                Assertions.assertEquals(expected[i], actual[i]);
            }
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    private List<String> list(Object... objs) {
        return Arrays.stream(objs).map(Object::toString).collect(Collectors.toList());
    }

    @AfterAll
    public static void deleteTestDatabase() {
        TestUtils.deleteTestDatabase();
    }
}
