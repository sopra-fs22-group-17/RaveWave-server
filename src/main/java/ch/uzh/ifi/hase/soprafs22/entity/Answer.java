package ch.uzh.ifi.hase.soprafs22.entity;

public class Answer {
	private Long playerId;
	private String playerAnswer;
	private int answerId;
	private float answerTime;
	private boolean answerResult;

	public boolean isCorrectAndUpdateAnswerResult(Answer correctAnswer) {
		this.answerResult = this.isEqualAnswerId(correctAnswer);
		return this.answerResult;
	}

	private boolean isEqualAnswerId(Answer correctAnswer) {
		return correctAnswer.getAnswerId() == this.getAnswerId();
	}

	public float getAnswerTime() {
		return this.answerTime;
	}

	public int getAnswerId() {
		return this.answerId;
	}

	public int getPlayerId() {
		return this.playerId;
	}

}
