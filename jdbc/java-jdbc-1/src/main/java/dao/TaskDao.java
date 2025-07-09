package dao;

import entity.Task;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskDao {
    private final DataSource dataSource;

    public TaskDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Task save(Task task) {
        // get Connection
        // create statement
        // set parameters
        // execute
        // get id
        // set id

        String sql = "insert into task (title, finished, creation_date)" +
                "values (?, ?, ?);";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, task.getTitle());
            statement.setBoolean(2, task.isFinished());
            statement.setTimestamp(3, Timestamp.valueOf(task.getCreationDate()));

            statement.executeUpdate();

            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    task.setId(resultSet.getInt(1));
                }
            }

            return task;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select task_id, " +
                    "title, finished, creation_date from task order by task_id;")) {

            while(resultSet.next()) {
                tasks.add(new Task(resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getTimestamp(4).toLocalDateTime()
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return tasks;
    }

    // -- ДОМАШНЕЕ ЗАДАНИЕ --
    // 1.
    public int deleteAll() {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate("truncate task;");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 2.
    public Task getById(Integer id) {
        String prepared_sql = "select * from task where task_id= ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(prepared_sql)) {
            prepStatement.setInt(1, id);

            ResultSet resultSet = prepStatement.executeQuery();
            if(!resultSet.next()) {
                return null;
            }

            return convertToTask(resultSet);

        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 3.
    public List<Task> findAllNotFinished() {
        List<Task> tasks = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "select * from task where finished is false order by task_id;");

            while(resultSet.next()) {
                tasks.add(convertToTask(resultSet));
            }

            return tasks;
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 4.
    public List<Task> findNewestTasks(Integer numberOfNewestTasks) {
        List<Task> tasks = new ArrayList<>();
        String sql = "select * from task order by creation_date desc limit ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            // Возвращает в порядке от самого нового к самому старому
            statement.setInt(1, numberOfNewestTasks);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                tasks.add(convertToTask(resultSet));
            }

            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 5.
    public Task finishTask(Task task) {
        String prep_sql = "update task set finished = true where task_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(prep_sql)) {
            statement.setInt(1, task.getId());
            statement.executeUpdate();

            task.setFinished(true);
            return task;
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 6.
    public void deleteById(Integer id) {
        String sql = "delete from task where task_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // [ВСПОМОГАТЕЛЬНОЕ]
    private Task convertToTask(ResultSet resultSet) {
        try {
            Task t = new Task(
                    resultSet.getString(2),
                    resultSet.getBoolean(3),
                    resultSet.getTimestamp(4).toLocalDateTime()
            );
            t.setId(resultSet.getInt(1));

            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
