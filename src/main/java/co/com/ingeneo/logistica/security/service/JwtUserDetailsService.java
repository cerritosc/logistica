package co.com.ingeneo.logistica.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.common.Constants;

@Service
@Qualifier("JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private static final String MESSAGE = "Usuario no encontrado!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscar usuario
        if (!username.equals(Constants.USUARIO_MOBILE)) {
            throw new UsernameNotFoundException(MESSAGE);
        } else {
            return new User(username, Constants.USUARIO_PASSWORD, new ArrayList<>());
        }
    }

}
