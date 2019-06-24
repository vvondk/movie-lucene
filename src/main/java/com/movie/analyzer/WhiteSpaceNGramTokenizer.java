package com.movie.analyzer;

import org.apache.lucene.analysis.ngram.NGramTokenizer;

public class WhiteSpaceNGramTokenizer extends NGramTokenizer {

    WhiteSpaceNGramTokenizer(int minGram, int maxGram) {
        super(minGram, maxGram);
    }

    @Override
    protected boolean isTokenChar(int chr) {
        if(chr == ' ')
            return false;
        return true;
    }
}
