package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.IntParam;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
    	this.bookRepository = bookRepository;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	int reviewNumber = book.getReviews().size();
    	BookDto bookResponse = new BookDto(book);
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
    	bookResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "PUT"));
    	// add more links
    	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
    	bookResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn() + "/reviews", "POST"));
    	if(reviewNumber > 0) {
    		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() + "/reviews", "GET"));
    	}	
    	
    	return bookResponse;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(name = "create-book")
    public Response createBook(Book request) {
    	// Store the new book in the BookRepository so that we can retrieve it.
    	Book savedBook = bookRepository.saveBook(request);
    	
    	String location = "/books/" + savedBook.getIsbn();
    	BookDto bookResponse = new BookDto(savedBook);
    	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
    	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
    	// Add other links if needed
    	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
    	bookResponse.addLink(new LinkDto("create-review", location + "/reviews", "POST"));
    	
    	return Response.status(201).entity(bookResponse).build();
    	
    }
    
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public BookDto delteBook(@PathParam("isbn") LongParam isbn) {
    	Book book = bookRepository.deleteBook(isbn.get());
    	BookDto bookRepository = new BookDto(book);
    	bookRepository.addLink(new LinkDto("create-book", "/books", "POST"));
    	
    	return bookRepository;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{isbn}/reviews")
    @Timed(name = "create-reviews")
    public Response createReview(Review review, @PathParam("isbn") LongParam isbn) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	int reviewNumber = book.getReviews().size();
    	review.setId(reviewNumber+1);
    	book.getReviews().add(review);
    	Book savedBook = bookRepository.saveBook(book);
    	BookDto bookResponse = new BookDto(savedBook);
    	String location = "/books/" + savedBook.getIsbn() + "/reviews/";
    	bookResponse.addLink(new LinkDto("view-review", location, "GET"));
    	 	
    	return Response.status(201).entity(bookResponse).build();
    } 
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{isbn}/authors")
    @Timed(name = "view-all-authors")
    public AuthorDto getAuthorByIsbn(@PathParam("isbn") LongParam isbn) {
    	Author author = bookRepository.getAuthorByISBN(isbn.get());
    	AuthorDto authorResponse = new AuthorDto(author);
    	
    	return authorResponse;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-all-reviews")
    public BookDto getReviewByIsbn(@PathParam("isbn") LongParam isbn, @QueryParam("id") IntParam id) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	BookDto bookResponse = new BookDto(book);
    	
    	/*
    	int reviewNumber = book.getReviews().size();
    	BookDto bookResponse = new BookDto(book);
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
    	bookResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "PUT"));
    	// add more links
    	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
    	bookResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn() + "/reviews", "POST"));
    	if(reviewNumber > 0) {
    		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() + "/reviews", "GET"));
    	}	
    	*/
    	
    	return bookResponse;
    }
}

