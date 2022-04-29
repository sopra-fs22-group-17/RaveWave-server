package ch.uzh.ifi.hase.soprafs22.utils;

import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;

public class Evaluator {

    public static int evaluation(Answer playerAnswer, int correctAnswer, RoundDuration roundDuration) {
        // returns points and stores in the answer if it is right or wrong
        boolean answerResult = isCorrect(playerAnswer, correctAnswer);

        if (!answerResult) {
            return 0;
        }

        // Reward points to player if correctAnswer

        return calculatePoints(playerAnswer, roundDuration);
    }

    private static int calculatePoints(Answer answer, RoundDuration roundDuration) {
        float pointsPossible = 1000;
        float respondsTime = answer.getAnswerTime();
        float maximumTime = roundDuration.getEnumRoundDuration();

        float multiplyBy = (float) (1.0 - ((respondsTime / maximumTime) / 2.0));
        System.out.println("multiplyBy:" + multiplyBy);

        float points = multiplyBy * pointsPossible;

        return (int) points;
    }

    private static boolean isCorrect(Answer playerAnswer, int correctAnswer) {
        int playersGuess = playerAnswer.getplayerGuess();
        return correctAnswer == playersGuess;
    }

}
