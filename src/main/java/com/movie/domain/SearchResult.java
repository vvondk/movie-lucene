package com.movie.domain;

import lombok.Getter;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class SearchResult {
    private long totalHits;
    private long hitsLength;
    private List<DocResult> docResults;

    public SearchResult(IndexSearcher indexSearcher, long totalHits, ScoreDoc[] hits) {

        this.totalHits = totalHits;
        this.hitsLength = hits.length;
        this.docResults = new ArrayList<>();
        Arrays.stream(hits).forEach(hit -> {
            try {
                int docId = hit.doc;
                Document document = indexSearcher.doc(docId);
                docResults.add(new DocResult(docId, document.get("name"), document.get("engName")));
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
