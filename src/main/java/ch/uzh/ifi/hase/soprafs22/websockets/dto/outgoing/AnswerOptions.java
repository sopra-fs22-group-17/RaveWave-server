package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import java.util.List;

public class AnswerOptions {
    private String answers;
    private int answerId;
    private String albumPicture;

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAlbumPicture() {
        return albumPicture;
    }

    public void setAlbumPicture(String albumPicture) {
        this.albumPicture = albumPicture;
    }


}
