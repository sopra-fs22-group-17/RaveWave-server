package ch.uzh.ifi.hase.soprafs22.constant;

public enum RoundDuration {
	TEN(10), TWELVE(12), FOURTEEN(14), SIXTEEN(16), EIGHTEEN(18), TWENTY(20);

	private int value;

	RoundDuration(int value){
		this.value = value;
	}

	public int getEnumRoundDuration(){
		return value;
	}

}
