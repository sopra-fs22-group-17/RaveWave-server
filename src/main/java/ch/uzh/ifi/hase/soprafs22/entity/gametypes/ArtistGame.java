package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class ArtistGame implements GameType {
    private final Question question;

    private final ArrayList<Song> songs;
    private final int songToPick;
    private final ArrayList<Track> answerSongs;
    private final SpotifyService spotifyService;
    Logger log = LoggerFactory.getLogger(ArtistGame.class);

    public ArtistGame(int songToPick, ArrayList<Song> songs, SpotifyService spotifyService2) {
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;
        this.answerSongs = new ArrayList<>();
        this.spotifyService = spotifyService2;
        generateQuestion();


    }

    @Override
    public void generateQuestion() {
        question.setQuestion("Guess the song artist");
        // store id of the song to be played
        question.setPreviewUrl(songs.get(songToPick).getTrack().getPreviewUrl());
        question.setSpotifyLink(songs.get(songToPick).getTrack().getExternalUrls().getExternalUrls().get("spotify"));


        StringBuilder correctAnswer = new StringBuilder();
        for (ArtistSimplified artist : songs.get(songToPick).getTrack().getArtists()) {
            correctAnswer.append(artist.getName());
            correctAnswer.append(", ");
        }
        correctAnswer.delete(correctAnswer.length() - 2, correctAnswer.length());

        question.setArtist(correctAnswer.toString());

        ArrayList<String> answers = new ArrayList<>();

        ArrayList<Integer> wrongAnswersIndex = new ArrayList<>();

        for (int i = 0; i < songs.size(); i++) {
            wrongAnswersIndex.add(i);
        }

        Random rand;
        rand = new Random();
        int a = 3;
        for (int i = 0; i < a; i++) {
            // pick a random number to compute the wrong answers
            int wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));

            // ensures that there will never be the same answer twice
            while (wrongAnswerIndex == songToPick || Objects.equals(songs.get(wrongAnswerIndex).getTrack().getArtists()[0].getName(), songs.get(songToPick).getTrack().getArtists()[0].getName())) {
                wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));
            }
            StringBuilder answer = new StringBuilder();
            for (ArtistSimplified artist : songs.get(wrongAnswerIndex).getTrack().getArtists()) {
                answer.append(artist.getName());
                answer.append(", ");
            }
            answer.delete(answer.length() - 2, answer.length());

            if (answers.contains(answer.toString())) {
                a++;
            }
            else {
                answers.add(answer.toString());
                answerSongs.add(songs.get(wrongAnswerIndex).getTrack());
            }

        }

        int correctAnswerIndex = rand.nextInt(4);
        answers.add(correctAnswerIndex, correctAnswer.toString());
        answerSongs.add(correctAnswerIndex, songs.get(songToPick).getTrack());

        question.setAnswers(answers);
        question.setCorrectAnswer(correctAnswerIndex + 1);
        question.setGamemode(GameMode.ARTISTGAME);
        question.setPictures(getPictures());
        question.setSongTitle(songs.get(songToPick).getTrack().getName());
        question.setCoverUrl(songs.get(songToPick).getTrack().getAlbum().getImages()[1].getUrl());
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    public ArrayList<String> getPictures() {
        ArrayList<String> artistPictures = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String id = answerSongs.get(i).getArtists()[0].getId();

            try {
                if (!Objects.equals(spotifyService.getArtistProfilePicture(id), "")) {
                    artistPictures.add(spotifyService.getArtistProfilePicture(id));
                }
                else {
                    artistPictures.add(answerSongs.get(i).getAlbum().getImages()[1].getUrl());
                }
            }
            catch (IOException | ParseException | SpotifyWebApiException e) {
                e.printStackTrace();
            }
        }
        return artistPictures;
    }
}
