package com.movie.analyzer;

// https://github.com/JAVACAFE-STUDY/elasticbooks/blob/master/project/lucene/chapter4/twitter-search/src/main/java/service/AnalyzerService.java

import com.movie.domain.DocResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class AnalyzerService {
    public void analyzeText(DocResult docResult, Analyzer analyzer){
        if(docResult.getTitle() != null) {

            System.out.println(docResult.getTitle());
            TokenStream tokenStream = analyzer.tokenStream("name", docResult.getTitle());

            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

            try {
                tokenStream.reset();

                while (tokenStream.incrementToken()) {
                    System.out.print("'"+charTermAttribute+"'");
                    System.out.print(" | ");
                }
                System.out.println();
                tokenStream.end();
                tokenStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println();

        }

    }
}
