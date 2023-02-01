package edu.zhuravlev.tproger_spring;

import java.util.List;

public interface BookService {
    Book getBookById(Long id);
    List<Book> getAllBooks();
    void addBook(Book book);
    List<Book> findByAuthor(String author);
}
