package com.shopping.backend.dto;

public interface SearchAllMenuResponse {

    String getMenuId();
    String getMenuName();
    String getParentMenuId();
    Integer getMenuDepth();
    Integer getMenuOrder();
}