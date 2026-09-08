package com.jaderoland.webdev2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId, Model model) {
        for (Book b : bookService.returnAllBooks()) {
            if (b.getId() == bookId) {
                model.addAttribute("book", b);
                return ResponseEntity.ok(bookService.getBookById(bookId));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Book createBook(@RequestParam String title, @RequestParam String author, @RequestParam Long bookId) {
        Book book = new Book(title, author, bookId);
        bookService.addBook(book);
        return book;
    }

}
