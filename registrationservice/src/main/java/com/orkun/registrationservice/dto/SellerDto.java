package com.orkun.registrationservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SellerDto {

    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private List<Item> itemsSold;
}
