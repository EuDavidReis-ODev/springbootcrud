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

import com.davidreisodev.springbootcrud.dto.ProductRecordDto;
import com.davidreisodev.springbootcrud.models.ProductModel;
import com.davidreisodev.springbootcrud.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {
    
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/addprod")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductModel product) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(product,productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product = productRepository.findById(id);
        
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto product){
        Optional<ProductModel> productModel = productRepository.findById(id);
        
        if(productModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        var newProductModel = productModel.get();
        BeanUtils.copyProperties(product, newProductModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(newProductModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product = productRepository.findById(id);
        
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        productRepository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
    }

}
