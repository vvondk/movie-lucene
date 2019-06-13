package underscore;

import org.apache.lucene.analysis.util.CharTokenizer;

public class UnderScoreTokenizer extends CharTokenizer {

    @Override
    protected boolean isTokenChar(int c) {
        if('_' == c)
            return false;
        return true;
    }
}
