package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import org.assertj.core.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionTest {
    Question testQuestion = new Question();
    @Test
    public void testQuestion(){
        ArrayList<String> testList = new ArrayList<>();
        testList.add("testy");

        testQuestion.setQuestion("Test");
        testQuestion.setPlaybackDuration(PlaybackDuration.EIGHTEEN);
        testQuestion.setSongTitle("Test Title");
        testQuestion.setPreviewUrl("test URL");
        testQuestion.setAlbumCovers(testList);
        testQuestion.setAnswers(testList);
        testQuestion.setCorrectAnswer(1);

        String correctTestAnswer = String.valueOf(testQuestion.getCorrectAnswer());

        ArrayList<String> questionActual = new ArrayList<>();
        Collections.addAll(
                questionActual, testQuestion.getQuestion(), testQuestion.getPlaybackDuration().toString(),
                testQuestion.getSongTitle(), testQuestion.getPreviewUrl(), testQuestion.getAlbumCovers().toString(),
                testQuestion.getAnswers().toString(), correctTestAnswer);

        ArrayList<String> questionExpected = new ArrayList<>();
        Collections.addAll(questionExpected, "Test", "EIGHTEEN", "Test Title", "test URL", "[testy]", "[testy]", "1");

        assertEquals(questionActual, questionExpected);
    }
}