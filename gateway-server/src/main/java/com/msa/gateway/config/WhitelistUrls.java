package com.msa.gateway.config;

import java.util.List;
import java.util.Map;

public class WhitelistUrls {

    private static final Map<String, List<String>> WHITELIST = Map.of(
        "GET", List.of("/internal"),
        "POST", List.of("/auth", "/internal"),
        "PUT", List.of("/internal"),
        "DELETE", List.of("/internal")
    );

    public static boolean isWhitelist(String method, String path) {
        return WHITELIST.getOrDefault(method, List.of()).stream().anyMatch(path::startsWith);
    }

}
