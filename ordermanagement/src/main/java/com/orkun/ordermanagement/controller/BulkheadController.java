package com.orkun.ordermanagement.controller;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.function.Supplier;

@RestController
@Slf4j
public class BulkheadController {

    private final RestTemplate restTemplate;
    private final Bulkhead bulkhead;

    private static final int MAX_CONCURRENT = 10;

    public BulkheadController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.bulkhead = createBulkHead(MAX_CONCURRENT);
    }

    private Bulkhead createBulkHead(int maxConcurrent) {

        BulkheadConfig bulkheadConfig = BulkheadConfig.custom()
                .maxConcurrentCalls(maxConcurrent)
                .maxWaitDuration(Duration.ofMillis(100))
                .build();

        Bulkhead bulkhead = Bulkhead.of("registrationService", bulkheadConfig);

        bulkhead.getEventPublisher()
                .onCallPermitted(event -> log.info("Call permitted"))
                .onCallRejected(event -> log.info("Call rejected"));

        return bulkhead;
    }

    /*
    $> ab -n 50 -c 15 http://localhost:8085/bulkhead
    Concurrency Level:      15
    Time taken for tests:   1.567 seconds
    Complete requests:      50
    Failed requests:        29
     */
    @GetMapping("/bulkhead")
    public String bulkhead() {

        // decorate call with bulkhead
        Supplier<String> register = Bulkhead.decorateSupplier(bulkhead, this::registerPayment);

        // execute the call
        Try<String> result = Try.ofSupplier(register);

        if (result.isSuccess()) {
            return result.get();
        } else {
            return "default-response";
        }
    }


    private String registerPayment() {

        return "The message was" + restTemplate.getForObject("/slow", String.class);
    }

}
