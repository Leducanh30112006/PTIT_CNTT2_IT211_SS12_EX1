package com.ra.ptit_cntt2_it211_ss12_ex1.service;

import com.ra.ptit_cntt2_it211_ss12_ex1.entity.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookService() {

        books.add(new Book(idGenerator.getAndIncrement(), "Lập trình Java", "Author A", 150000.0));
        books.add(new Book(idGenerator.getAndIncrement(), "Spring Boot Căn Bản", "Author B", 220000.0));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book createBook(Book book) {
        book.setId(idGenerator.getAndIncrement());
        books.add(book);
        return book;
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return getBookById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            return book;
        });
    }

    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}