package com.opefago;

import com.google.gson.Gson;
import com.opefago.examples.CompleteableFutureRxExample;
import com.opefago.examples.NestedCallbackExample;
import com.opefago.examples.RunningExample;
import com.opefago.models.Login;
import com.opefago.models.User;
import com.opefago.utils.HTTPUtils;
import com.opefago.utils.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {
    public static Future<String> calculateAsync(){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(()->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete("Hello");
        });

        return completableFuture;
    }

    public static Future<String> calculateAsyncWithCancellation(){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit((()->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.cancel(false);
        }));


        return completableFuture;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunningExample example = new NestedCallbackExample();
        example.test();

        example = new CompleteableFutureRxExample();
        example.test();

    }
}
