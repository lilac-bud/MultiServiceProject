package io.github.lilacbud.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigServerApplicationIT {
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void givenThatPropertiesFileExists_whenGettingPropertiesForService_thenReturnProperties() throws Exception {
        mvc.perform(get("/TestService/default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.propertySources[0].source.some-property").value("some-value"));
    }
}
