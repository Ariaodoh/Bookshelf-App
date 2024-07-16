package com.ljmu.comp7501.eodoh.bookshelf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class BookShelf {


        /**
         * --------------------------------------[MAIN METHOD]-----------------------------------
         * This is the main method of the bookshelf program
         * This method reads a list of books from a file and creates a arraylist of books that can be 
         * manipulated by the user by calling a search and a sort method respectively
         * @param args
         */
        public static void main(String[] args){

                //Creates an array list of books available in program by calling the CreateBookShelf method
                List<Book> bookList = createBookShelf();
                 
                //Open a try-with-resource to manage scanner resource 
                try (Scanner scanner = new Scanner(System.in)) {
                        
                        //Loops menu with a while to keep user in program until desired exit
                       while (true) {
                                //Print welcome message and menu to console
                                System.out.println(" \nWelcome to the Bookshelf. \n1. SELECT A BOOK \n2. SORT AND SEARCH \n0. EXIT");

                                //assign user entry to Optional variable by calling the userOptionReader method 
                                Optional<Integer> option = userOptionReader(scanner);
                                //use functional option method to parse user choice and call required method
                                option.ifPresentOrElse(
                                        choice -> {
                                        if (choice == 1) {
                                                selectBook(scanner, bookList);
                                        } else if (choice == 2) {
                                                sortBookShelf(scanner, bookList);
                                        } else if (choice == 0) {
                                                System.out.println("\n \nExiting Bookshelf... \n ");
                                                System.exit(0);
                                        } else {
                                                System.out.println("\n \nInvalid Input, enter a number from the options");
                                        }
                                        },
                                        () -> System.out.println("\n \nInvalid Input, enter a number from the options")
                                );
                        }
                        
                }
  
        }

   
       


       
        /**
         * ------------------------------[USER OPTION READER METHOD]-------------------------------
         * This method reads console for user option selection and assigns value to an optional object 
         * method defaults to a 0 value when user entry in not a valid int.
         * @param scanner receives scanner object from main to read console input
         * @return returns optional object with interger value indicating user selection from menu printed to console 
         * in main method or a 0 value
         */
        public static Optional<Integer> userOptionReader(Scanner scanner) {
                return scanner.hasNextInt() ? Optional.of(scanner.nextInt()) : Optional.of(0);
        }





        /**
         * -------------------------------[CREATE BOOKSHELF METHOD]----------------------------------
         * This method create and populates an arraylist of books from a specified file 
         * this method adopts functional programming to edit file entries during transfer to 
         * arraylist
         * @return an arraylist of books read from file
         */
        public static List<Book> createBookShelf() {
                List<Book> bookShelf = new ArrayList<>();//creates arraylist
               
                //Create Path obj and get file from specified filepath
                Path path = Paths.get("books.csv");
               
                //Try-with-resource to manage file stream resource and handle possible exceptions 
                try (Stream<String> lines = Files.newBufferedReader(path).lines()) {
                        //add entries to arraylist. Split file lines a ',' to gets individial array items
                        bookShelf = lines.map(s -> s.split(","))
                        .map(arr -> {
                                String title = arr[0].toUpperCase(), author = arr[1], publisher = arr[2], synopsis = arr[3]; 
                                long year = Long.valueOf(StringUtils.remove(arr[4], ' '));
                                long isbn = Long.valueOf(StringUtils.remove(arr[5], ' ')); 

                                return new Book(title, author, publisher, synopsis, year, isbn);
                        })
                        .collect(Collectors.toList());
                } catch (IOException ex) {
                        System.out.println("Oops! Something went wrong with the file. Please check specifed file...");//handles IOException
                        ex.printStackTrace();
                        System.exit(0);

                } catch (NumberFormatException e){
                        System.out.println("Oops! Something is wrong with the file's data. Please ensure data complies with format required...");//handles NumberFormatException 
                        e.printStackTrace();
                        System.exit(0);
                }
               
                return bookShelf;//returns arraylist of books from file 
        }
        




        /**
         * -----------------------------------[SELECT BOOK METHOD]-------------------------------
         * This method defines algorithm to print a user specified book from the arraylist if present or print an 
         * error message if the book is not in the arraylist
         * This method prints the book details using the toString method defined in the book class
         * This accepts user entry from console with a passed in scanner object, limiting the need to open a new 
         * scanner object. Scanner resource in managed in the mani method.
         * @param scanner this is a scanner obj passed in when method is called
         * @param list this is an arraylist of books to be searched for user entry
         */
        public static void selectBook(Scanner scanner, List<Book> list){

                System.out.println(" \nEnter a Book title:...");

                if (scanner.hasNextLine()) {
                        scanner.nextLine();//this clears out scanner buffer
                        String userTitle = scanner.nextLine().trim().toUpperCase();
                        
                        Book findBook = new Book(userTitle);//The Book constructor with only title param is used here

                        list.stream().filter(e -> findBook.equals(e))
                                .findAny().ifPresentOrElse(System.out::println,
                        () -> System.out.println(" \nThere are no books matching this title"));

                } else {
                        System.out.println("Oops! Something went wrong: no book title was provided...");
                }
     
        }



        /**
         * ----------------------------------[SORT BOOKSHELF METHOD]--------------------------------------
         * This method prints a sorting menu to console and sorts a arraylist of books according
         * to the user specified sorting algorithm by calling the required sorting method
         * This calls the userOptiionReader to get user interger option for console. A scanner obj is passed
         * in as parameter to limit the need for the creation of a new scanner obj. Scanner obj is managed in
         * the main method 
         * @param scanner this is a scanner objpassed in when method is called
         * @param list this is an array of book obj passed in when method is called
         */
        public static void sortBookShelf(Scanner scanner, List<Book> list){
                //Print sorting menu to console
                System.out.println("\n \nSort Bookshelf by: \n1. Title \n2. Author \n3. Publisher \n4. Release Year \n5. ISBN");
                Optional<Integer> option = userOptionReader(scanner);//get user interge option from method
                
                option.ifPresentOrElse(
                choice -> {
                        if (choice == 1) {
                                System.out.println();//prints a blank line for spacing 
                                sortByTitle(list).forEach(System.out::println);//Print all books sorted by titles 

                        }else if (choice == 2) {
                                System.out.println();//prints a blank line for spacing 
                                sortByAuthor(list).forEach(System.out::println);//prints all books sorted by author
                                
                        }else if (choice == 3) {
                                System.out.println();//prints a blank line for spacing 
                                sortByPublisher(list).forEach(System.out::println);//prints all books sorted by publisher
                                
                        }else if (choice == 4) {
                                System.out.println();//prints a blank line for spacing 
                                sortByReleaseYear(list).forEach(System.out::println);//prints all books sorted by release year
                                
                        }else if (choice == 5) {
                                System.out.println();//prints a blank line for spacing 
                                sortByIsbn(list).forEach(System.out::println);//prints all books sorted by ISBN
                                
                        }else{
                                System.out.println();//prints a blank line for spacing 
                                System.out.println("Invalid entry. Please select an option from the menu...");
                        }

                        },
                        () -> System.out.println("Invalid entry. Please select an option from the menu...")
                
                );

        }

       
        
        /**
         * -----------------------------[SORT BY TITLE METHOD]----------------------------
         * This method sorts the arraylist of books passed in by the natural ordering of titles
         * @param list arraylist of books passed in when method is called
         * @return sorted arraylist of books 
         */
        public static List<Book> sortByTitle(List<Book> list){
                // Using comparing to sort books based on title
                List<Book> sortedList = list.stream()
                        .sorted(Comparator.comparing(Book::getTitle))
                        .collect(Collectors.toList());
                        return sortedList;
        }

        /**
         * -------------------------------[SORT BY AUTHOR METHOD]-----------------------------
         * This method sorts the arraylist of books passed in by author
         * @param list arraylist of books passed in when method is called
         * @return sorted arraylist of books
         */
        public static List<Book> sortByAuthor(List<Book> list){
                // Using comparing to sort books based on author
                List<Book> sortedList = list.stream()
                        .sorted(Comparator.comparing(Book::getAuthor))
                        .collect(Collectors.toList());
                        return sortedList;
        }

        /**
         * ----------------------------------[SORT BY PUBLISHER METHOD]---------------------------
         * This method sorts the arraylist of books passed in by publisher 
         * @param list arraylist of books passed in when method is called 
         * @return a sorted arraylist of books
         */
        public static List<Book> sortByPublisher(List<Book> list){
                // Using comparing to sort books based on publisher
                List<Book> sortedList = list.stream()
                        .sorted(Comparator.comparing(Book::getPublisher))
                        .collect(Collectors.toList());
                        return sortedList;
        }

        /**
         * --------------------------------[SORT BY RELEASE YEAR METHOD]-------------------------------
         * This method sorts the arraylist passed in by the release year of the book
         * @param list arraylist of books passed in when method is called 
         * @return sorted arraylist of books
         */
        public static List<Book> sortByReleaseYear(List<Book> list){
                // Using comparing to sort books based on release year
                List<Book> sortedList = list.stream()
                        .sorted(Comparator.comparing(Book::getReleaseYear))
                        .collect(Collectors.toList());
                        return sortedList;
        }

        /**
         * ------------------------------[SORT BY ISBN METHOD]----------------------------------------
         * This method sorts the arraylist of books passed in by the ISBN of the books
         * @param list arraylist of books passed in when method is called
         * @return sorted arraylist of books 
         */
        public static List<Book> sortByIsbn(List<Book> list){
                // Using comparing to sort books based on isbn
                List<Book> sortedList = list.stream()
                        .sorted(Comparator.comparing(Book::getIsbn))
                        .collect(Collectors.toList());
                        return sortedList;
        }



}
