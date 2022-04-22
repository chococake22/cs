package fixel.cs.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()  // rest api로만 사용하기 때문에 기본 설정은 하지 않는다. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable()   // rest api이므로 csrf 보안은 사용할 필요가 없다.
                .cors()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰으로 인증하기 때문에 Session은 사용할 필요가 없음.
                .and()
                    .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
                    .anyRequest().permitAll();

    }

}
