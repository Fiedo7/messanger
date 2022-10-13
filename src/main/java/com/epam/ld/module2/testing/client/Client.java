package com.epam.ld.module2.testing.client;

import java.util.Map;

/**
 * The type Client.
 */
public interface Client {
    /**
     * Generate message by Client.
     */
    Map<String, String> getParams();

    void processMessage(String message);
}