package com.orkun.ordermanagement.service.impl;

import com.orkun.ordermanagement.dto.SellerDto;
import com.orkun.ordermanagement.service.UserRegistrationService;
import com.orkun.ordermanagement.service.resilience4j.UserRegistrationResilience4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRegistrationResilience4j userRegistrationResilience4j;

    @Override
    public String registerSeller(SellerDto sellerDto) {

        String registerSeller = null;

        //for (int i = 0; i < 10000; i++) {
        long start = System.currentTimeMillis();

        registerSeller = userRegistrationResilience4j.registerSeller(sellerDto);

        log.info("add seller call returned in - {}", System.currentTimeMillis() - start);
        // }
        //registerSeller = userRegistrationResilience4j.registerSeller(sellerDto);
        return registerSeller;

    }

    @Override
    public List<SellerDto> getSellersList() {
        List<SellerDto> sellerDtoList = userRegistrationResilience4j.getSellersList();
        log.info("userRegistrationResilience4j call returned count - {}", sellerDtoList.size());
        return sellerDtoList;
    }

    @Override
    public String registerSellerRateLimiter(SellerDto sellerDto) {
        return userRegistrationResilience4j.getSellersListRateLimiter(sellerDto);
    }

    @Override
    public String registerSellerRetry(SellerDto sellerDto) {
        return userRegistrationResilience4j.registerSellerRetry(sellerDto);
    }
}
