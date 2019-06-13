package com.movie.index;

import com.movie.domain.Movie;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;

import java.util.Arrays;
import java.util.function.Function;

public class MovieIndexFunction implements Function<Movie, Document> {
    @Override
    public Document apply(Movie movie) {
        Document document = new Document();

        document.add(new IntPoint("key", movie.getKey()));

        FieldType fieldType = new FieldType();
        fieldType.setStored(true);
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setStoreTermVectorPositions(true);
        fieldType.setStoreTermVectorOffsets(true);
        fieldType.setStoreTermVectorPayloads(true);

        document.add(new Field("name", movie.getName(), fieldType));
        document.add(new Field("engName", movie.getEngName(), fieldType));

        if(movie.getProductionYear() != null) {
            document.add(new StringField("productionYear", DateTools.dateToString(movie.getProductionYear(), DateTools.Resolution.YEAR), Field.Store.YES));
        }

        movie.getProductionCountry()
                .forEach(country-> document.add(new StringField("productionCountry", (String)country, Field.Store.YES)));

        document.add(new StringField("type", movie.getType(), Field.Store.YES));

        movie.getGenre()
                .forEach(genre-> document.add(new StringField("genre", (String)genre, Field.Store.YES)));

        document.add(new StringField("productionStatus", movie.getProductionStatus(), Field.Store.YES));

        movie.getDirector()
                .forEach(director->document.add(new StringField("director", (String)director, Field.Store.YES)));

        movie.getProducer()
                .forEach(producer->document.add(new StringField("producer", (String)producer, Field.Store.YES)));

        return document;
    }
}
