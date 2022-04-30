package ch.uzh.ifi.hase.soprafs22.constant;

public enum PlaybackDuration {
    TEN(10), TWELVE(12), FOURTEEN(14), SIXTEEN(16), EIGHTEEN(18), TWENTY(20);

    private final int value;

    PlaybackDuration(int value) {
        this.value = value;
    }

    public int getPlayBackDuration() {
        return value;
    }
}
