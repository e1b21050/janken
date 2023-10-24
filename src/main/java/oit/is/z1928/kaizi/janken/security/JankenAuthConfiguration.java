package oit.is.z1928.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class JankenAuthConfiguration {

  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(AntPathRequestMatcher.antMatcher("/janken/**")).authenticated()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll())
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/*")))
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin()));
    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    UserDetails user1 = User.withUsername("ほんだ")
        .password("{bcrypt}$2y$10$E2VcwndEzQxGzBCgxseN8er1Zv0CHfLf2rCbuXzG8E5dhOxgstR7W").roles("USER").build();
    UserDetails user2 = User.withUsername("いがき")
        .password("{bcrypt}$2y$10$E2VcwndEzQxGzBCgxseN8er1Zv0CHfLf2rCbuXzG8E5dhOxgstR7W").roles("USER").build();
    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）

    return new InMemoryUserDetailsManager(user1, user2);
  }

}
