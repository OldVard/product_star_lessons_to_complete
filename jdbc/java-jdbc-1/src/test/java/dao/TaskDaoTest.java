package dao;

import entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskDaoTest {
    private TaskDao taskDao;
    private final PGSimpleDataSource dataSource = new PGSimpleDataSource();

    private static final String DATABASE_NAME = "productStar";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @BeforeAll
    public void setup() {
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);

        taskDao = new TaskDao(dataSource);
        initializeDb(dataSource);
    }

    private void initializeDb(DataSource dataSource) {
        //считать содержимое файла
        // выполнить эту команду в базе
        try(InputStream inputStream = new FileInputStream("src/main/resources/initial.sql")) {
            String sql = new String (inputStream.readAllBytes());
            try(Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void testSaveSetsId() {
        Task task = new Task("test task", false, LocalDateTime.now());
        taskDao.save(task);

        Assertions.assertNotNull(task.getId());
    }

    @Test
    public void testFindAllReturnsAllTasks() {
        taskDao.deleteAll();

        Task task1 = new Task("[FIRST] task", false, LocalDateTime.now());
        Task task2 = new Task("[SECOND] task", false, LocalDateTime.now());
        taskDao.save(task1);
        taskDao.save(task2);

        Assertions.assertEquals(2, taskDao.findAll().size());
    }

    // -- ДОМАШНЕЕ ЗАДАНИЕ --
    // 1. Удаление всех данных из таблицы
    @Test
    public void testDeleteAllDeletesAllRowsInTasks() {
        taskDao.deleteAll();

        Task task1 = new Task("[FIRST] task", false, LocalDateTime.now());
        Task task2 = new Task("[SECOND] task", false, LocalDateTime.now());
        taskDao.save(task1);
        taskDao.save(task2);

        Assertions.assertEquals(2, taskDao.findAll().size());

        taskDao.deleteAll();

        Assertions.assertTrue(taskDao.findAll().isEmpty());
    }

    // 2. Вернуть задачу по id
    @Test
    public void testGetByIdReturnsCorrectTask() {
        taskDao.deleteAll();

        Task task1 = new Task("[FIRST] task", false, LocalDateTime.now());
        Task task2 = new Task("[SECOND] task", false, LocalDateTime.now());
        taskDao.save(task1);
        taskDao.save(task2);

        Task t = taskDao.getById(task1.getId());

        Assertions.assertNotNull(t);
        Assertions.assertEquals(t.getId(), task1.getId());
        Assertions.assertEquals(t.getTitle(), task1.getTitle());
        Assertions.assertEquals(t.isFinished(), task1.isFinished());
        Assertions.assertEquals(t.getCreationDate(), task1.getCreationDate());

        System.out.println(task1);
    }

    @Test
    public void testFindNotFinishedReturnsCorrectTasks() {
        taskDao.deleteAll();

        Task task1 = new Task("[FIRST] task", false, LocalDateTime.now());
        Task task2 = new Task("[SECOND] task", false, LocalDateTime.now());
        Task task3 = new Task("[THIRD] task", true, LocalDateTime.now());
        taskDao.save(task1);
        taskDao.save(task2);
        taskDao.save(task3);

        assertThat(taskDao.findAllNotFinished())
                .hasSize(2)
                .extracting(Task::getId)
                .containsExactlyInAnyOrder(task1.getId(), task2.getId());
    }

    @Test
    public void testFindNewestTasksReturnsCorrectTasks() {
        taskDao.deleteAll();
        Task taskA = new Task("Task A", false, LocalDateTime.of(2025, 7, 9, 14, 15, 45));
        Task taskB = new Task("Task B", false, LocalDateTime.of(2025, 5, 22, 5, 16, 55));
        Task taskC = new Task("Task C", false, LocalDateTime.of(2025, 6, 7, 10, 30, 10));
        taskDao.save(taskA);
        taskDao.save(taskB);
        taskDao.save(taskC);

        assertThat(taskDao.findNewestTasks(3))
                .hasSize(3)
                .extracting(Task::getId)
                .containsExactly(taskA.getId(), taskC.getId(), taskB.getId());
    }

    @Test
    public void testFinishSetsCorrectFlagInDb() {
        taskDao.deleteAll();

        Task task1 = new Task("[FIRST] task", false, LocalDateTime.now());
        Task task2 = new Task("[SECOND] task", false, LocalDateTime.now());
        Task task3 = new Task("[THIRD] task", true, LocalDateTime.now());
        taskDao.save(task1);
        taskDao.save(task2);
        taskDao.save(task3);

        assertThat(taskDao.finishTask(task1))
                .extracting(Task::isFinished)
                .isEqualTo(true);

        assertThat(taskDao.findAllNotFinished())
                .hasSize(1)
                .extracting(Task::getId)
                .containsExactly(task2.getId());
    }

    @Test
    public void deleteByIdDeletesOnlyNecessaryData() {
        taskDao.deleteAll();
        Task task1 = new Task("[FIRST] task", false, LocalDateTime.now());
        Task task2 = new Task("[SECOND] task", false, LocalDateTime.now());
        Task task3 = new Task("[THIRD] task", true, LocalDateTime.now());
        taskDao.save(task1);
        taskDao.save(task2);
        taskDao.save(task3);

        taskDao.deleteById(task3.getId());
        assertThat(taskDao.findAll())
                .hasSize(2);
        assertThat(taskDao.getById(task3.getId())).isNull();
    }
}
