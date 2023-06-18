package ssginc_kdt_team3.BE;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cash")
                .allowedOrigins("http://localhost:3000", "http://10.10.10.67:3000", "https://fe-cust.vercel.app/")
                .allowedMethods("GET", "POST")
                .allowCredentials(true);
    }
}