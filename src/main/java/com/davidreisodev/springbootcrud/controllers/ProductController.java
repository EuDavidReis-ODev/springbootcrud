package com.davidreisodev.springbootcrud.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.davidreisodev.springbootcrud.dtos.ProductRecordDto;
import com.davidreisodev.springbootcrud.models.ProductModel;
import com.davidreisodev.springbootcrud.repositories.ProductRepository;
import com.davidreisodev.springbootcrud.services.ProductService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
public class ProductController {
    
    private static final String PRODUCT_NOT_FOUND = "Produto não encontrado.";
    private static final String PRODUCT_REMOVED = "Produto removido com sucesso.";
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @PostMapping("/addprod")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto product) {
        Optional<ProductModel> productModel = productService.addProduct(product);

        if(productModel.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(productModel.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productList = productService.getAllProducts();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value="id") @NotBlank UUID id){
        Optional<ProductModel> product = productService.getProductById(id);

        if (product.isPresent())return ResponseEntity.status(HttpStatus.OK).body(product.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto product){
        Optional<ProductModel> productModel = productService.updateProductById(id, product);

        if(productModel.isPresent())return ResponseEntity.status(HttpStatus.OK).body(productModel);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
        
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product = productService.deleteProductById(id);

        if(product.isPresent())return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_REMOVED);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
    }

}
