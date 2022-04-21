package ch.uzh.ifi.hase.soprafs22.websockets;

public class IncomingMessage {
    private String name;
    public String getName(){
        return name;
    }
    void setName(String name){
        this.name = name;
    }
    public IncomingMessage(String name){
        this.name = name;
    }
    public IncomingMessage(){}
}
