package ch.uzh.ifi.hase.soprafs22.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;

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

        assertEquals(Evaluator.evaluation(playerAnswer, correctAnswer, roundDuration), 760);
    }

    @Test
    public void evaluationTestWrongAnswer() {
        int correctAnswer = 2;
        RoundDuration roundDuration = RoundDuration.TEN;

        assertEquals(Evaluator.evaluation(playerAnswer, correctAnswer, roundDuration), 0);
    }
}