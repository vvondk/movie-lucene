package com.movie.index;

import com.movie.domain.Movie;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Indexer {
    public List<Movie> parse() throws IOException {
        String fileName = "/Users/won/workspace/study-serving/movie.csv";

        List<Movie> movieList = null;

        try(Reader reader = Files.newBufferedReader(Paths.get(fileName))) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Movie.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            movieList = csvToBean.parse();
        }

        return movieList;
    }
}
