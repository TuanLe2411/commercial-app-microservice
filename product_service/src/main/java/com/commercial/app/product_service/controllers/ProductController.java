package com.commercial.app.product_service.controllers;

import com.commercial.app.product_service.dtos.ProductCreateDto;
import com.commercial.app.product_service.dtos.ProductUpdateDto;
import com.commercial.app.product_service.es.commands.CreateNewProductCommand;
import com.commercial.app.product_service.es.commands.DeleteProductCommand;
import com.commercial.app.product_service.es.commands.UpdateProductCommand;
import com.commercial.app.product_service.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {
    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-one")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid ProductCreateDto productCreateDto) throws JsonProcessingException {
        CreateNewProductCommand createNewProductCommand = CreateNewProductCommand.builder()
            .category(productCreateDto.getCategory())
            .name(productCreateDto.getName())
            .price(productCreateDto.getPrice())
            .amount(productCreateDto.getAmount())
            .build();
        this.productService.execute(createNewProductCommand);
    }

    @PostMapping("/update-one/{product-id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("product-id") @NotBlank String productId,
                       @RequestBody @Valid ProductUpdateDto productUpdateDto
    ) throws JsonProcessingException {
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
            .category(productUpdateDto.getCategory())
            .name(productUpdateDto.getName())
            .price(productUpdateDto.getPrice())
            .amount(productUpdateDto.getAmount())
            .productId(productId)
            .build();
        this.productService.execute(updateProductCommand);
    }

    @DeleteMapping("/remove-one/{product-id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("product-id") @NotBlank String productId) {
        DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder()
            .productId(productId)
            .build();
        this.productService.execute(deleteProductCommand);
    }
}
