package ch.uzh.ifi.hase.soprafs22.utils;

import java.security.Principal;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public class IdentityHeader {

    public static String getIdentity(SimpMessageHeaderAccessor sha) {
        Principal p = sha.getUser();
        if (p != null) {
            return p.getName();
        } else {
            return null;
        }
    }
}
