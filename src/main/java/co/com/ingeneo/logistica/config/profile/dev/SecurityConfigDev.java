package co.com.ingeneo.logistica.config.profile.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class SecurityConfigDev {
    @Bean
    public AuthenticationProvider authenticationProvider(@Autowired PasswordEncoder passwordEncoder
            , @Autowired @Qualifier("UserDetailsServiceImpl") UserDetailsService userDetailsService
    ) {
        MockAuthenticationProvider authProvider = new MockAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
