package com.rikishi.rikishi.provider;

public interface EmojiFlagProvider {
    /**
     * @param countryCode The country code.
     * @return The specified country flag or empty
     * string if the flag cannot be found.
     */
    String flagOf(String countryCode);
}
