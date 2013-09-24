package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.*;

public class BookRepository implements BookRepositoryInterface {
    /** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
    private final ConcurrentHashMap<Long, Book> bookInMemoryMap;
    private ConcurrentHashMap<Long, Author> authorInMemoryMap = new ConcurrentHashMap<Long, Author>();
    private ConcurrentHashMap<Long, Review> reviewInMemoryMap = new ConcurrentHashMap<Long, Review>();
    
    /** Never access this key directly; instead use generateISBNKey() */
    private long isbnKey;
    
    public BookRepository(ConcurrentHashMap<Long, Book> bookMap) {
    	checkNotNull(bookMap, "bookMap must not be null for BookRepository");
    	bookInMemoryMap = bookMap;
    	isbnKey = 0;
    }

    /**
     * This should be called if and only if you are adding new books to the
     * repository.
     * 
     * @return a new incremental ISBN number
     */
    private final Long generateISBNKey() {
    	// increment existing isbnKey and return the new value
    	return Long.valueOf(++isbnKey);
    }

    /*
    private ArrayList<Author> authors = new ArrayList<Author>();
    private int authorNumber = 0;
    private Author author;
    private int id = 0;
    */
    /**
     * This will auto-generate unique ISBN for new books.
     */
    @Override
    public Book saveBook(Book newBook) {
    	checkNotNull(newBook, "newBook instance must not be null");
    	// Generate new ISBN
    	Long isbn = generateISBNKey();
    	newBook.setIsbn(isbn);
    	// TODO: create and associate other fields such as author
    	/*
    	authors = newBook.getAuthors();
    	authorNumber = authors.size();
    	newBook.setAuthors(authors.add(author.setId(++id), element));
    	*/
    	// Finally, save the new book into the map
    	bookInMemoryMap.putIfAbsent(isbn, newBook);

    	return newBook;
    }
		
    /**
     * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
     */
    @Override
    public Book getBookByISBN(Long isbn) {
    	checkArgument(isbn > 0, "ISBN was %s but expected greater than zero value", isbn);
    	return bookInMemoryMap.get(isbn);
    }
    
    @Override
    public Book deleteBook(Long isbn) {
    	checkArgument(isbn > 0, "ISBN was %s but expected greater than zero value", isbn);
    	return bookInMemoryMap.remove(isbn);
    }
    
    @Override
    public Author getAuthorByISBN(Long isbn) {
    	checkArgument(isbn > 0, "ISBN was %s but expected greater than zero value", isbn);
    	return authorInMemoryMap.get(isbn);
    }
    
    @Override
    public Review getReviewByISBN(Long isbn) {
    	checkArgument(isbn > 0, "ISBN was %s but expected greater than zero value", isbn);
    	return reviewInMemoryMap.get(isbn);
    }
}
