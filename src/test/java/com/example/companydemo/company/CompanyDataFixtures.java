package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CompanyDataFixtures {


    public static Company company(String name, String address, String city, String country, String email, String phoneNumber, Set<Owner> owners) {
        return new Company(name, address, city, country, email, phoneNumber, owners);
    }

    public static Company company() {
        return company("Tesla",
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                Collections.singleton(OwnerDataFixtures.owner()));
    }

    public static Company companyWithName(String name) {
        return company(name,
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                Collections.singleton(OwnerDataFixtures.owner()));
    }

    public static Company companyWithOwners(Set<Owner> owners) {
        return company("Tesla",
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                owners);
    }

    public static Company companyWithNameAndOwners(String name, Set<Owner> owners) {
        return company(name,
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                owners);
    }

    public static List<Company> companies() {
        return Arrays.asList(
                companyWithNameAndOwners("Tesla", Collections.singleton(OwnerDataFixtures.owner("Elon Musk"))),
                companyWithNameAndOwners("BMW", Collections.singleton(OwnerDataFixtures.owner("Richard Hendricks"))),
                companyWithNameAndOwners("Mercedes", Collections.singleton(OwnerDataFixtures.owner("Erlich Bachman"))));
    }
}
