package com.movie.analyzer;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

public class NGramAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
//        Tokenizer tokenizer = new WhiteSpaceNGramTokenizer(2, 3);

        Tokenizer tokenizer = new WhitespaceTokenizer();
        TokenStream tokenStream = new WhiteSpaceNGramTokenFilter(tokenizer, 2, 3,  false);

        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}
