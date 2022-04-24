package ch.uzh.ifi.hase.soprafs22.utils;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import java.lang.Math;

public class Evaluator {

	public static void evaluate (Answer playerAnswers, Answer correctAnswer,
		RoundDuration roundDuration){
		// Check if answer is correct and saves the correctness to answerResult and returns answerResult (boolean)
		boolean answerResult = playerAnswers.isCorrectAndUpdateAnswerResult(correctAnswer);

		// Reward points to player if correctAnswer
		rewardPoints(playerAnswers, answerResult, roundDuratian);
	}

	private static void rewardPoints (Answer answer, boolean answerResult, RoundDuration roundDuration){
		if (!answerResult) {
			return 0;
		}

		Player player = PlayerService.getPlayerById(answer.getPlayerId());

		player.roundResult(answer, calculatePoints(answer, roundDuration), answerResult)
	}

	private static int calculatePoints(Answer answer, RoundDuration roundDuration){
		float pointsPossible = 1000;
		float respondsTime = answer.getAnswerTime();
		float maximumTime = roundDuratian.getEnumRoundDuration();

		float points = (1 - ((respondsTime/maximumTime)/2)) * pointsPossible;

		return Math.round(points)
	}

}
