package com.movie.domain;

import lombok.Getter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.highlight.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class SearchResult {
    private TotalHits totalHits;
    private long hitsLength;
    private List<DocResult> docResults;

    private Fragmenter fragmenter;
    private Formatter formatter = new SimpleHTMLFormatter();
    private Highlighter highlighter;
    private QueryScorer queryScorer;


    public SearchResult(SearchRequest searchRequest) {

        queryScorer = new QueryScorer(searchRequest.getQuery());
        highlighter = new Highlighter(formatter, queryScorer);
        fragmenter = new SimpleSpanFragmenter(queryScorer, 10);
        highlighter.setTextFragmenter(fragmenter);

        Analyzer analyzer = searchRequest.getAnalyzer();
        IndexSearcher indexSearcher = searchRequest.getIndexSearcher();

        this.hitsLength = searchRequest.getHits().length;
        this.docResults = new ArrayList<>();

        this.totalHits = searchRequest.getTotalHits();

        //AnalyzerService analyzerService = new AnalyzerService();

        Arrays.stream(searchRequest.getHits()).forEach(hit -> {
            try {
                int docId = hit.doc;
                Document document = indexSearcher.doc(docId);
                TokenStream tokenStream = analyzer.tokenStream("name", document.get("name"));
                String highlightedName = highlighter.getBestFragment(tokenStream, document.get("name"));

                tokenStream = analyzer.tokenStream("engName", document.get("engName"));
                String highlightedEngName = highlighter.getBestFragment(tokenStream, document.get("engName"));

                DocResult docResult = new DocResult(docId, document.get("name"), document.get("engName"), highlightedName, highlightedEngName);
                //analyzerService.analyzeText(docResult, analyzer);

                docResults.add(docResult);
            } catch (IOException | InvalidTokenOffsetsException e) {
                e.printStackTrace();
            }
        });
    }


}
