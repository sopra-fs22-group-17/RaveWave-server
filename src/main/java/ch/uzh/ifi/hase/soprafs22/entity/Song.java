package ch.uzh.ifi.hase.soprafs22.entity;

import se.michaelthelin.spotify.model_objects.specification.Track;

public class Song {
    private Track track;
    private long raveWaverId;

    public Song(Track track, long raveWaverId) {
        this.track = track;
        this.raveWaverId = raveWaverId;
    }

    public Track getTrack() {
        return this.track;
    }

    public long getIdentiy() {
        return this.raveWaverId;
    }
}
