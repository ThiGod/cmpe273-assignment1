package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Book {
    private long isbn;
    private String title;
    
    @JsonProperty("publication-date")
    private String publication_date;
   
    private String language;
    
    @JsonProperty("num-pages")
    private int num_pages;
    
    private String status = "available";
   
    private ArrayList<Author> authors = new ArrayList<Author>();
	private int authorNumber = 0;
	private int authorId;
	public ArrayList<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<Author> authors) {
		authorNumber = authors.size();
		for(authorId = 0; authorId < authorNumber; authorId++)  {
			authors.get(authorId).setId(authorId+1);
		}	
		this.authors = authors;
	} 
	
	private ArrayList<Review> reviews = new ArrayList<Review>();
	private int reviewNumber = 0;
	private int reviewId;
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		reviewNumber = reviews.size();
		for(reviewId = 0; reviewId < reviewNumber; reviewId++)  {
			authors.get(reviewId).setId(reviewId+1);
		}	
		this.reviews = reviews;
	} 
    

    // add more fields here

    /**
     * @return the isbn
     */
    public long getIsbn() {
    	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
    	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
    	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}
	
    public String getLanguage() {
    	return language;
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
    
    public int getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}
    
    public String getStatus() {
    	return status;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }
}
