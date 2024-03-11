package dev.fayzullokh.configuration.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

public class AppConfigTest {

    @Test
    public void testSpringOpenAPI() {
        AppConfig appConfig = new AppConfig();

        OpenAPI openAPI = appConfig.springOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertNotNull(openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getInfo().getLicense());
        assertNotNull(openAPI.getExternalDocs());
        assertNotNull(openAPI.getServers());
        assertEquals(4, openAPI.getServers().size());
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSecuritySchemes());
        assertTrue(openAPI.getComponents().getSecuritySchemes().containsKey("bearerAuth"));
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertEquals("bearerAuth", securityScheme.getName());
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
        assertEquals("bearer", securityScheme.getScheme());
        assertEquals("JWT", securityScheme.getBearerFormat());
        assertNotNull(openAPI.getSecurity());
        assertTrue(openAPI.getSecurity().contains(new SecurityRequirement().addList("bearerAuth")));
    }

    @Test
    public void testCorsConfigurer() {
        AppConfig appConfig = new AppConfig();

        WebMvcConfigurer webMvcConfigurer = appConfig.corsConfigurer();

        assertNotNull(webMvcConfigurer);
        CorsRegistry corsRegistry = new CorsRegistry();
        webMvcConfigurer.addCorsMappings(corsRegistry);
        assertNotNull(corsRegistry);
    }
}
