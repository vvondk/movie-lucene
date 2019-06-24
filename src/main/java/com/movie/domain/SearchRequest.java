package com.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TotalHits;

@AllArgsConstructor
@Getter
public class SearchRequest {
    Query query;
    IndexSearcher indexSearcher;
    TotalHits totalHits;
    ScoreDoc[] hits;
    Analyzer analyzer;
}
