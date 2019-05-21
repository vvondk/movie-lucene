package com.movie.view;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Printer {
    private static Logger logger = LoggerFactory.getLogger(Printer.class);

    public void print(Map searchResult){

        IndexSearcher indexSearcher = (IndexSearcher) searchResult.get("indexSearcher");
        long totalHits = (Long) searchResult.get("totalHits");
        ScoreDoc[] hits = (ScoreDoc[]) searchResult.get("hits");

        logger.info("검색 결과 : "+ hits.length +" / "+ totalHits);
        Arrays.stream(hits).forEach(hit -> {
            try {
                logger.info("==================================");
                int docId = hit.doc;
                Document document = indexSearcher.doc(docId);

                logger.info("docID : " + docId);
                logger.info("name : " + document.get("name"));
                logger.info("eng : " + document.get("engName"));
                logger.info("-------------------------------");
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
