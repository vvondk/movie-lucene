package underscore;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class UnderScoreTest {

    private Analyzer analyzer;
    public UnderScoreTest() {
        analyzer = new UnderScoreAnalyzer();
    }

    @Test
    public void testUnderScoreTokenizer() {
        String text = "hello_world_tokenizer_under_score";
        List<String> expectedList = Arrays.asList(new String[]{"hello", "world", "tokenizer", "under", "score"});
        List<String> list = analyzeText(text, analyzer);
        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(5);
        assertThat(list.get(0)).isEqualTo("hello");
        assertThat(list.toArray()).containsExactly(expectedList.toArray());
    }

    @Test
    public void testUnderScoreTokenizer2() {
        String text = "helloworldtokenizerunderscore";
        List<String> list = analyzeText(text, analyzer);
        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0)).isEqualTo("helloworldtokenizerunderscore");
    }


    public List<String> analyzeText(String text, Analyzer analyzer) {
        List list = new ArrayList();
        TokenStream tokenStream = analyzer.tokenStream("text", text);

        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        try {
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                list.add(charTermAttribute+"");
                System.out.print("'"+charTermAttribute+"'");
                System.out.print(" | ");
            }
            System.out.println();
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
