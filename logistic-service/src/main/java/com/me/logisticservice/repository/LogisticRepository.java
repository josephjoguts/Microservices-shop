package com.me.logisticservice.repository;


import com.me.logisticservice.model.LogisticModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticRepository extends JpaRepository<LogisticModel, Long> {
}
