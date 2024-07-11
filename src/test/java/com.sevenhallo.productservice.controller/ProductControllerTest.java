package com.sevenhallo.productservice.controller;

import com.sevenhallo.productservice.dto.ProductDto;
import com.sevenhallo.productservice.service.ProductService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional
public class ProductControllerTest {
    private static final String ENDPOINT_URL = "/api/product";

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<ProductDto> page = new PageImpl<>(Collections.singletonList(ProductBuilder.getDto()));

        Mockito.when(productService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(productService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void getById() throws Exception {
        Mockito.when(productService.findById(ArgumentMatchers.anyString()))
                .thenReturn(ProductBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));

        Mockito.verify(productService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(productService.save(ArgumentMatchers.any(ProductDto.class)))
                .thenReturn(ProductBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProductBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(productService, Mockito.times(1)).save(ArgumentMatchers.any(ProductDto.class));
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(productService.update(ArgumentMatchers.any(), ArgumentMatchers.anyString()))
                .thenReturn(ProductBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProductBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(1))
                .update(ArgumentMatchers.any(ProductDto.class), ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(productService).deleteById(ArgumentMatchers.anyString());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProductBuilder.getIds())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(1))
                .deleteById(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(productService);
    }
}
