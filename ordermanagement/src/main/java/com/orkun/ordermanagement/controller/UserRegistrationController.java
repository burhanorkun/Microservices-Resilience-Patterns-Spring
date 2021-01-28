package com.orkun.ordermanagement.controller;

import com.orkun.ordermanagement.dto.SellerDto;
import com.orkun.ordermanagement.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register/seller")
    public String registerAsSeller(@RequestBody SellerDto sellerDto) {
        return userRegistrationService.registerSeller(sellerDto);
    }

    @GetMapping("/sellerList")
    public List<SellerDto> getSellersList() {
        return userRegistrationService.getSellersList();
    }

    @PostMapping("/register/sellerRateLimiter")
    public String registerAsSellerRateLimitter(@RequestBody SellerDto sellerDto){
        return userRegistrationService.registerSellerRateLimiter(sellerDto);
    }

    @PostMapping("/register/sellerRetry")
    public String registerAsSellerRetry(@RequestBody SellerDto sellerDto){
        return userRegistrationService.registerSellerRetry(sellerDto);
    }
}
