package com.jokecompany;

import com.google.gson.annotations.SerializedName;

public class PersonDTO {
	
	@SerializedName("name")
    private String firstName;
	@SerializedName("surname")
    private String lastName;
	private String gender;
	private String region;

    public PersonDTO(String firstName, String lastName, String gender, String region) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.region = region;
	}

	public String getFirstname() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
    
}
