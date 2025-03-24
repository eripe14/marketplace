package com.eripe14.marketplace.discord;

import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

public class DiscordWebhookService {

    public static final Gson GSON = new Gson();

    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    public void sendMessage() {
        //
    }

}
