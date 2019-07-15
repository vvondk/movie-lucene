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

        /*
            TODO : preserveOriginal=true 인 경우 lastStartOffset이 startOffset보다 커서 실행 안됨
            Exception in thread "main" java.lang.IllegalArgumentException: startOffset must be non-negative,
            and endOffset must be >= startOffset, and offsets must not go backwards startOffset=0,endOffset=5,lastStartOffset=3 for field 'name'
         */

        TokenStream tokenStream = new WhiteSpaceNGramTokenFilter(tokenizer, 2, 3,   false);

        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}
