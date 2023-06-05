package com.me.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.productservice.dto.product.*;
import com.me.productservice.model.Product;
import com.me.productservice.repository.ProductDirectRepository;
import com.me.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.me.productservice.security.Securities.currentUser;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDirectRepository productDirectRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public ProductListResponse createProduct(ProductCreateRequest request) throws JsonProcessingException {
        String user = currentUser().getUsername();
        Product product = new Product()
                .setName(request.getName())
                .setPrice(request.getPrice())
                .setQuantity(request.getQuantity())
                .setProductPath(request.getProductPath())
                .setProductOptions(request.getProductOptions())
                .setCreatedAt(new Date())
                .setUsername(user)
                .setDescription(request.getDescription());
        //TODO validate add to inventory


        Product saved = productRepository.save(product);

        InventoryAddResponse result = webClientBuilder.baseUrl("http://inventory-service").build()
                .post()
                .uri("/api/v1/internal/inventory")
                .header("USER", currentUser().getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(new ProductInternal()
                        .setId(saved.getId())
                        .setQuantity(saved.getQuantity())
                        .setName(saved.getName())
                        .setPrice(saved.getPrice())
                        .setUsername(saved.getUsername())))
                .retrieve()
                .bodyToMono(InventoryAddResponse.class)
                .block();


        log.info("Product is saved {}", product);

        return new ProductListResponse()
                .setId(saved.getId())
                .setName(saved.getName())
                .setPrice(saved.getPrice())
                .setQuantity(saved.getQuantity())
                .setProductPath(saved.getProductPath())
                .setProductOptions(saved.getProductOptions())
                .setCreatedAt(saved.getCreatedAt())
                .setUsername(saved.getUsername())
                .setDescription(saved.getDescription());
    }

    public List<ProductListResponse> getProductsFiltered(ProductsSearchRequest request) {
        List<Product> products = productDirectRepository.findByCharacteristic(request);
        log.info("Get all products count {}", products.size());

        return products.stream().map(x -> new ProductListResponse()
                .setId(x.getId())
                .setName(x.getName())
                .setPrice(x.getPrice())
                .setQuantity(x.getQuantity())
                .setProductPath(x.getProductPath())
                .setProductOptions(x.getProductOptions())
                .setCreatedAt(x.getCreatedAt())
                .setUsername(x.getUsername())
                .setDescription(x.getDescription())
        ).toList();
    }

    public List<ProductListResponse> findProduct() {
        return null;
    }
}
