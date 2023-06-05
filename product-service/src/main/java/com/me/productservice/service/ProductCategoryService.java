package com.me.productservice.service;

import com.me.productservice.dto.category.CategoryOption;
import com.me.productservice.dto.category.ProductCategoryCreateRequest;
import com.me.productservice.dto.category.ProductCategoryResponse;
import com.me.productservice.dto.category.SubCategory;
import com.me.productservice.model.CategoryOptionModel;
import com.me.productservice.model.ProductCategoryModel;
import com.me.productservice.model.SubCategoryModel;
import com.me.productservice.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.me.productservice.security.Securities.currentUser;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    public void create(ProductCategoryCreateRequest request) {
        List<ProductCategoryModel> productCategoryModels = request.getCategories().stream().map(
                category -> {
                    ProductCategoryModel productCategoryModel = new ProductCategoryModel();
                    productCategoryModel.setCategoryName(category.getCategoryName());
                    List<SubCategory> subCategories = category.getSubCategories();
                    List<SubCategoryModel> subCategoryModels = subCategories.stream().map(
                            this::mapSubcategoryToModel
                    ).toList();
                    productCategoryModel.setSubCategories(subCategoryModels);
                    if (CollectionUtils.isEmpty(subCategoryModels)){
                        productCategoryModel.setCategoryOptionModels(mapCategoryOptionsModel(category.getCategoryOptions()));
                    }

                    productCategoryModel.setUsername(getUsername());
                    return productCategoryModel;
                }
        ).toList();

        productCategoryRepository.saveAll(productCategoryModels);
    }

    public ProductCategoryResponse getUserCategories(String username) {
        List<ProductCategoryModel> productCategoryModels = productCategoryRepository.findByUsername(username).orElse(Collections.emptyList());
        return mapModelToResponse(productCategoryModels);


    }

    private ProductCategoryResponse mapModelToResponse(List<ProductCategoryModel> productCategoryModels) {
        ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
        if (CollectionUtils.isNotEmpty(productCategoryModels)) {
            productCategoryResponse.setUsername(productCategoryModels.get(0).getUsername());
        }
        List<SubCategory> list = productCategoryModels.stream().map(model -> {
            SubCategory subCategory = new SubCategory();
            subCategory.setCategoryName(model.getCategoryName());
            subCategory.setSubCategories(mapModelToResponseSubCategories(model.getSubCategories()));
            if (CollectionUtils.isNotEmpty(model.getCategoryOptionModels())) {
                subCategory.setCategoryOptions(mapModelCategoryOptionsToResponse(model.getCategoryOptionModels()));
            }
            return subCategory;
        }).toList();

        return productCategoryResponse.setCategories(list);

    }

    private List<SubCategory> mapModelToResponseSubCategories(List<SubCategoryModel> subCategories) {
        if (CollectionUtils.isNotEmpty(subCategories)) {
            return subCategories.stream().map(model -> {
                SubCategory subCategory = new SubCategory();
                subCategory.setCategoryName(model.getCategoryName());
                subCategory.setCategoryOptions(mapModelCategoryOptionsToResponse(model.getCategoryOptions()));
                subCategory.setSubCategories(mapModelToResponseSubCategories(model.getSubCategories()));
                return subCategory;
            }).toList();
        }

        return Collections.emptyList();
    }

    private List<CategoryOption> mapModelCategoryOptionsToResponse(List<CategoryOptionModel> subCategories) {
        if (CollectionUtils.isNotEmpty(subCategories)) {
            return subCategories.stream().map(model -> new CategoryOption()
                    .setMax(model.getMax())
                    .setMin(model.getMin())
                    .setName(model.getName())
                    .setRequired(model.isRequired())).toList();
        }
        return Collections.emptyList();
    }

    private String getUsername() {
        return currentUser().getUsername();
    }

    private SubCategoryModel mapSubcategoryToModel(SubCategory subCategory) {
        SubCategoryModel subCategoryModel = new SubCategoryModel();
        subCategoryModel.setCategoryName(subCategory.getCategoryName());
        if (CollectionUtils.isNotEmpty(subCategory.getSubCategories())) {
            subCategoryModel.setSubCategories(subCategory.getSubCategories().stream().map(this::mapSubcategoryToModel).toList());
        }
        if (CollectionUtils.isEmpty(subCategoryModel.getSubCategories())) {
            subCategoryModel.setCategoryOptions(mapCategoryOptionsModel(subCategory.getCategoryOptions()));
        }
        return subCategoryModel;
    }

    private List<CategoryOptionModel> mapCategoryOptionsModel(List<CategoryOption> categoryOptions) {
        if (CollectionUtils.isNotEmpty(categoryOptions)) {
            return categoryOptions.stream().map(
                    x -> new CategoryOptionModel()
                            .setName(x.getName())
                            .setRequired(x.isRequired())
                            .setMax(x.getMax())
                            .setMin(x.getMin())
            ).toList();
        }
        return Collections.emptyList();
    }



}
