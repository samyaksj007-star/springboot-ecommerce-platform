package com.spring.sakura.GoogleAuthentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;

@Service
public class GoogleAuthService {
    private static final String CLIENT_ID = "599430361630-2mtdrdvsf6sj7k5fg14ksuldvanenmqv.apps.googleusercontent.com"; // Replace with your Google Client ID
    
    // Method to verify the Google ID Token
    public GoogleIdToken verifyToken(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID)) // The Client ID you created in Google Developer Console
                .build();

        GoogleIdToken token = verifier.verify(idToken);
        if (token != null) {
            return token;
        } else {
            throw new IOException("Invalid ID token.");
        }
    }
}
