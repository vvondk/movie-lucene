package com.movie.start;

import com.movie.domain.Movie;
import com.movie.index.Indexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Indexer indexer = new Indexer();
        List<Movie> movieList = indexer.parse();

        int index = 0;
        for(Movie movie : movieList){
            System.out.println(movie.toString());
            index++;
            if(index == 20)
                break;
        }

//        String cmd = "";
//        while(!(cmd = bufferedReader.readLine()).equals("0")){
//            System.out.println(cmd);
//        }
    }
}
