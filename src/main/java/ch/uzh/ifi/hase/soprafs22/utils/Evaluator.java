package ch.uzh.ifi.hase.soprafs22.utils;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import java.lang.Math;

public class Evaluator {

	public static int evaluate (Answer playerAnswers, Answer correctAnswer,
		RoundDuration roundDuration){
		// Check if answer is correct and saves the correctness to answerResult and returns answerResult (boolean)
		boolean answerResult = playerAnswers.isCorrectAndUpdateAnswerResult(correctAnswer);

		if (!answerResult) {
            return 0;
        }

		// Reward points to player if correctAnswer
		return calculatePoints(playerAnswers, roundDuration);
	}

	private static int calculatePoints(Answer answer, RoundDuration roundDuration){
		float pointsPossible = 1000;
		float respondsTime = answer.getAnswerTime();
		float maximumTime = roundDuration.getEnumRoundDuration();

		float points = (1 - ((respondsTime/maximumTime)/2)) * pointsPossible;

		return Math.round(points);
	}

}
