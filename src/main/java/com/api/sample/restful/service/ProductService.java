package com.api.sample.restful.service;

import com.api.sample.restful.model.Product;
import com.api.sample.restful.repository.ProductRepository;
import com.api.sample.restful.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductVO> findAll() {
        return productRepository.findAll().stream()
                .map(p -> getProductVO(p))
                .collect(Collectors.toList())
                ;
    }

    public Optional<ProductVO> findById(Long id) {
        return productRepository
                .findById(id)
                .map(p -> getProductVO(p))
                ;
    }

    public ProductVO save(ProductVO product) {
        return getProductVO(productRepository.save(this.getProduct(product)));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Transform Product to ProductVO
     * @param product
     * @return
     */
    private ProductVO getProductVO(Product product) {
        return ProductVO.builder()
                .created(product.getCreated())
                .updated(product.getUpdated())
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    /**
     * Transform Product to ProductVO
     * @param product
     * @return
     */
    private Product getProduct(ProductVO product) {
        return Product.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
