package ch.uzh.ifi.hase.soprafs22.utils;

import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluatorTest {
    private Answer playerAnswer;

    @BeforeEach
    void setup() {
        playerAnswer = new Answer();
        playerAnswer.setAnswerTime(4.8f);
        playerAnswer.setplayerGuess(1);
        playerAnswer.setPlayerId(1L);
    }

    @Test
    public void evaluationTestCorrectAnswer() {
        int correctAnswer = 1;
        RoundDuration roundDuration = RoundDuration.TEN;

        assertEquals(760, Evaluator.evaluation(playerAnswer, correctAnswer, roundDuration));
    }

    @Test
    public void evaluationTestWrongAnswer() {
        int correctAnswer = 2;
        RoundDuration roundDuration = RoundDuration.TEN;

        assertEquals(0, Evaluator.evaluation(playerAnswer, correctAnswer, roundDuration));
    }
}