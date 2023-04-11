package com.idihia.springgraphQL;


import com.idihia.springgraphQL.entities.Category;
import com.idihia.springgraphQL.entities.Product;
import com.idihia.springgraphQL.repository.CategoryRepository;
import com.idihia.springgraphQL.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SpringGraphQlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGraphQlApplication.class, args);
    }


    CommandLineRunner commandLineRunner(CategoryRepository categoryRepository,
                                        ProductRepository productRepository) {
        return args -> {
            Random random = new Random();
            DecimalFormat df = new DecimalFormat("#.##");

            List.of("Computer", "Printer", "Smartphone").forEach(cat -> {
                Category category = Category.builder()
                        .name(cat)
                        .build();
                categoryRepository.save(category);
            });

            categoryRepository.findAll().forEach(category -> {
                for (int i = 1; i <= 10; i++) {
                    Product product = Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name(category.getName() + " " + i)
                            .price(100 + random.nextDouble() * (50000 - 100))
                            .quantity(1 + random.nextInt(99))
                            .category(category)
                            .build();
                    productRepository.save(product);
                }
            });
        };
    }

}
