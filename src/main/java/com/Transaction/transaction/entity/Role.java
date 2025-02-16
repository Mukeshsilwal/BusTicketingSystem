package com.Transaction.transaction.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.Transaction.transaction.entity.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            new LinkedHashSet<>(Arrays.asList(ADMIN_CREATE, ADMIN_DELETE, ADMIN_UPDATE, ADMIN_READ))
    ),
    SUPER_ADMIN(
            new LinkedHashSet<>(Arrays.asList(ADMIN_CREATE, ADMIN_DELETE, ADMIN_UPDATE, ADMIN_READ, SUPER_ADMIN_CREATE, SUPER_ADMIN_READ, SUPER_ADMIN_UPDATE, SUPER_ADMIN_DELETE))
    ),
    USER(Collections.emptySet());

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authority = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
        authority.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authority;

    }

}
