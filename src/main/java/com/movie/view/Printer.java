package com.movie.view;

import com.movie.domain.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Printer {
    private static Logger logger = LoggerFactory.getLogger(Printer.class);

    public void print(SearchResult searchResult){

        long hitsLength = searchResult.getHitsLength();
        long totalHits = searchResult.getTotalHits();

        logger.info("검색 결과 : "+ hitsLength +" / "+ totalHits);

        StringBuffer sb = new StringBuffer();
        List docs = searchResult.getDocResults();
        docs.stream().forEach(doc -> {
            sb.append(doc+"\n");

        });

        logger.info("\n"+sb.toString());
    }
}
