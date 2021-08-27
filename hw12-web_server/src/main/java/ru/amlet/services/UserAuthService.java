package ru.amlet.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
