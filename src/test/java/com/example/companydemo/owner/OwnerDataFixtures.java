package com.example.companydemo.owner;

public class OwnerDataFixtures {

    public static Owner owner(String name) {
        return new Owner(name);
    }

    public static Owner owner() {
        return owner("Elon Musk");
    }
}
