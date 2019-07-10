package com.movie.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramAnalyzerTest {
    private Analyzer analyzer;

    public NGramAnalyzerTest() {
        analyzer = new NGramAnalyzer();
    }

    @Test
    public void testTokenFilter(){
        String text = "abcdef";
        List<String> list = analyzeText(text, analyzer);
        assertThat(list).isNotEmpty();
    }

    @Test
    public void testTokenFilter2(){
        String text = "이웃집 토토로";
        List<String> list = analyzeText(text, analyzer);
        assertThat(list).isNotEmpty();
    }

    @Test
    public void testTokenFilter3(){
        String text = "매니페스토";
        List<String> list = analyzeText(text, analyzer);
        assertThat(list).isNotEmpty();

        analyzeText("테스트 진행중", analyzer);
    }

    public List<String> analyzeText(String text, Analyzer analyzer) {
        List list = new ArrayList();
        TokenStream tokenStream = analyzer.tokenStream("text", text);

        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);

        try {
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                list.add(charTermAttribute+"");
                System.out.print("'"+charTermAttribute+"'");
                System.out.print(" | ");

                System.out.println(offsetAttribute.startOffset() +" , "+ offsetAttribute.endOffset());
            }
            //System.out.println();
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
