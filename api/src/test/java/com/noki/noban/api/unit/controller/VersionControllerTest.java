package com.noki.noban.api.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.noki.noban.api.controller.VersionController;
import com.noki.noban.api.models.Version;
import com.noki.noban.api.repository.UserRepository;
import com.noki.noban.api.security.jwt.JwtService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = VersionController.class)
public class VersionControllerTest {
    
    @Autowired
    private MockMvcTester mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void versionTest() throws Exception{  
        MvcTestResult result = mockMvc.get()
            .uri("/api/version")
            .exchange();

        Version version = new Version("noban", "latest");

        assertThat(result)
            .hasStatus(HttpStatus.OK)
            .bodyJson()
            .convertTo(Version.class)
            .isEqualTo(version);
    }
}
