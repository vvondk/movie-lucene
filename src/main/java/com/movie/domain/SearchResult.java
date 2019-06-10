package com.movie.domain;

import com.movie.analyzer.AnalyzerService;
import lombok.Getter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Formatter;

import java.io.IOException;
import java.util.*;

@Getter
public class SearchResult {
    private TotalHits totalHits;
    private long hitsLength;
    private List<DocResult> docResults;

    private Fragmenter fragmenter;
    private Formatter formatter = new SimpleHTMLFormatter();
    private Highlighter highlighter;
    private QueryScorer queryScorer;


    public SearchResult(Query query, IndexSearcher indexSearcher, TotalHits totalHits, ScoreDoc[] hits, Analyzer analyzer) {

        queryScorer = new QueryScorer(query);
        highlighter = new Highlighter(formatter, queryScorer);
        fragmenter = new SimpleSpanFragmenter(queryScorer, 10);
        highlighter.setTextFragmenter(fragmenter);

        this.totalHits = totalHits;
        this.hitsLength = hits.length;
        this.docResults = new ArrayList<>();

        AnalyzerService analyzerService = new AnalyzerService();
        Arrays.stream(hits).forEach(hit -> {
            try {
                int docId = hit.doc;
                Document document = indexSearcher.doc(docId);
                TokenStream tokenStream = analyzer.tokenStream("name", document.get("name"));
                String highlightedName = highlighter.getBestFragment(tokenStream, document.get("name"));

                tokenStream = analyzer.tokenStream("engName", document.get("engName"));
                String highlightedEngName = highlighter.getBestFragment(tokenStream, document.get("engName"));


                if(highlightedName != null)
                    System.out.println(highlightedName);
                else
                    System.out.println(document.get("name"));

                if(highlightedEngName != null)
                    System.out.println(highlightedEngName);
                else
                    System.out.println(document.get("engName"));

                DocResult docResult = new DocResult(docId, document.get("name"), document.get("engName"));
                analyzerService.analyzeText(docResult, analyzer);

                docResults.add(docResult);
            } catch (IOException | InvalidTokenOffsetsException e) {
                e.printStackTrace();
            }
        });
    }


}
