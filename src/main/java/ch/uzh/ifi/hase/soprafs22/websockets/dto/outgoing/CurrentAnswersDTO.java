package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class CurrentAnswersDTO {

    private final String type = "answerCount";
    private int currentAnswers;
    private int expectedAnswers;

    public String getType() {
        return type;
    }

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


    @Override
    public String toString() {
        return "{\n" +
                "\"currentAnswers\": \"" + currentAnswers + "\"" +
                "\"expectedAnswers\": \"" + expectedAnswers + "\"" +
                "},";
    }
}
