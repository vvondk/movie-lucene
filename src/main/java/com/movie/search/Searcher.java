package com.movie.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class Searcher {
    private static Logger logger = LoggerFactory.getLogger(Searcher.class);

    private IndexSearcher indexSearcher;
    private int hitsPerPage;

    public Searcher(IndexSearcher indexSearcher, int hitsPerPage) {
        this.indexSearcher = indexSearcher;
        this.hitsPerPage = hitsPerPage;
    }

    public void search(String searchQuery) throws IOException {
        Query query = new TermQuery(new Term("name", searchQuery));

        TopDocs docs = indexSearcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        logger.info("검색 결과 : "+ hits.length +" / "+ docs.totalHits);
        print(hits);
    }

    public void print(ScoreDoc[] hits){
        Arrays.stream(hits).forEach(hit -> {
             try {
                 int docId = hit.doc;
                 logger.info("docID : " + docId);
                 Document document = indexSearcher.doc(docId);
                 logger.info(document.get("name"));
                 logger.info(document.get("engName"));
                 logger.info("-------------------------------");
             }catch (IOException e) {
                 e.printStackTrace();
             }
        });
    }
}
