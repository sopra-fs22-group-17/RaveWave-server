package ch.uzh.ifi.hase.soprafs22.entity;

import se.michaelthelin.spotify.model_objects.specification.Track;

public class Song {
    private Track track;
    private long raveWaverId;
    private String playerName;

    public Song(Track track, long raveWaverId, String playerName) {
        this.track = track;
        this.raveWaverId = raveWaverId;
        this.playerName = playerName;
    }

    public Song(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return this.track;
    }

    public long getIdentiy() {
        return this.raveWaverId;
    }

    public String getPlayerName() {
        return this.playerName;
    }
}
