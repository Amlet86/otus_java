package ru.amlet.services;

public interface ClientAuthService {
    boolean authenticate(String login, String password);
}
