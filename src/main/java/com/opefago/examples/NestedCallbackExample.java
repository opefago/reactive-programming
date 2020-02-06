package com.opefago.examples;

import com.google.gson.Gson;
import com.opefago.models.Login;
import com.opefago.models.User;
import com.opefago.utils.HTTPUtils;
import com.opefago.utils.Utils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class NestedCallbackExample implements RunningExample{
    @Override
    public void test() {
        String payLoad = new Gson().toJson(new Login("ope", "fago"));
        System.out.println(payLoad);
        HTTPUtils.post(Utils.URL + "/api/verify", payLoad, (status, response) -> {
            User user = new Gson().fromJson(response, User.class);
            HTTPUtils.get(Utils.URL + "/api/profile/" + user.getId(), ((status1, response1) -> {
                System.out.println(response1);
            }));
        });
    }
}
