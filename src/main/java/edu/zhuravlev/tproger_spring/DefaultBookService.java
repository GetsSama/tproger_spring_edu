package edu.zhuravlev.tproger_spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultBookService implements BookService{
    private final BookRepository bookRepository;

    @Override
    public Book getBookById(Long id) {
        BookEntity bookEntity = bookRepository
                                            .findById(id)
                                            .orElseThrow(()->new RuntimeException("Book not found: id = " + id));

        return new Book(bookEntity.getId(),
                        bookEntity.getAuthor(),
                        bookEntity.getTitle(),
                        bookEntity.getPrice());
    }

    @Override
    public List<Book> getAllBooks() {
        Iterable<BookEntity> bookEntities = bookRepository.findAll();
        List<Book> books = new ArrayList<>();

        for (var entities : bookEntities) {
            books.add(new Book(entities.getId(),
                    entities.getAuthor(),
                    entities.getTitle(),
                    entities.getPrice()));
        }
        return books;
    }

    @Override
    public void addBook(Book book) {
        BookEntity bookEntity = new BookEntity(null, book.getAuthor(), book.getTitle(), book.getPrice());
        bookRepository.save(bookEntity);
    }
}
