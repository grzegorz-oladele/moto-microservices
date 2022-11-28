package pl.grzegorz.lapservice;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBikerService() {
        return new WireMockServer(9561);
    }
}