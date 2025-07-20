package org.example.dao;

import java.util.List;

import org.example.models.Book;

public interface BooksDao {
    List<Book> findAll();
    Book save(Book book);
    Book getById(String bookId);
    Book update(Book book);
    void delete(Book book);
    void deleteAll();

    // Вспомогательные методы
    Book findExactBook(Book book);
}
