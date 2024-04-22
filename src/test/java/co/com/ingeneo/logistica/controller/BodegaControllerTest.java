package co.com.ingeneo.logistica.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import co.com.ingeneo.logistica.builders.BodegaTestBuilder;
import co.com.ingeneo.logistica.config.ApplicationConfig;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
public class BodegaControllerTest {

    private static final String PATH = "/bodega";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldFetchAllBodegas() throws Exception {
        this.mockMvc.perform(get(PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$", IsNot.not(IsNull.nullValue())));
    }

}
