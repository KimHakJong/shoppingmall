package com.shopping.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.backend.repository.ProductRepository;
import com.shopping.backend.dto.SearchProductsResponse; // SearchProductsResponse import 추가
import java.util.List; // List import 추가

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<SearchProductsResponse> searchProductsList(String menuId) {
        try{
        String prod_status = "1";
        String thumbnail_yn = "Y";
        return productRepository.searchProductsList(menuId, prod_status, thumbnail_yn);
        }catch (Exception e){
            System.out.println("service에러 : " +e.getMessage());
            return null;
        }
    }

}
