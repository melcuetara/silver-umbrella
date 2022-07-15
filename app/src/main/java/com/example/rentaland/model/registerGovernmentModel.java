package com.example.rentaland.model;

public class registerGovernmentModel {
    private String BarangayName;
    private String firstName,lastName,age,gender,contactNumber,imageUrl;

    public registerGovernmentModel(String barangayName, String firstName, String lastName, String age, String gender, String contactNumber, String imageUrl) {
        BarangayName = barangayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.imageUrl = imageUrl;
    }

    public String getBarangayName() {
        return BarangayName;
    }

    public void setBarangayName(String barangayName) {
        BarangayName = barangayName;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
