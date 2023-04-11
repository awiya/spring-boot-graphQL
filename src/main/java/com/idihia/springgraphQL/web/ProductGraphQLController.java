package com.idihia.springgraphQL.web;


import com.idihia.springgraphQL.dto.ProductRequestDTO;
import com.idihia.springgraphQL.entities.Category;
import com.idihia.springgraphQL.entities.Product;
import com.idihia.springgraphQL.repository.CategoryRepository;
import com.idihia.springgraphQL.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public class ProductGraphQLController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @QueryMapping
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product getProductById(@Argument String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException(format("The product with ID %s could not be found.", id))
        );
    }

    @QueryMapping
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Category getCategoryById(@Argument Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Category %s not found", id)));
    }

    @QueryMapping
    public List<Product> getProductByCategory(@Argument Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(format("The requested category %s could not be found.",categoryId)));
        return productRepository.findByCategory(category);
    }

    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product) {
        Category category = categoryRepository.findById(product.categoryId())
                .orElseThrow(() -> new RuntimeException(format("The requested category %s could not be found.", product.categoryId())));
        Product productToSave = new Product();
        productToSave.setId(UUID.randomUUID().toString());
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }

    @MutationMapping
    public Product updateProduct(@Argument String id, @Argument ProductRequestDTO product) {
        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToSave = new Product();
        productToSave.setId(id);
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }

    @MutationMapping
    public void deleteProduct(@Argument String id) {
        productRepository.deleteById(id);
    }

}
