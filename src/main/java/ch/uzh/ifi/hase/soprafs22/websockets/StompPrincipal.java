package ch.uzh.ifi.hase.soprafs22.websockets;

import java.security.Principal;

class StompPrincipal implements Principal{
    String name;

    StompPrincipal(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }
}