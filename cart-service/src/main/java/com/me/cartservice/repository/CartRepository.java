package com.me.cartservice.repository;

import com.me.cartservice.model.CartModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartModel, String> {
    Optional<CartModel> findByUsername(String username);
    Optional<CartModel> findByCartName(String cartName);
    void deleteByUsername(String username);
}
