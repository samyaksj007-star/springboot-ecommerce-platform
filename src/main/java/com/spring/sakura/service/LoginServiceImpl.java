package com.spring.sakura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sakura.dao.LoginRepository;
import com.spring.sakura.entities.Users;

@Service
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	public LoginRepository loginRepository;

	@Override
	public Users findByEmail(String email) {
		return loginRepository.findByEmail(email);
	}

	@Override
	public void saveUser(Users user) {
		loginRepository.save(user);
	}
	
	@Override
	public Users getUserProfileData(Long id) {
		return loginRepository.getUserProfileData(id);
	}

}
