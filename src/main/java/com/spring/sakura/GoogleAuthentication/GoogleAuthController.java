package com.spring.sakura.GoogleAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.sakura.entities.Users;
import com.spring.sakura.service.ILoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpSession; 
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController
@RequestMapping("/auth")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;
    
    @Autowired
	public ILoginService userService;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request, HttpSession session) {
        try {
            String idToken = request.getIdToken(); // Extract the ID token from the request
            System.out.println("In googleLogin method 1");
            // Verify the ID token with Google
            GoogleIdToken token = googleAuthService.verifyToken(idToken);

            // Extract user information from the token
            GoogleIdToken.Payload payload = token.getPayload();
            String userEmail = payload.getEmail();
            String userName = (String) payload.get("name");
            System.out.println("In googleLogin method 2");
            // Check if the user already exists in the database
            Users user = userService.findByEmail(userEmail);
            if (user == null) {
                // If the user doesn't exist, create a new user
            	System.out.println("In googleLogin method 3");
                user = new Users();
                user.setEmail(userEmail);
                user.setName(userName);
                user.setPassword("DUMMY_PASSWORD");
                user.setRole("USER");
                user.setProvider("google");
                userService.saveUser(user);
            }
            System.out.println("In googleLogin method 4");
            
            // Return a response (e.g., a JWT token or user details)
            // store in session
            session.setAttribute("loggedInUser", user);
            
         // Authenticate with Spring Security
         // 1. Convert your DB role into GrantedAuthority
            List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())
            );

            // 2. Wrap into UserDetails (Spring’s expected object)
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),      // username
                    user.getPassword(),   // password (dummy for Google login)
                    authorities           // role(s) from DB
            );

            // 3. Create Authentication token
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // no credentials needed
                    authorities
            );

            // 4. Store in SecurityContext so Spring knows the user is logged in
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            // 5. Very important: also bind to session
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // return redirect URL as JSON
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/user/account");
            return ResponseEntity.ok(response);

        } catch (GeneralSecurityException | IOException e) {
        	System.out.println("In googleLogin method 5");
            return ResponseEntity.status(400).body("Invalid token.");
        }
    }
}

