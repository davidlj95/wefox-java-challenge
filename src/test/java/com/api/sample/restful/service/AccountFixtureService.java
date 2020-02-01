package com.api.sample.restful.service;

import com.api.sample.restful.model.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AccountFixtureService {

    Account getFixture() {
        return new Account(
                new Random().nextLong(),
                "Fixture Account",
                "fixture@example.com",
                25,
                new ArrayList<>(),
                new Date(),
                new Date()
        );
    }
}
