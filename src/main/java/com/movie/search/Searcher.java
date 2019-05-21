package com.movie.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Searcher {
    private static Logger logger = LoggerFactory.getLogger(Searcher.class);

    private IndexSearcher indexSearcher;
    private int hitsPerPage;

    public Searcher(IndexSearcher indexSearcher, int hitsPerPage) {
        this.indexSearcher = indexSearcher;
        this.hitsPerPage = hitsPerPage;
    }

    public Map<String, Object> search(String searchQuery) throws IOException {
        Query krQuery = new TermQuery(new Term("name", searchQuery));
        Query engQuery = new TermQuery(new Term("engName", searchQuery));

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(krQuery, BooleanClause.Occur.SHOULD);
        builder.add(engQuery, BooleanClause.Occur.SHOULD);

        Query query = builder.build();
        TopDocs docs = indexSearcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        HashMap result = new HashMap();
        result.put("indexSearcher", indexSearcher);
        result.put("totalHits", docs.totalHits);
        result.put("hits", hits);
        return result;
    }

}
