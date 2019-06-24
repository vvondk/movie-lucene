package com.movie.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenizer;

public class EdgeNGramAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new EdgeNGramTokenizer(1, 10);
//        Tokenizer tokenizer = new WhitespaceTokenizer();
        TokenFilter tokenFilter = new EdgeNGramTokenFilter(tokenizer, 1, 10, true);
        return new TokenStreamComponents(tokenizer, tokenFilter);
    }
}
