package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception{
         auth.inMemoryAuthentication()
                 .withUser("sa")
                 .password(getPasswordEncoder().encode("123"))
                 .roles("ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/actuator/shutdown").permitAll()
                .mvcMatchers("/h2-console/**").permitAll()
                .mvcMatchers("/**").authenticated()
                .and()
                .httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
