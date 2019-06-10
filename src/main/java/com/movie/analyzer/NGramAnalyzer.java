package com.movie.analyzer;


import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;

public class NGramAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
//        Tokenizer tokenizer = new NGramTokenizer(2, 3);
        Tokenizer tokenizer = new WhitespaceTokenizer();

        CharArraySet charArraySet = StopFilter.makeStopSet(new String[]{"은", "는", "이", "가", "의", ":"});
        TokenStream tokenStream = new StopFilter(tokenizer, charArraySet);
        tokenStream = new NGramTokenFilter(tokenStream, 2, 3, true);
        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}
