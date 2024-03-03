package com.Transaction.transaction.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    ADMIN_READ("ADMIN_READ"),
    ADMIN_UPDATE("ADMIN_UPDATE"),
    ADMIN_DELETE("ADMIN_DELETE"),
    ADMIN_CREATE("ADMIN_CREATE"),
    SUPER_ADMIN_READ("SUPER_ADMIN_READ"),
    SUPER_ADMIN_UPDATE("SUPER_ADMIN_UPDATE"),
    SUPER_ADMIN_DELETE("SUPER_ADMIN_DELETE"),
    SUPER_ADMIN_CREATE("SUPER_ADMIN_CREATE");

    private final String permission;
}
