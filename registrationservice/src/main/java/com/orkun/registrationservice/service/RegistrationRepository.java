package com.orkun.registrationservice.service;

import com.orkun.registrationservice.dto.SellerDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepository {

    List<SellerDto> sellerDtoList = new ArrayList<>();

    public boolean addSeller(SellerDto sellerDto) {
        return sellerDtoList.add(sellerDto);
    }

    public List<SellerDto> getSellerList() {
        return sellerDtoList;
    }
}
