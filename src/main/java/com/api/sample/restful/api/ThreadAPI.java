package com.api.sample.restful.api;

import com.api.sample.restful.service.SampleThreadedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread")
@Slf4j
@RequiredArgsConstructor
public class ThreadAPI {

    private final SampleThreadedService sampleThreadedService;

    @GetMapping()
    public ResponseEntity<String> runSomeThreads() {
       sampleThreadedService.runThreeThreadsAtOnceAndThenAFinalOne();
       return ResponseEntity.ok("Done");
    }
}
