package ch.uzh.ifi.hase.soprafs22.constant;

public enum SongPool {
    COUNTRY("37i9dQZF1DX1lVhptIYRda"),
    SWITZERLAND("37i9dQZEVXbJiyhoAPEfMK"),
    TOPHITSOFTHEDAY("37i9dQZF1DXcBWIGoYBM5M"),
    ROCK("37i9dQZF1DWXRqgorJj26U"),
    RNB("37i9dQZF1DX4SBhb3fqCJd"),
    HIPHOP(""),
    POP(""),
    LATINO(""),
    PERSONAL("");

    private String value;

    SongPool(String value){
        this.value = value;
    }

    public String getPlaylistId(){
        return value;
    }
}
