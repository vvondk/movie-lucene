package com.movie.search;

import com.movie.util.LuceneConfig;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SearcherTest {

    private Searcher searcher;
    public SearcherTest() {
        LuceneConfig luceneConfig = new LuceneConfig();
        searcher = new Searcher(luceneConfig.getIndexSearcher(), luceneConfig.getHitsPerPage());
    }

    @Test
    public void testBasic() {
        assertThat(searcher.getClass().getName()).isEqualTo("com.movie.search.Searcher");
    }

    public void testSearch() throws IOException {
        String searchValue = "토토";
    }
}
