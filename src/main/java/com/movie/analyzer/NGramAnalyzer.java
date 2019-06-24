package com.movie.analyzer;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class NGramAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new WhiteSpaceNGramTokenizer(2, 3);

//        Tokenizer tokenizer = new WhitespaceTokenizer();
//        TokenStream tokenStream = new CustomNGramTokenFilter(tokenizer, 2, 3, true);

        return new TokenStreamComponents(tokenizer);
    }
}
