package com.spring.sakura.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.sakura.DTO.LoginDTO;
import com.spring.sakura.dao.AddressRepository;
import com.spring.sakura.dao.LoginRepository;
import com.spring.sakura.entities.Address;
import com.spring.sakura.entities.Users;
import com.spring.sakura.service.IAddressService;
import com.spring.sakura.service.ILoginService;
import jakarta.validation.Valid;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	public ILoginService userService;
	
	@Autowired
    private LoginRepository loginRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@GetMapping("/login")
	public String loginPageHandler(HttpSession session) {
		System.out.println("In login Page Handler");
		if (session.getAttribute("loggedInUser") != null) {
	        return "redirect:/user/account";  // already logged in → redirect
	    }
		return "loginPage";
	}
	
	@PostMapping("/loginUser")
	public String loginUser(@ModelAttribute("user") LoginDTO loginDTO, HttpSession session, Model model){
		System.out.println("In login User method");
		Users user = userService.findByEmail(loginDTO.getEmail());
		
		if(user == null) {
			model.addAttribute("error", "Invalid email or password");
			System.out.println("Method 1");
	        return "loginPage";
		}
		
		if ("google".equals(user.getProvider())) {
	        model.addAttribute("error", "This account was registered with Google. Please use Google Sign-In.");
	        return "loginPage";
	    }
		
		if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
			model.addAttribute("error", "Invalid email or password");
			System.out.println("Raw password from form = " + loginDTO.getPassword());
			System.out.println("Hashed password from DB = " + user.getPassword());
			System.out.println("Matches? " + passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()));
			System.out.println("Method 2");
	        return "loginPage";
		}
		
		// Save user in session
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
	    
	    if(user.getRole().equals("ADMIN")) {
	    	return "redirect:/admin/home";
	    }
	    else {
	    	return "redirect:/user/account";
	    }	
	}
	
	@GetMapping("/user/account")
	public String userAccount(HttpSession session, Model model) {
	    Users user = (Users) session.getAttribute("loggedInUser");
	    if (user == null || !user.getRole().equals("USER")) {
	        return "redirect:/login";
	    }
	    model.addAttribute("user", user);
	    return "account";
	}
	
	@GetMapping("/registerUser")
	public String registerUser(Model model) {
		System.out.println("In Register user handler");
		
		model.addAttribute("users",new Users());
		return "register";
	}
	
	@PostMapping("/process_register")
	public String processFormCreation(@Valid @ModelAttribute("users") Users user, BindingResult result) {
		System.out.println("This is process form creation page");
		
		if(result.hasErrors()) {
			System.out.println(result);
			return "register";
		}
		
		System.out.println(user);
		
		// hash the password before saving
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
		loginRepository.save(user);
		return "account";
	}
	
	@GetMapping("/generatePassword")
	@ResponseBody
	public String generatePassword() {
	    String rawPassword = "sakura@123";
	    String hashed = passwordEncoder.encode(rawPassword);
	    return "Hashed password for admin123: " + hashed;
	}
	
	//Below handlers are for Users profile.
	// ========== Profile Page ==========
	@GetMapping("/user-profile")
	public String userProfile(@RequestParam Long id, Model model) {
	    Users user = userService.getUserProfileData(id);

	    // Fetch all addresses
	    List<Address> addresses = addressRepository.findByUserIdOrderByIsPrimaryDesc(user.getId());

	    // For form binding (empty object)
	    Address newAddress = new Address();

	    model.addAttribute("addresses", addresses);
	    model.addAttribute("address", newAddress); // form binding
	  //below user model is used to display initials of name. For example Samyak Jain --> SJ
	    model.addAttribute("user", user);
	    model.addAttribute("name", user.getName());
	    model.addAttribute("email", user.getEmail());

	    return "profile";
	}

	// ========== Save / Update Address ==========
	@PostMapping("/addressForm")
	public String addressFormCreation(@ModelAttribute("address") Address address,
	                                  BindingResult result,
	                                  HttpSession session,
	                                  RedirectAttributes redirectAttributes) {
	    if (result.hasErrors()) {
	        return "profile";
	    }

	    // Logged-in user from session
	    Users user = (Users) session.getAttribute("loggedInUser");
	    if (user == null) {
	        return "redirect:/login"; // session expired or not logged in
	    }

	    try {
	        // Service layer handles insert/update + primary logic
	        addressService.saveAddress(address, user.getId());
	    } catch (DataIntegrityViolationException e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "This address already exists.");
	        return "redirect:/user-profile?id=" + user.getId();
	    }

	    return "redirect:/user-profile?id=" + user.getId();
	}


}
