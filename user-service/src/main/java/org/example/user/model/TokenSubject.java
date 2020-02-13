package org.example.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TokenSubject {
    private String email;
    private UUID userId;
    private Collection<? extends GrantedAuthority> authorities;
    private long expiration;
    private String scope;
}
