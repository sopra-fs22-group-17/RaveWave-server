package ch.uzh.ifi.hase.soprafs22.utils;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import java.lang.Math;

public class Evaluator {

    public int evaluation(Answer playerAnswer, int correctAnswer, RoundDuration roundDuration){
        //returns points and stores in the answer if it is right or wrong
        boolean answerResult = isCorrect(playerAnswer, correctAnswer);
        playerAnswer.setAnswerResult(answerResult);

		if (!answerResult) {
            return 0;
        }

		// Reward points to player if correctAnswer

        return calculatePoints(playerAnswer, roundDuration);
	}

	private static int calculatePoints(Answer answer, RoundDuration roundDuration){
		float pointsPossible = 1000;
		float respondsTime = answer.getAnswerTime();
		float maximumTime = roundDuration.getEnumRoundDuration();

		float points = (1 - ((respondsTime/maximumTime)/2)) * pointsPossible;

		return Math.round(points);
	}

    private boolean isCorrect(Answer playerAnswer, int correctAnswer) {
        int playerAnswerNr = playerAnswer.getAnswerNr();
        return correctAnswer == playerAnswerNr;
    }



}
