package com.deepak.yozotask.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<JsonElement> executeProduct() {
        return apiCallInterface.getproducts();
    }
}
