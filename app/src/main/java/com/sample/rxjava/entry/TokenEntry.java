package com.sample.rxjava.entry;

/**
 * Created by cui on 2018/8/17.
 */

public class TokenEntry {
    public String token;
    public boolean expired;

    public TokenEntry() {
    }

    public TokenEntry(boolean expired) {
        this.expired = expired;
    }
}
