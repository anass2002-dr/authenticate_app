package com.authenticate_app.authenticate_app.Config;

import com.authenticate_app.authenticate_app.JWT.JwtService;
import com.authenticate_app.authenticate_app.ServiceImpl.UserDetailServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    UserDetailServiceImp userDetailServiceImp;
    @Autowired
    JwtService jwtService;
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String autheader=request.getHeader("Authorization");
        if(autheader==null || !autheader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String Token=autheader.substring(7);
        String username=jwtService.extractUsername(Token);
        log.info(Token);
        log.info(username);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetail=userDetailServiceImp.loadUserByUsername(username);
            if(jwtService.isValid(Token,userDetail)){
                UsernamePasswordAuthenticationToken authenticationToken=new
                        UsernamePasswordAuthenticationToken
                        (userDetail,null,userDetail.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
