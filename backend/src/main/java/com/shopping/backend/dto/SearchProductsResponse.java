package com.shopping.backend.dto;

public interface SearchProductsResponse {

    long getProdId();
    String getProdName();
    String getMenuId();
    Integer getProdPrice();
    String getImageUrl();

}