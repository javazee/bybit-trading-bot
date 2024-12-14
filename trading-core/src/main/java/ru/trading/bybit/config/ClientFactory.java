package ru.trading.bybit.config;

import com.bybit.api.client.config.BybitApiConfig;
import com.bybit.api.client.restApi.*;
import com.bybit.api.client.service.BybitApiClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Map;

@Configuration
public class ClientFactory {

    @Bean
    public BybitApiClientFactory bybitApiClientFactory(final @Value("${api.config}") String apiFile) throws IOException {
        final Map<String, String> keySecret = this.readKeySecret(apiFile);
        return BybitApiClientFactory.newInstance(keySecret.get("key"), keySecret.get("secret"), BybitApiConfig.MAINNET_DOMAIN, true);
    }

    @Bean
    public BybitApiAsyncTradeRestClient bybitApiAsyncTradeRestClient(final BybitApiClientFactory bybitApiClientFactory) throws IOException {
        return bybitApiClientFactory.newAsyncTradeRestClient();
    }

    @Bean
    public BybitApiAssetRestClient bybitApiAssetRestClient(final BybitApiClientFactory bybitApiClientFactory) throws IOException {
        return bybitApiClientFactory.newAssetRestClient();
    }

    @Bean
    public BybitApiAccountRestClient bybitApiAccountRestClient(final BybitApiClientFactory bybitApiClientFactory) throws IOException {
        return bybitApiClientFactory.newAccountRestClient();
    }

    @Bean
    public BybitApiMarketRestClient bybitApiMarketRestClient(final BybitApiClientFactory bybitApiClientFactory) throws IOException {
        return bybitApiClientFactory.newMarketDataRestClient();
    }


    private Map<String, String> readKeySecret(final String filePAth) throws IOException {
        final InputStream inputStream = new FileInputStream(filePAth);
        return new Yaml().load(inputStream);
    }
}
