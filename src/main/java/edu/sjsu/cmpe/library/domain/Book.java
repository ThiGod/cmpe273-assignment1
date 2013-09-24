package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;

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
	

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
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
