package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class PlayerJoinDTO {
    public boolean likedGameModeUnlocked;
    private final static String type = "playerJoin";
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public boolean isLikedGameModeUnlocked() {
        return likedGameModeUnlocked;
    }

    public void setLikedGameModeUnlocked(boolean likedGameModeUnlocked) {
        this.likedGameModeUnlocked = likedGameModeUnlocked;
    }
}
