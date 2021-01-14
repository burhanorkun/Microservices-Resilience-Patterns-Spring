package com.orkun.ordermanagement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    private long id;
    private String name;
    private String category;
    private double price;//per item
}
