package com.orkun.registrationservice.service;

import com.orkun.registrationservice.dto.SellerDto;

import java.util.List;

public interface RegistrationService {

    String addSeller(SellerDto sellerDto);

    List<SellerDto> getSellersList();
}
