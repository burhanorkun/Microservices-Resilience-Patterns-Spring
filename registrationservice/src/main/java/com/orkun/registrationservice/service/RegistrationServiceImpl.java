package com.orkun.registrationservice.service;

import com.orkun.registrationservice.dto.Item;
import com.orkun.registrationservice.dto.SellerDto;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{

    private final RegistrationRepository registrationRepository;

    @Override
    public String addSeller(SellerDto sellerDto) {
        if (sellerDto.getEmailId() == null || sellerDto.getEmailId().isEmpty()) {
            log.error("email id which is mandatory field is null/empty");
            throw new RuntimeException("Seller mail id is not valid. Please enter valid Id");
        }
        sellerDto.setId(getSellersList().size() + 1);
        boolean isSellerAdded = registrationRepository.addSeller(sellerDto);
        String message;
        if (isSellerAdded) {
            message = "Registration successful. Your registration id is - '" + sellerDto.getId()
                    + "'\n Save it for future communication with us.";

        } else {
            message = "There was some problem in registering the seller. Please try after some time!!";

        }
        log.info("Add seller status - {} and message - {}", isSellerAdded, message);
        return message;
    }

    @Override
    public List<SellerDto> getSellersList() {
        List<SellerDto> sellerList = registrationRepository.getSellerList();

        sellerList.add(getDefaultSelletDto());

        log.info("fetching seller list. Total sellers - {}", sellerList.size());
        return sellerList;
    }

    private SellerDto getDefaultSelletDto() {
        //default value
        SellerDto sellerDto = new SellerDto();
        sellerDto.setId(123456l);
        sellerDto.setFirstName("Burhan");
        sellerDto.setLastName("Orkun");
        sellerDto.setEmailId("burhanorkun@gmail.com");

        Item item = new Item();
        item.setId(3456l);
        item.setName("Apple");
        item.setCategory("Computer");
        item.setPrice(12.5);

        List<Item> items = new ArrayList<>();
        items.add(item);

        sellerDto.setItemsSold(items);
        return sellerDto;
    }
}
