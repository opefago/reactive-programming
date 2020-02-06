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

        final CompletableFuture<String> verifyFuture = new CompletableFuture<>();
        HTTPUtils.post(Utils.URL + "/api/verify", payLoad, (status, response) -> {verifyFuture.complete(response);});

        final CompletableFuture<String> profileFuture = new CompletableFuture<>();
        verifyFuture.thenApplyAsync(s -> {
            User user = new Gson().fromJson(s, User.class);
            HTTPUtils.get(Utils.URL + "/api/profile/" + user.getId(), ((status1, response1) -> {
                profileFuture.complete(response1);
            }));
            return null;
        });

        try {
            System.out.println(profileFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
