package com.movie.index;

import com.movie.domain.Movie;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
import java.util.function.Function;

public class Indexer {
    private static Logger logger = LoggerFactory.getLogger(Indexer.class);
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;

    public Indexer(Directory directory, IndexWriterConfig indexWriterConfig) {
        this.directory = directory;
        this.indexWriterConfig = indexWriterConfig;
    }

    public boolean run(String fileName, Function<Object, Document> function, Class clazz) throws IOException {
        boolean result = false;

        logger.info("start indexing...");
        long indexStartTime = System.currentTimeMillis();

        List list = parse(fileName, clazz);
        result = make(list, function, clazz);

        long indexEndTime = System.currentTimeMillis();
        logger.info("completed indexing  > "+ (indexEndTime - indexStartTime) + " ms");

        return result;
    }

    private List parse(String fileName, Class clazz) throws IOException {
        List list = null;

        try(Reader reader = Files.newBufferedReader(Paths.get(fileName))) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            list = csvToBean.parse();
        }

        return list;
    }

    private boolean make(List list, Function<Object, Document> function, Class clazz) {
        try(IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            list.stream().forEach(item -> {

                Document document = function.apply(item);
                try {
                    indexWriter.addDocument(document);
                } catch (IOException e) {
                    logger.error("document add error", e);
                }

            });
        } catch (IOException e) {
            logger.error("indexWriter error", e);
            return false;
        }
        return true;
    }


}
