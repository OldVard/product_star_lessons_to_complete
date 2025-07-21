package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.example.models.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner cmd(DataSource ds) {
        return args -> {
            try (InputStream is = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(is.readAllBytes());
                try (Connection con = ds.getConnection();
                        Statement stat = con.createStatement()) {
                    stat.executeUpdate(sql);

                    // stat.executeUpdate(
                    // "insert into books (name, author, pages) values ('Американские боги', 'Нил
                    // Гейман', 702)");

                    System.out.println("----- [BOOKS] -----");
                    ResultSet rs = stat.executeQuery("select * from books");
                    while (rs.next()) {
                        Book book = new Book(rs.getString("id"), rs.getString("name"), rs.getString("author"),
                                rs.getInt("pages"));
                        prettyBookPrint(book);
                    }
                }
            }
        };
    }

    private void prettyBookPrint(Book book) {
        System.out.printf("ID: %s AUTHOR: %s NAME: %s PAGES: %d\n", book.getId(), book.getAuthor(), book.getName(),
                book.getPages());
        System.out.println("--------------------------------");
    }
}
