package com.example.companydemo.owner;

import java.util.Arrays;
import java.util.List;

public class OwnerDataFixtures {

    public static Owner owner(String name) {
        return new Owner(name);
    }

    public static Owner owner() {
        return owner("Elon Musk");
    }

    public static List<Owner> owners() {
        return Arrays.asList(
                owner("Elon Musk"),
                owner("Richard Hendricks"),
                owner("Erlich Bachman"));
    }
}
