package com.project.products.service;

import com.project.products.model.Category;
import com.project.products.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    void findAll() {
        Category categoryMock_3 = buildCategory(3L);
        Category categoryMock_1 = buildCategory(1L);
        Category categoryMock_2 = buildCategory(2L);
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(categoryMock_3, categoryMock_1, categoryMock_2));

        List<Category> categorysFound = service.findAll();

        assertFalse(categorysFound.isEmpty());
        assertEquals(3, categorysFound.size());
        assertEquals( categoryMock_1, categorysFound.get(1));
        assertEquals( categoryMock_2, categorysFound.get(2));
        assertEquals( categoryMock_3, categorysFound.get(0));
    }

    private Category buildCategory(Long id){
        Category categoryMock = new Category();
        categoryMock.setId(id);
        categoryMock.setName("Vegetables");

        return categoryMock;
    }
}