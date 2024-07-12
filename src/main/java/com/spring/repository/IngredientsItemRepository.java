package com.spring.repository;

import com.spring.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Long> {
}
