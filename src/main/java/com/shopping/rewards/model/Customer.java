package com.shopping.rewards.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a customer in the rewards system.
 */
@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String customerId;	

    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;
    
    private String phone;

    @PrePersist
    public void generateCustomerId() {
        if (this.customerId == null) {
            this.customerId = UUID.randomUUID().toString();
        }
    } 
    public Customer() {}
    
    public Customer(String customerId, String name, String email, String phone) { 
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    
}
