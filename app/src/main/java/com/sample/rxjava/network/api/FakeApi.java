package com.sample.rxjava.network.api;

import com.sample.rxjava.entry.TokenEntry;
import com.sample.rxjava.entry.TokenThing;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by cui on 2018/8/17.
 */

public class FakeApi {
    Random random = new Random();

    public Observable<TokenEntry> getFakeToken(@NonNull String fakeAuth) {
        return Observable.just(fakeAuth)
                .map(new Function<String, TokenEntry>() {
                    @Override
                    public TokenEntry apply(String fakeAuth) {
                        // Add some random delay to mock the network delay
                        int fakeNetworkTimeCost = random.nextInt(500) + 500;
                        try {
                            Thread.sleep(fakeNetworkTimeCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        TokenEntry fakeToken = new TokenEntry();
                        fakeToken.token = createToken();
                        return fakeToken;
                    }
                });
    }

    private static String createToken() {
        return "fake_token_" + System.currentTimeMillis() % 10000;
    }

    public Observable<TokenThing> getFakeData(TokenEntry fakeToken) {
        return Observable.just(fakeToken)
                .map(new Function<TokenEntry, TokenThing>() {
                    @Override
                    public TokenThing apply(TokenEntry fakeToken) {
                        // Add some random delay to mock the network delay
                        int fakeNetworkTimeCost = random.nextInt(500) + 500;
                        try {
                            Thread.sleep(fakeNetworkTimeCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (fakeToken.expired) {
                            throw new IllegalArgumentException("Token expired!");
                        }

                        TokenThing fakeData = new TokenThing();
                        fakeData.id = (int) (System.currentTimeMillis() % 1000);
                        fakeData.name = "FAKE_USER_" + fakeData.id;
                        return fakeData;
                    }
                });
    }
}
