package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class PlayerJoinDTO {
    private String type = "playerJoin";

    private String name;

    public boolean likedGameModeUnlocked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLikedGameModeUnlocked() {
        return likedGameModeUnlocked;
    }

    public void setLikedGameModeUnlocked(boolean likedGameModeUnlocked) {
        this.likedGameModeUnlocked = likedGameModeUnlocked;
    }
}
