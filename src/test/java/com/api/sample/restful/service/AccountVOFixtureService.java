package com.api.sample.restful.service;

import com.api.sample.restful.vo.AccountVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AccountVOFixtureService {

    AccountVO getFixture() {
        return AccountVO.builder()
                .id(new Random().nextLong())
                .name("Fixture Account")
                .email("fixture@example.com")
                .age(25)
                .addresses(new ArrayList<>())
                .created(new Date())
                .updated(new Date())
                .build();
    }
}
