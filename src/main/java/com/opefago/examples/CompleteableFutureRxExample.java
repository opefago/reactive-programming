package com.opefago.examples;

import com.google.gson.Gson;
import com.opefago.models.Login;
import com.opefago.models.User;
import com.opefago.utils.HTTPUtils;
import com.opefago.utils.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompleteableFutureRxExample implements RunningExample{
    @Override
    public void test() {
        String payLoad = new Gson().toJson(new Login("ope", "fago"));

        final CompletableFuture<String> verifyFuture = CompletableFuture.supplyAsync(()->
            HTTPUtils.postBlocking(Utils.URL + "/api/verify", payLoad).getResponseData()
        );

        final CompletableFuture<String> profileFuture = verifyFuture.thenApply((s)->{
                User user = new Gson().fromJson(s, User.class);
                return HTTPUtils.getBlocking(Utils.URL + "/api/profile/" + user.getId()).getResponseData();
        }
        );

    }
}
