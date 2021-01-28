package com.orkun.ordermanagement.service.resilience4j;

import com.orkun.ordermanagement.dto.SellerDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationResilience4j {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "service1", fallbackMethod = "fallbackForRegisterSeller")
    public String registerSeller(SellerDto sellerDto) {
        String response =
                restTemplate.postForObject("/registration/addSeller", sellerDto, String.class);
        return response;
    }

    public String fallbackForRegisterSeller(SellerDto sellerDto, Throwable t) {
        log.error("Inside circuit breaker fallbackForRegisterSeller, cause - {}", t.toString());
        // SEND KAFKA, RABBITMQ or Persist anywhere
        return "Inside circuit breaker fallback method. Some error occurred while calling service for seller registration";
    }

    @CircuitBreaker(name = "service2", fallbackMethod = "fallbackForGetSeller")
    public List<SellerDto> getSellersList() {
        log.info("calling getSellerList()");
        return restTemplate.getForObject("/registration/sellersList", List.class);
    }

    public List<SellerDto> fallbackForGetSeller(Throwable t) {
        log.error("Inside fallbackForGetSeller, cause - {}", t.toString());
        SellerDto sd = new SellerDto();
        sd.setFirstName("orkun");
        sd.setId(1111);
        sd.setEmailId("default");
        List<SellerDto> defaultList = new ArrayList<>();
        defaultList.add(sd);
        return defaultList;
    }

    public String bulkHeadFallback(SellerDto sellerDto, Throwable t) {
        log.error("Inside bulkHeadFallback, cause - {}", t.toString());
        return "Inside bulkHeadFallback method. Some error occurred while calling service for seller registration";
    }

    // RateLimiter
    @RateLimiter(name = "service1", fallbackMethod = "rateLimiterfallback")
    public String getSellersListRateLimiter(SellerDto sellerDto) {
        String response =
                restTemplate.postForObject("/registration/addSeller", sellerDto, String.class);
        return response;
    }

    public String rateLimiterfallback(SellerDto sellerDto, Throwable t) {
        log.error("Inside rateLimiterfallback, cause - {}", t.toString());
        return "Inside rateLimiterfallback method. Some error occurred while calling service for seller registration";
    }

    // Retry
    @Retry(name = "retryService1", fallbackMethod = "retryfallback")
    public String registerSellerRetry(SellerDto sellerDto) {
        String response =
                restTemplate.postForObject("/registration/addSeller", sellerDto, String.class);
        return response;
    }

    public String retryfallback(SellerDto sellerDto, Throwable t) {
        log.error("Inside retryfallback, cause - {}", t.toString());
        return "Inside retryfallback method. Some error occurred while calling service for seller registration";
    }


}
