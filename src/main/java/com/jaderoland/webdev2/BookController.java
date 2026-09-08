package com.jaderoland.webdev2;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseBody 
    public List<Book> getAllBooks (@RequestParam()) {
        if ()
        return bookService.returnAllBooks();
    }

    @GetMapping ("/{id}")
    @ResponseBody 
    public Book getBookById(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }
}
