package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class CurrentAnswersDTO {

    private int currentAnswers;
    private int expectedAnswers;

    public int getCurrentAnswers() {
        return currentAnswers;
    }

    public void setCurrentAnswers(int currentAnswers) {
        this.currentAnswers = currentAnswers;
    }

    public int getExpectedAnswers() {
        return expectedAnswers;
    }

    public void setExpectedAnswers(int expectedAnswers) {
        this.expectedAnswers = expectedAnswers;
    }
}
