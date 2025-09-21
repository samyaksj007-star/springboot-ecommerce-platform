package com.spring.sakura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sakura.dao.AddressRepository;
import com.spring.sakura.entities.Address;

@Service
public class AddressServiceImpl implements IAddressService{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }
	
	@Override
	public Address saveAddress(Address address, Long userId) {
	    // normalize null to false
	    if (address.getIsPrimary() == null) {
	        address.setIsPrimary(false);
	    }

	    List<Address> existingAddresses = addressRepository.findByUserId(userId);

	    // Always link user
	    address.setUserId(userId);

	    // Case 1: First address → must be primary
	    if (existingAddresses.isEmpty()) {
	        address.setIsPrimary(true);
	        return addressRepository.save(address);
	    }

	    // Case 2: If this address is marked as primary → unset all others
	    if (Boolean.TRUE.equals(address.getIsPrimary())) {
	        for (Address addr : existingAddresses) {
	            if (addr.getId() != null && !addr.getId().equals(address.getId())) {
	                addr.setIsPrimary(false);
	            }
	        }
	        addressRepository.saveAll(existingAddresses);

	        // ensure this one stays primary
	        address.setIsPrimary(true);
	    }

	    // Case 3: If it's not primary → just save as is (old primary remains unchanged)
	    return addressRepository.save(address); // update if id exists, insert if new
	}



}
