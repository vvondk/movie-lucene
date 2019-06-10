package com.movie.search;

import com.movie.domain.DocResult;
import com.movie.domain.SearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Searcher {
    private static Logger logger = LoggerFactory.getLogger(Searcher.class);

    private IndexSearcher indexSearcher;
    private int hitsPerPage;
    private QueryParser queryParser;

    private Analyzer analyzer;


    public Searcher(IndexSearcher indexSearcher, int hitsPerPage, QueryParser queryParser) {
        this.indexSearcher = indexSearcher;
        this.hitsPerPage = hitsPerPage;
        this.queryParser = queryParser;

    }

    public Searcher(IndexSearcher indexSearcher, int hitsPerPage, QueryParser queryParser, Analyzer analyzer) {
        this.indexSearcher = indexSearcher;
        this.hitsPerPage = hitsPerPage;
        this.queryParser = queryParser;
        this.analyzer = analyzer;

    }

    public SearchResult search(String searchQuery) throws IOException, ParseException {

        Query krQuery = new TermQuery(new Term("name", searchQuery));
        Query engQuery = new TermQuery(new Term("engName", searchQuery));

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(krQuery, BooleanClause.Occur.SHOULD);
        builder.add(engQuery, BooleanClause.Occur.SHOULD);

        Query query = queryParser.parse(searchQuery);
        TopDocs docs = indexSearcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        SearchResult searchResult = new SearchResult(query, indexSearcher, docs.totalHits, hits, analyzer);

        return searchResult;
    }

}
