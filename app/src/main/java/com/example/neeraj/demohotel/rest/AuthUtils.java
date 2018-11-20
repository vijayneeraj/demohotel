package com.example.neeraj.demohotel.rest;

import android.util.Base64;

/**
 * Created by ubuntu on 1/8/16.
 */
public class AuthUtils {

    public static String basic(String email, String password) {

        String basicAuth = "Basic " +
                Base64.encodeToString(String.format("%s:%s", email, password)
                                .getBytes(),
                        Base64.NO_WRAP);

        return basicAuth;
    }
}
