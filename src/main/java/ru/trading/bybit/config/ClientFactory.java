package ru.trading.bybit.config;

import com.bybit.api.client.config.BybitApiConfig;
import com.bybit.api.client.restApi.BybitApiAsyncTradeRestClient;
import com.bybit.api.client.service.BybitApiClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Configuration
public class ClientFactory {

    @Bean
    public BybitApiAsyncTradeRestClient bybitApiAsyncTradeRestClient(final @Value("${api.config}") String apiFile) throws IOException {
        final Map<String, String> keySecret = this.readKeySecret(apiFile);
        return BybitApiClientFactory
                .newInstance(keySecret.get("key"), keySecret.get("secret"), BybitApiConfig.TESTNET_DOMAIN, true)
                .newAsyncTradeRestClient();
    }


    private Map<String, String> readKeySecret(final String filePAth) throws IOException {
        final InputStream inputStream = new FileInputStream(filePAth);
        return new Yaml().load(inputStream);
    }
}
