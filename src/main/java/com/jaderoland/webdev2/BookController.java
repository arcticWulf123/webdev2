package com.jaderoland.webdev2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false) String author) {
        if ((author == null)) {
            return bookService.returnAllBooks();
        }
        List<Book> filteredBooks = new ArrayList<>();
        for (Book b : bookService.returnAllBooks()) {
            if (b.getAuthor().equalsIgnoreCase(author)) {
                filteredBooks.add(b);
            }
        }
        return filteredBooks;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long bookId) {

        return bookService.getBookById(bookId);
    }
}
