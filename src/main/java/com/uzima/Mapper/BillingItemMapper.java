package com.uzima.Mapper;

import com.uzima.dtos.BillingItemRequest;
import com.uzima.dtos.BillingItemResponse;
import com.uzima.models.BillingItem;

public class BillingItemMapper {

    public static BillingItem fromRequest(BillingItemRequest request) {
        BillingItem item = new BillingItem();
        updateBillingItem(item, request);
        return item;
    }

    public static void updateBillingItem(BillingItem item, BillingItemRequest request) {
        item.setDescription(request.getDescription());
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());
    }

    public static BillingItemResponse toResponse(BillingItem item) {
        BillingItemResponse response = new BillingItemResponse();
        response.setId(item.getId());
        response.setDescription(item.getDescription());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setAmount(item.getAmount());
        return response;
    }
}
