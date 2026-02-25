package com.example.webbackend.controller;

import com.example.webbackend.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private List<Book> books = new ArrayList<>();

    private Long nextId = 1L;

    public BookController() {
        // Add 15 books with varied data for testing
        books.add(new Book(nextId++, "Spring Boot in Action", "Craig Walls", 39.99));
        books.add(new Book(nextId++, "Effective Java", "Joshua Bloch", 45.00));
        books.add(new Book(nextId++, "Clean Code", "Robert Martin", 42.50));
        books.add(new Book(nextId++, "Java Concurrency in Practice", "Brian Goetz", 49.99));
        books.add(new Book(nextId++, "Design Patterns", "Gang of Four", 54.99));
        books.add(new Book(nextId++, "Head First Java", "Kathy Sierra", 35.00));
        books.add(new Book(nextId++, "Spring in Action", "Craig Walls", 44.99));
        books.add(new Book(nextId++, "Clean Architecture", "Robert Martin", 39.99));
        books.add(new Book(nextId++, "Refactoring", "Martin Fowler", 47.50));
        books.add(new Book(nextId++, "The Pragmatic Programmer", "Andrew Hunt", 41.99));
        books.add(new Book(nextId++, "You Don't Know JS", "Kyle Simpson", 29.99));
        books.add(new Book(nextId++, "JavaScript: The Good Parts", "Douglas Crockford", 32.50));
        books.add(new Book(nextId++, "Eloquent JavaScript", "Marijn Haverbeke", 27.99));
        books.add(new Book(nextId++, "Python Crash Course", "Eric Matthes", 38.00));
        books.add(new Book(nextId++, "Automate the Boring Stuff", "Al Sweigart", 33.50));
    }

    // get all books - /api/books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // create a new book
    @PostMapping("/books")
    public List<Book> createBook(@RequestBody Book book) {
        books.add(book);
        return books;
    }

    // search by title
    @GetMapping("/books/search")
    public List<Book> searchByTitle(
            @RequestParam(required = false, defaultValue = "") String title
    ) {
        if (title.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

    }

    // price range
    @GetMapping("/books/price-range")
    public List<Book> getBooksByPrice(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return books.stream()
                .filter(book -> {
                    boolean min = minPrice == null || book.getPrice() >= minPrice;
                    boolean max = maxPrice == null || book.getPrice() <= maxPrice;

                    return min && max;
                }).collect(Collectors.toList());
    }

    // sort
    @GetMapping("/books/sorted")
    public List<Book> getSortedBooks(
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ) {
        Comparator<Book> comparator;

        switch (sortBy.toLowerCase()) {
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
            default:
                comparator = Comparator.comparing(Book::getTitle);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return books.stream().sorted(comparator)
                .collect(Collectors.toList());


    }

    //get endpoint with pagination
    @GetMapping("/books/pagination")
    public List<Book> getBooksWithPagination(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ) {
        return books.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    //Advanced GET endpoint with filtering, sorting, and pagination combined in the valid order
    @GetMapping("/books/advanced")
    public List<Book> getBooksAdvanced(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ) {
        Comparator<Book> comparator;

        switch (sortBy.toLowerCase()) {
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
            default:
                comparator = Comparator.comparing(Book::getTitle);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> {
                    boolean min = minPrice == null || book.getPrice() >= minPrice;
                    boolean max = maxPrice == null || book.getPrice() <= maxPrice;

                    return min && max;
                })
                .sorted(comparator)
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    // get book by id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return books.stream().filter(book -> book.getId().equals(id))
                .findFirst().orElse(null);
    }
    //PUT endpoint (update book)
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        for (Book b : books) {
            if (b.getId().equals(id)) {
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPrice(book.getPrice());
                return b;
            }
        }
        return null;
    }
    //PATCH endpoint (partial update)
    @PatchMapping("/books/{id}")
    public Book patchBook(@PathVariable Long id, @RequestBody Book book) {
        for (Book b : books) {
            if (b.getId().equals(id)) {
                if (book.getTitle() != null) {
                    b.setTitle(book.getTitle());
                }
                if (book.getAuthor() != null) {
                    b.setAuthor(book.getAuthor());
                }
                if (book.getPrice() != null) {
                    b.setPrice(book.getPrice());
                }
                return b;
            }
        }
        return null;
    }

    //DELETE endpoint (remove book)
    @DeleteMapping("/books/{id}")
    public List<Book> deleteBook(@PathVariable Long id) {
        books = books.stream().filter(book -> !book.getId().equals(id))
                .collect(Collectors.toList());
        return books;
    }








































}
