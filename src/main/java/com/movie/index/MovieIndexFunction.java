package com.movie.index;

import com.movie.domain.Movie;
import org.apache.lucene.document.*;

import java.util.function.Function;

public class MovieIndexFunction implements Function<Movie, Document> {
    @Override
    public Document apply(Movie movie) {
        Document document = new Document();

        document.add(new IntPoint("key", movie.getKey()));
        document.add(new TextField("name", movie.getName(), Field.Store.YES));
        document.add(new TextField("engName", movie.getEngName(), Field.Store.YES));

        if(movie.getProductionYear() != null)
            document.add(new StringField("productionYear", DateTools.dateToString(movie.getProductionYear(),DateTools.Resolution.YEAR), Field.Store.YES));

        for (Object country : movie.getProductionCountry()) {
            document.add(new StringField("productionCountry", (String)country, Field.Store.YES));
        }

        document.add(new StringField("type", movie.getType(), Field.Store.YES));

        for (Object genre : movie.getGenre()) {
            document.add(new StringField("genre", (String)genre, Field.Store.YES));
        }

        document.add(new StringField("productionStatus", movie.getProductionStatus(), Field.Store.YES));

        for (Object director : movie.getDirector()) {
            document.add(new StringField("director", (String)director, Field.Store.YES));
        }

        for (Object producer : movie.getProducer()) {
            document.add(new StringField("producer", (String)producer, Field.Store.YES));
        }
        return document;
    }
}
