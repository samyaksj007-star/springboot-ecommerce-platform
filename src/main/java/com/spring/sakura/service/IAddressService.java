package com.spring.sakura.service;

import java.util.List;

import com.spring.sakura.entities.Address;

public interface IAddressService {
	
	public List<Address> getAddressesByUserId(Long userId);
	
	public Address saveAddress(Address address, Long userId);

}
