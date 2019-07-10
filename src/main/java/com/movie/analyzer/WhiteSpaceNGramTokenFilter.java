package com.movie.analyzer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;

public class WhiteSpaceNGramTokenFilter extends TokenFilter {
    public static final boolean DEFAULT_PRESERVE_ORIGINAL = false;

    private final int minGram;
    private final int maxGram;
    private final boolean preserveOriginal;

    private char[] curTermBuffer;
    private int curTermLength;
    private int curTermCodePointCount;
    private int curGramSize;
    private int curPos;
    private int curPosIncr;
    private State state;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);

    // add
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private int preTokenEndOffset;

    /**
     * Creates an NGramTokenFilter that, for a given input term, produces all
     * contained n-grams with lengths &gt;= minGram and &lt;= maxGram. Will
     * optionally preserve the original term when its length is outside of the
     * defined range.
     *
     * Note: Care must be taken when choosing minGram and maxGram; depending
     * on the input token size, this filter potentially produces a huge number
     * of terms.
     *
     * @param input {@link TokenStream} holding the input to be tokenized
     * @param minGram the minimum length of the generated n-grams
     * @param maxGram the maximum length of the generated n-grams
     * @param preserveOriginal Whether or not to keep the original term when it
     * is shorter than minGram or longer than maxGram
     */
    public WhiteSpaceNGramTokenFilter(TokenStream input, int minGram, int maxGram, boolean preserveOriginal) {
        super(input);
        if (minGram < 1) {
            throw new IllegalArgumentException("minGram must be greater than zero");
        }
        if (minGram > maxGram) {
            throw new IllegalArgumentException("minGram must not be greater than maxGram");
        }
        this.minGram = minGram;
        this.maxGram = maxGram;
        this.preserveOriginal = preserveOriginal;
    }

    /**
     * Creates an NGramTokenFilter that produces n-grams of the indicated size.
     *
     * @param input {@link TokenStream} holding the input to be tokenized
     * @param gramSize the size of n-grams to generate.
     */
    public WhiteSpaceNGramTokenFilter(TokenStream input, int gramSize) {
        this(input, gramSize, gramSize, DEFAULT_PRESERVE_ORIGINAL);
    }

    @Override
    public final boolean incrementToken() throws IOException {

        while (true) {
            if (curTermBuffer == null) {
                if (!input.incrementToken()) {
                    // add
                    // 하나의 필드값 색인 생성 후 초기화 해주는 작업 필요, 안그러면 이어져서 실행됨
                    preTokenEndOffset = 0;

                    return false;
                }
                state = captureState();

                curTermLength = termAtt.length();
                curTermCodePointCount = Character.codePointCount(termAtt, 0, termAtt.length());

                curPosIncr += posIncrAtt.getPositionIncrement();
                curPos = 0;

                if (preserveOriginal && curTermCodePointCount < minGram) {
                    // Token is shorter than minGram, but we'd still like to keep it.
                    posIncrAtt.setPositionIncrement(curPosIncr);
                    curPosIncr = 0;
                    return true;
                }

                curTermBuffer = termAtt.buffer().clone();
                curGramSize = minGram;
            }

            if (curGramSize > maxGram || (curPos + curGramSize) > curTermCodePointCount) {
                ++curPos;
                curGramSize = minGram;
            }
            if ((curPos + curGramSize) <= curTermCodePointCount) {

                restoreState(state);
                final int start = Character.offsetByCodePoints(curTermBuffer, 0, curTermLength, 0, curPos);
                final int end = Character.offsetByCodePoints(curTermBuffer, 0, curTermLength, start, curGramSize);
                termAtt.copyBuffer(curTermBuffer, start, end - start);

                posIncrAtt.setPositionIncrement(curPosIncr);

                // add
                offsetAtt.setOffset(preTokenEndOffset+start, preTokenEndOffset+end);

                curPosIncr = 0;
                curGramSize++;
                return true;
            }
            else if (preserveOriginal && curTermCodePointCount > maxGram) {
                // Token is longer than maxGram, but we'd still like to keep it.
                restoreState(state);
                posIncrAtt.setPositionIncrement(0);
                termAtt.copyBuffer(curTermBuffer, 0, curTermLength);

                // add
                offsetAtt.setOffset(0, curTermLength);

                curTermBuffer = null;
                return true;
            }

            // Done with this input token, get next token on next iteration.
            curTermBuffer = null;

            // TODO : totalPosIncr(=)+1... stop filter처럼 토큰 처리되었을 경우...?
            // add
            preTokenEndOffset += (curTermLength+1);

        }
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        curTermBuffer = null;
        curPosIncr = 0;
    }

    @Override
    public void end() throws IOException {
        super.end();
        posIncrAtt.setPositionIncrement(curPosIncr);
    }
}
