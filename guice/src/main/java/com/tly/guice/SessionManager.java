package com.tly.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class SessionManager {

    private final Long sessionId;

    private final Provider<Integer> ageProvider;

    @Inject
    @Named("cookie")
    private final String cookie;

    @Inject
    @Named("address")
    private final String address;

    @Inject
    @Book
    private final Long bookId;

    @Inject
    public SessionManager(Long sessionId, Provider<Integer> ageProvider, String cookie, String address, Long bookId) {
        super();
        this.sessionId = sessionId;
        this.ageProvider = ageProvider;
        this.cookie = cookie;
        this.address = address;
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "SessionManager{" +
                "sessionId=" + sessionId +
                ", ageProvider=" + ageProvider +
                ", cookie='" + cookie + '\'' +
                ", address='" + address + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }
}
