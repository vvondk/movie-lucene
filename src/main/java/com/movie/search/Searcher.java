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
        Query krQuery = new TermQuery(new Term("name", searchQuery));
        Query engQuery = new TermQuery(new Term("engName", searchQuery));

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(krQuery, BooleanClause.Occur.SHOULD);
        builder.add(engQuery, BooleanClause.Occur.SHOULD);

        Query query = builder.build();
        TopDocs docs = indexSearcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        logger.info("검색 결과 : "+ hits.length +" / "+ docs.totalHits);
        print(hits);
    }

    public void print(ScoreDoc[] hits){
        Arrays.stream(hits).forEach(hit -> {
             try {
                 int docId = hit.doc;
                 Document document = indexSearcher.doc(docId);

                 logger.info("docID : " + docId);
                 logger.info("name : " + document.get("name"));
                 logger.info("eng : " + document.get("engName"));
                 logger.info("-------------------------------");
             }catch (IOException e) {
                 logger.error("print error {}", e);
             }
        });
    }
}
