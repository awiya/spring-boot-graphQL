package com.idihia.springgraphQL.repository;


import com.idihia.springgraphQL.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
