package org.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.example.dao.BooksDao;
import org.example.models.Book;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksDao booksDao;

    public BooksController(BooksDao booksDao) {
        this.booksDao = booksDao;
    }

    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", booksDao.findAll());
        return "books-list";
    }

    @GetMapping("/create-form")
    public String createBookForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book) {
        booksDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        booksDao.delete(booksDao.getById(id));
        return "redirect:/books";
    }

    @GetMapping("/delete-all")
    public String deleteAllBooks() {
        booksDao.deleteAll();
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookForm(@PathVariable String id, Model model) {
        model.addAttribute("book", booksDao.getById(id));
        return "edit-form";
    }

    @PostMapping("/update")
    public String updateBook(Book book) {
        booksDao.update(book);
        return "redirect:/books";
    }
}
