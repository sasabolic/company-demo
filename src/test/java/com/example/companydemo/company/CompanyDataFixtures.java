package com.example.companydemo.company;

public class CompanyDataFixtures {


    public static Company company(String name, String address, String city, String country, String email, String phoneNumber) {
        return new Company(name, address, city, country, email, phoneNumber);
    }

    public static Company company() {
        return company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", "+16506815000");
    }
}
