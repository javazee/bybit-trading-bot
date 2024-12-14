package ru.trading.bybit.service;

import com.bybit.api.client.domain.CategoryType;
import com.bybit.api.client.domain.account.AccountType;
import com.bybit.api.client.domain.asset.request.AssetDataRequest;
import com.bybit.api.client.restApi.BybitApiAssetRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AssetService {

    private final BybitApiAssetRestClient bybitApiAssetRestClient;

    public AssetService(BybitApiAssetRestClient bybitApiAssetRestClient) {
        this.bybitApiAssetRestClient = bybitApiAssetRestClient;
    }

    public void getAsset() {
//        var response = bybitApiAssetRestClient.getAssetTransferSubUidList();
//        System.out.println(response);
    }
}
