package com.example.companydemo.owner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OwnerDataFixtures {

    public static Owner owner(String name) {
        return new Owner(name);
    }

    public static Owner owner() {
        return owner("Elon Musk");
    }

    public static Set<Owner> owners() {
        return Stream.of(
                owner("Elon Musk"),
                owner("Richard Hendricks"),
                owner(" Erlich Bachman")
        ).collect(Collectors.toSet());
    }
}
