package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class PlayerJoinDTO {
    private String type = "playerJoin";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
