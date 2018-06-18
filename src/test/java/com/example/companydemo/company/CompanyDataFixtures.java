package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDataFixtures {


    public static Company company(String name, String address, String city, String country, String email, String phoneNumber, List<Owner> owners) {
        return new Company(name, address, city, country, email, phoneNumber, owners);
    }

    public static Company company() {
        return company("Tesla",
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                Collections.singletonList(OwnerDataFixtures.owner()));
    }

    public static Company companyWithName(String name) {
        return company(name,
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                Collections.singletonList(OwnerDataFixtures.owner()));
    }

    public static Company companyWithOwners(List<Owner> owners) {
        return company("Tesla",
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                owners);
    }

    public static Company companyWithNameAndOwners(String name, List<Owner> owners) {
        return company(name,
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto", "USA",
                "office@tesla.com",
                "+16506815000",
                owners);
    }

    public static List<Company> companies() {
        return Arrays.asList(
                companyWithNameAndOwners("Tesla", Collections.singletonList(OwnerDataFixtures.owner("Elon Musk"))),
                companyWithNameAndOwners("BMW", Collections.singletonList(OwnerDataFixtures.owner("Richard Hendricks"))),
                companyWithNameAndOwners("Mercedes", Collections.singletonList(OwnerDataFixtures.owner("Erlich Bachman"))));
    }

    public static CompanyInfo companyInfoWithNameAndOwnerIds(String name, List<Long> ownerIds) {
        return new CompanyInfo(name,
                "3500 Deer Creek Road, CA 94304",
                "Palo Alto",
                "USA",
                "office@tesla.com",
                "+16506815000",
                ownerIds);
    }
}
