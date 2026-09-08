package com.jaderoland.webdev2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public Book getBookById(@PathVariable("id") Long bookId, Model model) {
        for (Book b : bookService.returnAllBooks()) {
            if (b.getId() == bookId) {
                model.addAttribute("book", b);
                return bookService.getBookById(bookId);
            }
        }
        return null;
    }

    @PostMapping()
    public String postMethodName(@RequestParam String title, @RequestParam  String author, @RequestParam Long bookId) {
        bookService.addBook(new Book(title, author, bookId));
        return "redirect:/books";
    }

}
