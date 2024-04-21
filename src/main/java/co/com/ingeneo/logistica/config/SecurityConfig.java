package co.com.ingeneo.logistica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // filtro tradicional de Spring retiene orden mas bajo que el rest
    @Bean
    @Order(20)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf()
                    .ignoringRequestMatchers("/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/assets/**"
                        ,"/dist/**"
                        ,"/css/**"
                        ,"/js/**"
                        , "/img/**"
                        , "/images/**"
                        , "/public/**"
                        , "/**"
                        , "/").permitAll()
                .anyRequest().denyAll()
                .and()
                .build();
    }
    
    /** Este bean crea el authenticationManager usado por el aplicativo. El authenticationProvider
     *  es establecido dependiendo del perfil usado:
     * 
     *  - si es dev, se usa una version personalizada que no requiere password
     *  - si es prod, se usa la version que hace match con el password
     * 
     * @param http dependencia de Spring donde se definen los paths a asegurar
     * @param authenticationProvider el proveedor de autenticacion configurado por perfil
     * @return
     * @throws Exception 
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, @Autowired AuthenticationProvider authenticationProvider)
            throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class).authenticationProvider(authenticationProvider)
                .build();
    }
}
