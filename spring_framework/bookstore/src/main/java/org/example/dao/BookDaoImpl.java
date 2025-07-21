package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.example.models.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BooksDao {

    private final DataSource ds;

    public BookDaoImpl(DataSource datasource) {
        this.ds = datasource;
    }

    // 1. Получить все книги
    @Override
    public List<Book> findAll() {
        // Получаем все книги из базы данных и сортируем на уровне sql запроса
        final String SELECT_ALL_SQL = "select * from books order by name";
        List<Book> books = new ArrayList<>();
        try (Connection con = ds.getConnection();
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                // Создаем объект книги и добавляем его в список
                books.add(
                        new Book(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("author"),
                                rs.getInt("pages")));
            }

            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. Создать новую книгу
    @Override
    public Book save(Book book) {
        // Проверяем, существует ли идентичная книга
        Book exactBook = findExactBook(book);
        // Если такая книга уже существует, возвращаем ее
        if (exactBook != null) {
            return exactBook;
        }
        // Если такой книги нет, то создаем ее
        final String INSERT_SQL = "insert into books (name, author, pages) values (?, ?, ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPages());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    // Устанавливаем идентификатор книги
                    book.setId(rs.getString(1));
                }
            }

            return book;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 3. Получить книгу по id
    @Override
    public Book getById(String bookId) {
        final String GET_BOOK_BY_ID_SQL = "select * from books where id = ?";
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_ID_SQL)) {
            ps.setString(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException(String.format("Book with id %s not found :(", bookId));
                }

                // Создаем объект книги и возвращаем его
                return new Book(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("pages"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 4. Обновить книгу
    @Override
    public Book update(Book book) {
        // Проверяем, существует ли книга. Нельзя обновить книгу, которая не была создана
        if (book.getId() == null || book.getId().isEmpty()) {
            throw new RuntimeException("Cannot update not saved book");
        }
        final String UPDATE_SQL = "update books set name = ?, author = ?, pages = ? where id = ?";
        try (Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPages());
            ps.setString(4, book.getId());

            ps.executeUpdate();

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Book book) {
        // Проверяем, существует ли книга. Нельзя удалить несуществующую книгу
        if (book.getId() == null || book.getId().isEmpty()) {
            throw new RuntimeException("Cannot delete not saved book");
        }
        final String DELETE_SQL = "delete from books where id = ?";
        try (Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            ps.setString(1, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        // Удаляем все книги (идентификатор не сбрасывается)
        final String DELETE_ALL_SQL = "delete from books";
        try (Connection con = ds.getConnection();
                Statement stat = con.createStatement()) {
            stat.executeUpdate(DELETE_ALL_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Вспомогательный метод для поиска идентичной книги
    @Override
    public Book findExactBook(Book book) {
        // Ищем, приводим все к lowercase для точности поиска
        final String FIND_EXACT_BOOK_SQL = "select * from books where lower(name) = lower(?) and lower(author) = lower(?) and pages = ?";
        try (Connection con = ds.getConnection();
                PreparedStatement ps = con.prepareStatement(FIND_EXACT_BOOK_SQL)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPages());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                // Возвращаем книгу, если она была найдена
                return new Book(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("pages"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
