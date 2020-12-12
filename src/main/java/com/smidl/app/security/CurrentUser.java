package com.smidl.app.security;

import com.smidl.app.backend.model.User;

@FunctionalInterface
public interface CurrentUser {
    User getUser();
}
