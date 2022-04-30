package ch.uzh.ifi.hase.soprafs22.utils;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.security.Principal;

public class IdentityHeader {

    public static String getIdentity(SimpMessageHeaderAccessor sha) {
        Principal p = sha.getUser();
        if (p != null) {
            return p.getName();
        }
        else {
            return null;
        }
    }
}
