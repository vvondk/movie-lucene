package com.movie.index;

import com.movie.domain.Movie;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Indexer {
    private static Logger logger = LoggerFactory.getLogger(Indexer.class);
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;

    public Indexer(Directory directory, IndexWriterConfig indexWriterConfig) {
        this.directory = directory;
        this.indexWriterConfig = indexWriterConfig;
    }

    public List<Movie> parse(String fileName) throws IOException {
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

    public boolean makeIndex(List<Movie> movieList) {
        try(IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            movieList.stream().forEach(movie -> {
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


                try {
                    indexWriter.addDocument(document);
                } catch (IOException e) {
                    logger.error("document add error {}", e);
                }

            });
        } catch (IOException e) {
            logger.error("indexWriter error {}", e);
            return false;
        }
        return true;
    }
}
