package com.beem.beeminterface;

import lombok.Data;

@Data
class Tokens {
    private static Token token;

    public static Token getToken() {
        return token;
    }

    public static void setToken(Token token) {
        Tokens.token = token;
    }
}
