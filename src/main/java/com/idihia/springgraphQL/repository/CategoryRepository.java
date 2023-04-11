package com.idihia.springgraphQL.repository;

import com.idihia.springgraphQL.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
