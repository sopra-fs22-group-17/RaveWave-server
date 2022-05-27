package ch.uzh.ifi.hase.soprafs22.constant;

public enum RoundDuration {
    THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11),
    TWELVE(12), THIRTEEN(13), FOURTEEN(14), FIFTEEN(15) ,SIXTEEN(16), SEVENTEEN(17), EIGHTEEN(18),
    NINETEEN(19),TWENTY(20);

    private final int value;

    RoundDuration(int value) {
        this.value = value;
    }

    public int getEnumRoundDuration() {
        return value;
    }

}
