package org.example;

import org.example.dao.BooksDao;
import org.example.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = { "jdbcUrl=jdbc:h2:mem:books-manager;DB_CLOSE_DELAY=-1" })
public class BookDaoImplTest {

    @Autowired
    private BooksDao bookDao;

    @BeforeEach
    void setUp() {
        bookDao.deleteAll();
    }

    @Test
    public void contextCreated() {
    }

    // Тест должен вернуть: книгу с id, которая была создана
    void save_ShouldReturnBookWithId() {
        Book book = bookDao.save(new Book("name", "author", 100));
        assertThat(book.getId()).isNotBlank();
        assertThat(bookDao.findAll()).hasSize(1);
        assertThat(bookDao.findAll())
                .extracting(Book::getId)
                .containsExactly(book.getId());
    }

    // Тест должен вернуть: пустой список
    @Test
    void deleteAll_ShouldReturnEmpty() {
        bookDao.save(new Book("name", "author", 100));
        assertThat(bookDao.findAll()).isNotEmpty();
        assertThat(bookDao.findAll()).hasSize(1);
        bookDao.deleteAll();

        assertThat(bookDao.findAll()).isEmpty();
    }

    // Тест должен вернуть: все книги
    @Test
    void findAll_ShouldReturnAllBooks() {
        assertThat(bookDao.findAll()).isEmpty();
        bookDao.save(new Book("name", "author", 100));
        assertThat(bookDao.findAll()).isNotEmpty();
        assertThat(bookDao.findAll()).hasSize(1);
    }

    // Тест должен вернуть: исключение, т.к. книги с таким id нет
    @Test
    void getById_ShouldThrowExceptionIfNoBookFound() {
        assertThatThrownBy(() -> bookDao.getById("1"))
                .isInstanceOf(RuntimeException.class);
    }

    // Тест должен вернуть: книгу с таким id
    @Test
    void getById_ShouldReturnCorrectBook() {
        Book book = bookDao.save(new Book("name1", "author1", 100));
        Book otherBook = bookDao.save(new Book("name2", "author2", 200));

        assertThat(bookDao.getById(book.getId())).isEqualTo(book);
        assertThat(bookDao.getById(otherBook.getId())).isEqualTo(otherBook);

        assertThat(bookDao.getById(book.getId()))
                .isNotNull()
                .extracting(Book::getName)
                .isEqualTo(book.getName());
    }

    // Тест должен вернуть: обновленную книгу
    @Test
    void update_ShouldReturnUpdatedBook() {
        Book book = bookDao.save(new Book("name1", "author1", 100));
        book.setName("new name");

        bookDao.update(book);

        // Соответствует новому имени
        assertThat(bookDao.getById(book.getId()).getName()).isEqualTo("new name");
    }

    // Тест должен вернуть: исключение, т.к. книги с таким id нет
    @Test
    void update_ThrowsExceptionIfBookNotFound() {
        assertThatThrownBy(() -> bookDao.update(new Book("name1", "author1", 100)))
                .isInstanceOf(RuntimeException.class);
    }

    // Тест должен вернуть: удалить корректную книгу, оставив остальное нетронутым
    @Test
    void delete_ShouldDeleteCorrectBook() {
        Book bookToKeep = bookDao.save(new Book("name1", "author1", 100));
        Book bookToDelete = bookDao.save(new Book("name2", "author2", 200));

        assertThat(bookDao.findAll()).hasSize(2);
        assertThat(bookDao.findAll())
                .extracting(Book::getId)
                .containsExactly(bookToKeep.getId(), bookToDelete.getId());

        bookDao.delete(bookToDelete);

        assertThat(bookDao.findAll()).hasSize(1);
        assertThat(bookDao.findAll())
                .extracting(Book::getId).containsExactly(bookToKeep.getId());
    }

    // Тест должен вернуть: исключение, т.к. книги с таким id нет
    @Test
    void delete_ShouldThrowExceptionIfBookNotFound() {
        assertThatThrownBy(() -> bookDao.delete(new Book("name1", "author1", 100)))
                .isInstanceOf(RuntimeException.class);
    }
}
