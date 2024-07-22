package com.project.products.service;

import com.project.products.model.Category;
import com.project.products.model.Product;
import com.project.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void findById() {
        Long id = 1L;
        Product productMock = buildProduct(id);
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(productMock));

        Optional<Product> productFound = service.findById(id);

        assertTrue(productFound.isPresent());
        assertEquals(productMock, productFound.get());
    }

    @Test
    void findAll() {
        Product productMock_3 = buildProduct(3L);
        Product productMock_1 = buildProduct(1L);
        Product productMock_2 = buildProduct(2L);
        Mockito.when(productRepository.findAll()).thenReturn(List.of(productMock_3, productMock_1, productMock_2));

        List<Product> productsFound = service.findAll();

        assertFalse(productsFound.isEmpty());
        assertEquals(3, productsFound.size());
        assertEquals( productMock_1, productsFound.get(0));
        assertEquals( productMock_2, productsFound.get(1));
        assertEquals( productMock_3, productsFound.get(2));
    }

    @Test
    void save() {
        Long id = 1L;
        Product productMock = buildProduct(id);
        Mockito.when(productRepository.save(any())).thenReturn(productMock);

        productMock.setId(null);
        Product productSaved = service.save(productMock);

        productMock.setId(id);
        assertEquals(productMock, productSaved);
    }

    @Test
    void update() {
        Long id = 1L;
        Product productMock = buildProduct(id);
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(productMock));
        Mockito.when(productRepository.save(any())).thenReturn(productMock);

        Product productUpdated = service.update(productMock);

        assertEquals(productMock, productUpdated);
    }

    @Test
    void updateWithError() {
        Long id = 1L;
        Product productMock = buildProduct(id);
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrowsExactly(RuntimeException.class, () -> service.update(productMock));
        assertEquals(String.format("Product does not exist with id %s.", id), runtimeException.getMessage());
    }

    @Test
    void deleteById() {
        Long id = 1L;
        Product productMock = buildProduct(id);
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(productMock));
        Mockito.doNothing().when(productRepository).deleteById(any());

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @Test
    void deleteByIdWithError() {
        Long id = 1L;
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrowsExactly(RuntimeException.class, () -> service.deleteById(id));
        assertEquals(String.format("Product does not exist with id %s.", id), runtimeException.getMessage());
    }

    private Product buildProduct(Long id){
        Category categoryMock = new Category();
        categoryMock.setId(1L);
        categoryMock.setName("Vegetables");

        Product productMock = new Product();
        productMock.setId(id);
        productMock.setName("Product Test");
        productMock.setDescription("Description Test");
        productMock.setAvailable(true);
        productMock.setPrice(BigDecimal.valueOf(10.99));
        productMock.setCategory(categoryMock);

        return productMock;
    }
}