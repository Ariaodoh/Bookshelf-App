package com.ljmu.comp7501.eodoh.bookshelf;

import java.util.Objects;


 /**
  * --------------------------------[BOOK CLASS]-------------------------------------
 *The book class defines a book object with its feature as fields and methods
 *This class implements the comparable interface and defines the compareTo method with a title field
 *This class overides the toString method to provide use information about book object
 */
public class Book implements Comparable<Book> {

    /**
     * -----------------------------[BOOK FIELDS]------------------------------------
     * Declears class fields for book obj features. 
     */
    private String title, author, publisher, synopsis;
    private long releaseYear, isbn;

   
    /**
     * -----------------------------[BOOK CONSTRUCTOR]-------------------------------
     * Defines Book obj constructor for book obj with title only. This is used to creat a book when only 
     * the of the book title is provided
     * @param title this is the title of the book object 
     */
    public Book(String title){
        this.title = title;
    }
    

    
    /**
     * -------------------------------[BOOK CONSTRUCTOR]-------------------------------
     * Defines constructor for book object with all the book properties provided
     * This is used to create a book obj when all properties are provided e.g, from file
     * Where title, author, or publisher data is missing, an empty string in appened instead
     * of null. This prevents a NullPointerException when using the comparaing methods 
     * @param title this is the title of the book
     * @param author this is the author name of the book
     * @param publisher this is the publishing company of the book
     * @param synopsis this is the synopsis of the book
     * @param releaseYear this is the release year of the book 
     * @param isbn this is the ISBN of the book
     */
    public Book(String title, String author, String publisher, String synopsis, long releaseYear, long isbn){
        this.title = title == null ? "" : title;
        this.author = author == null ? "" : author;
        this.publisher = publisher == null ? "" : publisher;
        this.synopsis = synopsis;
        this.releaseYear = releaseYear;
        this.isbn = isbn;

    }


    
    /**
     * -----------------------------------[GETTER METHODS]----------------------------------
     * This is a method to get the private title field
     * @return returns book title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * method to access the private author field
     * @return author of book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * method to access private publisher field 
     * @return publisher of book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * method to access private release year field
     * @return release year of book
     */
    public long getReleaseYear() {
    return releaseYear;
    }

    /**
     * Method to access private ISBN field 
     * @return ISBN of book
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * method to access private synopsis field
     * @return synopsis of book
     */
    public String getSynopsis() {
        return synopsis;
    }



    /**
     * ---------------------------------[OVERRIDE METHODS]-------------------------
     * The following methods overrides defaults of class, extended classes, and implemented interfaces
     */
    @Override
    public String toString() {
        return "\n" + title + " by" + author + "\nPublished by" + publisher + "\nRelease year : "
                + releaseYear + "\nISBN : " + isbn + "\n \n Synopsis : \n  " + synopsis;
    }

    @Override
    public int compareTo(Book o) {
        return title.compareToIgnoreCase(o.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(!(o instanceof Book)) return false;
        Book book = (Book)o;
        return Objects.equals(title, book.getTitle());
    }

    @Override
        public int hashCode(){
        return Objects.hash(releaseYear, title);
    }

    

}
