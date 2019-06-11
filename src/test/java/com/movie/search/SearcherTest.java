package com.movie.search;

import com.movie.config.LuceneConfig;
import com.movie.domain.SearchResult;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SearcherTest {

    private Searcher searcher;
    public SearcherTest() {
        LuceneConfig luceneConfig = new LuceneConfig();
        searcher = new Searcher(luceneConfig.getIndexSearcher(), luceneConfig.getHitsPerPage(), luceneConfig.getQueryParser());
    }

    @Test
    public void testBasic() {
        assertThat(searcher.getClass().getName()).isEqualTo("com.movie.search.Searcher");
    }


    @Test
    public void searchKoreanQuery() throws IOException, ParseException {
        String searchQuery = "토토";
        SearchResult searchResult = searcher.search(searchQuery);
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getDocResults()).hasSize(10);
        assertThat(searchResult.getTotalHits().value).isEqualTo(887L);
    }
}
