package org.activiti.core.common.spring.security.test;

import static org.assertj.core.api.Assertions.assertThat;
import org.activiti.core.common.spring.security.LocalSpringSecurityContextPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import java.security.Principal;
import java.util.Optional;

public class LocalSpringSecurityContextPrincipalProviderTest_Purified {

    private LocalSpringSecurityContextPrincipalProvider subject;

    @BeforeEach
    public void setUp() {
        subject = new LocalSpringSecurityContextPrincipalProvider();
    }

    @Test
    public void testGetCurrentPrincipalNotAuthenticated_1() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assertThat(authentication.isAuthenticated()).isFalse();
    }

    @Test
    public void testGetCurrentPrincipalNotAuthenticated_2() {
        Optional<Principal> principal = subject.getCurrentPrincipal();
        assertThat(principal).isEmpty();
    }
}
