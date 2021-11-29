package com.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
