package com.spring.sakura.service;

import com.spring.sakura.entities.Users;

public interface ILoginService {
	
	public Users findByEmail(String email);
	
	public void saveUser(Users user);
	
	public Users getUserProfileData(Long id);

}
