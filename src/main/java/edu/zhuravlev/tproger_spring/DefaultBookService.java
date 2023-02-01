package edu.zhuravlev.tproger_spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultBookService implements BookService{
    private final BookRepository bookRepository;
    private final BookToEntityMapper bookToEntityMapper;

    @Override
    public Book getBookById(Long id) {
        BookEntity bookEntity = bookRepository
                                            .findById(id)
                                            .orElseThrow(()->new RuntimeException("Book not found: id = " + id));

        return bookToEntityMapper.bookEntityToBook(bookEntity);
    }

    @Override
    public List<Book> getAllBooks() {
        Iterable<BookEntity> bookEntities = bookRepository.findAll();
        List<Book> books = new ArrayList<>();

        for (var entities : bookEntities)
            books.add(bookToEntityMapper.bookEntityToBook(entities));

        return books;
    }

    @Override
    public void addBook(Book book) {
        BookEntity bookEntity = bookToEntityMapper.bookToBookEntity(book);
        bookRepository.save(bookEntity);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        Iterable<BookEntity> iterable = bookRepository.findAllByAuthorContaining(author);
        List<Book> books = new ArrayList<>();
        for(var bookEntity : iterable)
            books.add(bookToEntityMapper.bookEntityToBook(bookEntity));
        return books;
    }
}
