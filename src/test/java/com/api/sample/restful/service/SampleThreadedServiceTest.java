package com.api.sample.restful.service;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SampleThreadedServiceTest {

    private final SampleThreadedService sut = new SampleThreadedService();

    @Test
    public void runThreeThreadsAtOnceAndThenAFinalOne() {
        long minimumRuntime = 3000L + 4000L;
        Long startTime = System.currentTimeMillis();
        sut.runThreeThreadsAtOnceAndThenAFinalOne();
        Long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;
        assertTrue(runTime > minimumRuntime);
    }
}
