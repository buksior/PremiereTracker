package com.example.karolinar.premieretracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dominika on 21.04.2017.
 */

public class SearchService {

    public List<Product> findMovieByTitle(String title){
        MovieService movieService = new MovieService();
        List<Movie> moviesList = movieService.GetMoviesWhichContainTheTextInTitle(title);
        final List<Product> movieProducts = new ArrayList<>();

        for (Iterator<Movie> iter = moviesList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Movie movie = iter.next();
            product.setTitle(movie.getTitle());
            product.setPremiereDate(movie.getPremiereDate());
            product.setProductType(movie.getProductType());
            product.setAuthor(movie.getAuthor());
            product.setDescription(movie.getDescription());
            movieProducts.add(product);
        }
        return movieProducts;
    }

    public List<Product> findMovieByDirector(String director){
        MovieService movieService = new MovieService();
        List<Movie> moviesList = movieService.GetMoviesWhichContainTheTextInDirector(director);
        final List<Product> movieProducts = new ArrayList<>();

        for (Iterator<Movie> iter = moviesList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Movie movie = iter.next();
            product.setTitle(movie.getTitle());
            product.setPremiereDate(movie.getPremiereDate());
            product.setProductType(movie.getProductType());
            product.setAuthor(movie.getAuthor());
            product.setDescription(movie.getDescription());
            movieProducts.add(product);
        }
        return movieProducts;
    }

    public List<Product> findBookByTitle(String title){
        BookService bookService = new BookService();
        List<Book> booksList = bookService.GetBooksBeforePremiereWhichContainTheTextInTitle(title);
        final List<Product> bookProducts = new ArrayList<>();

        for (Iterator<Book> iter = booksList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Book book = iter.next();
            product.setTitle(book.getTitle());
            product.setPremiereDate(book.getPremiereDate());
            product.setProductType(book.getProductType());
            product.setAuthor(book.getAuthor());
            product.setDescription(book.getDescription());
            bookProducts.add(product);
        }
        return bookProducts;
    }

    public List<Product> findBookByAuthor(String author){
        BookService bookService = new BookService();
        List<Book> booksList = bookService.GetBooksBeforePremiereWhichContainTheTextInAuthor(author);
        final List<Product> bookProducts = new ArrayList<>();

        for (Iterator<Book> iter = booksList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Book book = iter.next();
            product.setTitle(book.getTitle());
            product.setPremiereDate(book.getPremiereDate());
            product.setProductType(book.getProductType());
            product.setAuthor(book.getAuthor());
            product.setDescription(book.getDescription());
            bookProducts.add(product);

        }
        return bookProducts;
    }

    public List<Product> findGameByTitle(String title){
        GameService gameService = new GameService();
        List<Game> gamesList = gameService.GetGamesWhichContainTheTextInTitle(title);
        final List<Product> gameProducts = new ArrayList<>();

        for (Iterator<Game> iter = gamesList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Game game = iter.next();
            product.setTitle(game.getTitle());
            product.setPremiereDate(game.getPremiereDate());
            product.setProductType(game.getProductType());
            product.setAuthor(game.getAuthor());
            product.setDescription(game.getDescription());
            gameProducts.add(product);
        }
        return gameProducts;
    }

    public List<Product> findGameByStudio(String studio){
        GameService gameService = new GameService();
        List<Game> gamesList = gameService.getGamesWhichContainsTheTextInAuthor(studio);
        final List<Product> gameProducts = new ArrayList<>();

        for (Iterator<Game> iter = gamesList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Game game = iter.next();
            product.setTitle(game.getTitle());
            product.setPremiereDate(game.getPremiereDate());
            product.setProductType(game.getProductType());
            product.setAuthor(game.getAuthor());
            product.setDescription(game.getDescription());
            gameProducts.add(product);
        }
        return gameProducts;
    }

}
