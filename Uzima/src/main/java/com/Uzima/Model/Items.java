package com.Uzima.Model;

import com.Uzima.enums.ItemStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "items")

public class Items extends BluePrint{

    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private int itemAmount;
    private double itemQuantity;
    private double itemBuyingPrice;
    private double itemSellingPrice;
    private ItemStatus status;
    private String itemSupplier;

}