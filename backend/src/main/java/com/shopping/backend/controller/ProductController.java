package com.shopping.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.backend.service.ProductService;
import com.shopping.backend.constant.ErrorCode;
import com.shopping.backend.dto.ApiResponse;
import com.shopping.backend.dto.SearchProductsRequest;
import com.shopping.backend.dto.SearchProductsResponse;

import java.util.List;

/**
 * ProductController는 상품 관련 API를 제공합니다.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    
    @Autowired
    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<SearchProductsResponse>>> SearchProductsList (@RequestBody SearchProductsRequest searchProductsRequest) {
        try {
            if (searchProductsRequest.getMenuId()==null || searchProductsRequest.getMenuId().trim().isEmpty()) {
                ApiResponse<List<SearchProductsResponse>> response = ApiResponse.error(
                    "상품 목록 조회 시 메뉴 찾기에 실패하였습니다.: ",
                    ErrorCode.MENU_SEARCH_ERROR,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }

            List<SearchProductsResponse> productsList = productService.searchProductsList(searchProductsRequest.getMenuId());
            ApiResponse<List<SearchProductsResponse>> response = ApiResponse.success(
                "상품 목록 조회 성공했습니다.",
                productsList,
                HttpStatus.OK.value()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("searchAllMenus 오류: " + e.getMessage());
            
            ApiResponse<List<SearchProductsResponse>> response = ApiResponse.error(
                "상품 목록 조회에 실패했습니다: " + e.getMessage(),
                ErrorCode.MENU_PRODUCTS_ERROR,
                HttpStatus.BAD_REQUEST.value()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
