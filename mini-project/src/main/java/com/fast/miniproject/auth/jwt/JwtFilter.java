package com.fast.miniproject.auth.jwt;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.auth.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!tokenService.checkLogout(token)) {

            Claims claims = jwtProvider.tokenToUser(token);
            if (claims != null) {
                LoginReqDTO dto = new LoginReqDTO(claims);
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities()));
            }
        }
        filterChain.doFilter(request,response);
    }
}
