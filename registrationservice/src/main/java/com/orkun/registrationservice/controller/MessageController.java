package com.orkun.registrationservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@Slf4j
public class MessageController {

    private int count = 0;
    private static final int DELAY = 500;

    @GetMapping("/")
    public String okay() {
        return "I'm fine.";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(DELAY);
        return "I'm fine, just slow";
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("I'm definitely not fine");
    }

    @GetMapping("/erratic")
    public String erratic() throws InterruptedException {
        log.info(Integer.toString(count++));
        if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
            log.error("erratic");
            throw new RuntimeException("I'm erratic");
        }
        log.info("ok");
        return "For now I'm ok";
    }
}
