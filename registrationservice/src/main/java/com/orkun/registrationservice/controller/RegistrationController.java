package com.orkun.registrationservice.controller;

import com.orkun.registrationservice.dto.SellerDto;
import com.orkun.registrationservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/addSeller")
    public String addSeller(@RequestBody SellerDto sellerDto) {
        return registrationService.addSeller(sellerDto);
    }

    @GetMapping("/sellerList")
    public List<SellerDto> getSellersList() {
        return registrationService.getSellersList();
    }
}
