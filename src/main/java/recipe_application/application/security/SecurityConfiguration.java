package recipe_application.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/webjars/**", "/images/**", "/css/**").permitAll()
                .antMatchers("/home", "/home/index", "/home/about").permitAll()
                .antMatchers("/user/list", "/user/get/{id}").permitAll()
                .antMatchers("/login", "/dashboard").permitAll()
                .anyRequest().authenticated();

        http.
                formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/dashboard");

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin").password("1234").roles("GUEST", "USER", "ADMIN").build();

        return new InMemoryUserDetailsManager(admin);
    }

}
