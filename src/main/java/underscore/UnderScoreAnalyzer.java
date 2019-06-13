package underscore;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class UnderScoreAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new UnderScoreTokenizer();

        return new TokenStreamComponents(tokenizer);
    }
}
