package SpringPractice.Security;

import SpringPractice.Handlers.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.*;


import java.util.function.Function;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    Function<String,String> encodingFunction = password-> passwordEncoder().encode(password);

    @Bean
    MapReactiveUserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("misha").
                password("0123").passwordEncoder(encodingFunction).
                roles("USER").build();

        UserDetails admin = User.withUsername("admin").
                password("3210").passwordEncoder(encodingFunction).
                roles("USER","ADMIN").build();
        return new MapReactiveUserDetailsService(user,admin);
    }

    // TODO: create custom login and logout page.

    @Bean
    SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange().
                pathMatchers("/news").permitAll().
                pathMatchers("/news2","/html/**").permitAll().
                pathMatchers("/user/{username}").access((mono,context)->
                                mono.map(auth->auth.getName().equals(context.getVariables().get("username"))).
                                map(AuthorizationDecision::new)).
                anyExchange().authenticated().
                and().httpBasic().
                and().formLogin().
                authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/user")).
                and().build();
    }

}
