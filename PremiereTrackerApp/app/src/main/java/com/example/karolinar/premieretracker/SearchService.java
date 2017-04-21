package com.example.karolinar.premieretracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dominika on 21.04.2017.
 */

public class SearchService {

    public List<Product> findMovie(String title){
        MovieService movieService = new MovieService();
        List<Movie> moviesList = movieService.GetMoviesWhichContainTheTextInTitle(title);
        final List<Product> movieProducts = new ArrayList<>();

        for (Iterator<Movie> iter = moviesList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Movie movie = iter.next();
            product.setTitle(movie.getTitle());
            product.setPremiereDate(movie.getPremiereDate());
            movieProducts.add(product);
        }
        return movieProducts;
    }

    public List<Product> findBook(String title){
        BookService bookService = new BookService();
        List<Book> booksList = bookService.GetBooksWhichContainTheTextInTitle(title);
        final List<Product> bookProducts = new ArrayList<>();

        for (Iterator<Book> iter = booksList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Book book = iter.next();
            product.setTitle(book.getTitle());
            product.setPremiereDate(book.getPremiereDate());
            bookProducts.add(product);
        }
        return bookProducts;
    }

    public List<Product> findGame(String title){
        GameService gameService = new GameService();
        List<Game> gamesList = gameService.GetGamesWhichContainTheTextInTitle(title);
        final List<Product> gameProducts = new ArrayList<>();

        for (Iterator<Game> iter = gamesList.iterator(); iter.hasNext(); ) {
            Product product = new Product();
            Game game = iter.next();
            product.setTitle(game.getTitle());
            product.setPremiereDate(game.getPremiereDate());
            gameProducts.add(product);
        }
        return gameProducts;
    }
}
