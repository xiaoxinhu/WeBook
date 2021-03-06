package com.example.demo.security;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.user.SecurityUser;
import com.example.demo.model.user.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @Override
//    auth.userDetailsService(myUserDetailService)????????????????????????myUserDetailService
//    ?????????SpringSecurity???????????????????????????SpringSecurity???????????????????????????UserDetailsService,????????????????????????
    //????????????????????????????????????????????????????????????????????????????????????????????????
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());

//        auth.inMemoryAuthentication().withUser("?????????").password("$2a$10$vEBSLD7Wy7UsF2raEtZa0e5uUg0HGOosxEycfLdJj4LtkMiQIZXpm").roles("user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/states","/user/switch","/book/modify").hasAnyRole("ADMIN")
                .antMatchers("/user/init","/book/get","/user/code","/user/signup","/book/detail","/book/search","/images/*","/mail").permitAll()
                .antMatchers("/book/*", "/register","/sort/*","/topic/*","/user/register","/user/accountVerify")// ????????????????????????????????????
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//??????????????????????????????options??????
                .permitAll()
//                .antMatchers("/**")//???????????????????????????
//                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll().logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                HttpSession session = request.getSession();
                session.removeAttribute("loginState");
                out.write(new ObjectMapper().writeValueAsString( "????????????"));
                out.flush();
                out.close();
            }
        })
                .and().csrf().disable()
                // ????????????
                .cors().and();
        // ????????????
        http.headers().cacheControl();

        // ??????JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //????????????????????????????????????????????????
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        //???????????????????????????
        http.addFilterAt(CAFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    CustomAuthenticationFilter CAFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                System.out.println("=============????????????===============");
                //???authentication?????????SecurityUser?????????
                SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
                String token = jwtTokenUtil.generateToken(securityUser);
                token = "Bearer"+token;
                System.out.println("====token:====="+token+"=====");
                resp.setHeader("Authorization", token);
                resp.setHeader("Access-control-Expose-Headers", "Authorization");
                System.out.println("???authentication????????????????????????"+securityUser.toString());
                User user = new User();
                user.setAccount(securityUser.getUsername());
                boolean isManage = userService.getUser(user.getAccount()).isManage();
                if(isManage){
                    user.setManage(true);
                }else {
                    user.setManage(false);
                }
                Map<String,Object> map = new HashMap<>();
                map.put("user",user);
                String json = JSON.toJSONString(ResultUtil.resultSuccess(map));
                out.print(json);
                out.flush();
                out.close();
            }
        });
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                System.out.println("=============????????????=================");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString("????????????"));
                out.flush();
                out.close();
            }
        });
        //authenticationManagerBean()???WebSecurityConfigurerAdapter?????????????????????
        // ???????????????????????????AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}
