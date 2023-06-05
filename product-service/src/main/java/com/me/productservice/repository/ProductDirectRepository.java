package com.me.productservice.repository;

import com.me.productservice.dto.product.ProductsSearchRequest;
import com.me.productservice.dto.product.SearchParameters;
import com.me.productservice.model.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDirectRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> findByCharacteristic(ProductsSearchRequest request) {
        //TODO VALIDATE THIS CRITERIA USING SCHEMA
        SearchParameters search = request.getSearch();
        Query query = new Query();
        Pageable paging = PageRequest.of(request.getPage().getPage(), request.getPage().getSize());
        query.with(paging);
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(search.getName())) {
            criteria = withRegex(criteria, "name", search.getName());
        }
        if (StringUtils.isNotBlank(search.getUsername())) {
            criteria.and("username").is(search.getUsername());
        }
        if (search.getPriceFrom() != null || search.getPriceTo() != null) {
//            if (search.getPriceFrom() == null) {
//                search.setPriceFrom(Long.MIN_VALUE);
//            }
//            if (search.getPriceTo() == null) {
//                search.setPriceTo(Long.MAX_VALUE);
//            }
            criteria = criteria.and("price");
        }
          if (search.getPriceFrom() != null) {
            criteria.gte(search.getPriceFrom());
        }
        if (search.getPriceTo() != null) {
            criteria.lte(search.getPriceTo());
        }
        if (search.getCreatedTo() != null || search.getCreatedFrom() != null) {
            criteria = criteria.and("createdAt");
        }
        if (search.getCreatedFrom() != null) {
            criteria.gte(search.getCreatedFrom());
        }
        if (search.getCreatedTo() != null) {
            criteria.lte(search.getCreatedTo());
        }
        if (StringUtils.isNotBlank(search.getProductPath())) {
            criteria.and("productPath").is(search.getProductPath());
        }

        Criteria finalCriteria = criteria;
        //TODO UPDATE WITH SCHEMA
//        if (CollectionUtils.isNotEmpty(search.getOptionalProductParams())) {
//            search.getOptionalProductParams().forEach(x -> {
//                finalCriteria.and("productOptions.name").is(x.getAdditionalParamName());
//                finalCriteria.and("productOptions.value")
//                        .is(x.getAdditionalParamValue())
//                        .gte(x.getAdditionalParamFrom())
//                        .lte(x.getAdditionalParamTo());
//
//            });
//        }

        query.addCriteria(finalCriteria);

        return mongoTemplate.find(query, Product.class);
    }

    private Criteria withRegex(Criteria criteria,String name, String value) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(value))
        {
            return criteria;
        }
        if (value.length() > 4) {
            return criteria.and(name).regex(".*" + value +".*");
        }

        return criteria;
    }
}
