package com.spring.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws
            ServletException, IOException {
        String authorizationHeader = request.getHeader(JwtConstant.JWT_TOKEN_HEADER);
        if (authorizationHeader != null) {
            authorizationHeader = authorizationHeader.substring(7);
            // validate the token
            // if the token is valid, set the authentication in the context
            // if the token is invalid, throw an exception
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(authorizationHeader)
                        .getBody();
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList(authorities);
                // set the authentication in the context
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            catch (Exception e) {
                throw new BadCredentialsException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
