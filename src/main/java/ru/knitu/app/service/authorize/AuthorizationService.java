package ru.knitu.app.service.authorize;

import org.springframework.stereotype.Service;

public interface AuthorizationService {
    void authorize(String code);
}
