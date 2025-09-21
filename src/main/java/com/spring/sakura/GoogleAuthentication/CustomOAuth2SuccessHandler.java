//package com.spring.sakura.GoogleAuthentication;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import com.spring.sakura.entities.Users;
//import com.spring.sakura.service.ILoginService;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//	@Autowired
//	public ILoginService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//        Map<String, Object> attributes = token.getPrincipal().getAttributes();
//
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//
//        Users userOpt = userService.findByEmail(email);
//
//        if (userOpt == null) {
//            // New user, insert into DB
//            Users newUser = new Users();
//            newUser.setEmail(email);
//            newUser.setName(name);
//            newUser.setPassword(""); // leave blank or set dummy
//
//            userService.saveUser(newUser);
//        }
//
//        // Redirect to home page or dashboard
//        response.sendRedirect("/home"); // or wherever
//    }
//}
//
