package com.spring.sakura.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class Users {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min=10 , message="Password should be more than 10 characters !!")
    private String password;

    @Column(name ="Full_Name")
    private String name;

    @Column(nullable = false)
    private String role = "USER"; // default role
    
    @Column(nullable = false)
    private String provider = "local"; // default
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
	private String lastName;
    
	private LocalDate dateOfBirth;
    
    @Column(nullable = false)
	private String mobileNumber;
    
    //This below method will help to insert the data in name column before inserting the full entry.
    @PrePersist
    @PreUpdate
    public void setFullNameBeforeSave() {
        if (firstName != null || lastName != null) {
            this.name = (firstName != null ? firstName : "") + 
                            (lastName != null ? " " + lastName : "");
        }
    }

    // Constructors
    public Users() {}

    public Users(Long id, String email,
			@Size(min = 10, message = "Password should be more than 10 characters !!") String password, String name,
			String role, String provider, String firstName, String lastName, LocalDate dateOfBirth,
			String mobileNumber) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
		this.provider = provider;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNumber;
	}


	// Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", role=" + role
				+ ", provider=" + provider + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", mobileNumber=" + mobileNumber + "]";
	}

}
