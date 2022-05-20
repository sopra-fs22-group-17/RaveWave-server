package ch.uzh.ifi.hase.soprafs22.constant;

public enum SongPool {
    SWITZERLAND("37i9dQZEVXbJiyhoAPEfMK"),
    // https://open.spotify.com/playlist/37i9dQZF1DX4vth7idTQch?si=6da87e4fd7c74a23
    ROCK("37i9dQZF1DX4vth7idTQch"),
    HIPHOP("37i9dQZF1DXbkfWVLd8wE3"),
    // https://open.spotify.com/playlist/7jSif3WYmpCzKkTkKNykg1?si=7849755323ac4d08
    RAVEWAVESPECIAL("7jSif3WYmpCzKkTkKNykg1"),
    // https://open.spotify.com/playlist/0CFXqmgHlSnEzjSVMmZ24w?si=d9d28d2caea846a3
    PARTY("0CFXqmgHlSnEzjSVMmZ24w"),
    // https://open.spotify.com/playlist/37i9dQZF1DX7ZUug1ANKRP?si=ed597f24ca6243e8
    TECHNO("37i9dQZF1DX7ZUug1ANKRP"),

    // https://open.spotify.com/playlist/0q1di38xTtJS21GjOtaeNb?si=9ecf843e99c242b6
    LATIN("0q1di38xTtJS21GjOtaeNb"),
    // https://open.spotify.com/playlist/37i9dQZF1DX4o1oenSJRJd?si=2af4d69828bb4e03
    NOUGHTIES("37i9dQZF1DX4o1oenSJRJd"),
    // https://open.spotify.com/playlist/29c6gYmiNOicHDieRrQONr?si=440c38d4b8a9471a
    NINETIES("29c6gYmiNOicHDieRrQONr"),
    // https://open.spotify.com/playlist/37i9dQZF1DX4UtSsGT1Sbe?si=59869cc268a74530
    EIGHTIES("37i9dQZF1DX4UtSsGT1Sbe"),

    USERSTOPTRACKS(""),
    USERSSAVEDTRACKS("");

    private final String value;

    SongPool(String value) {
        this.value = value;
    }

    public String getPlaylistId() {
        return value;
    }
}
