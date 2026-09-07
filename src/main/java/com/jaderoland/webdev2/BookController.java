package com.jaderoland.webdev2;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping ("/books")
    public List<Book> getAllBooks () {
        return bookService.returnAllBooks();
    }

    @GetMapping ("/{id}")
    public Book getBookById(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }
}
