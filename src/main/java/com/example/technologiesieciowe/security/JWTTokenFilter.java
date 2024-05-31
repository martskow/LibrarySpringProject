package com.example.technologiesieciowe.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
/**
 * Filter class responsible for JWT token authentication.
 */
public class JWTTokenFilter extends OncePerRequestFilter {

    private final String key;

    /**
     * Constructs a new JWTTokenFilter with the specified key.
     *
     * @param key The secret key used to sign JWT tokens.
     */
    public JWTTokenFilter(String key) {
        this.key = key;
    }

    /**
     * Performs filtering for each incoming request to validate JWT token.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException If an error occurs during the filter processing.
     * @throws IOException      If an I/O error occurs during the filter processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader((HttpHeaders.AUTHORIZATION));
        if(headerAuth!=null && headerAuth.startsWith("Bearer")) {
            String token = headerAuth.split(" ")[1];
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String userId = claims.get("id").toString(); // to będzie zapisane w tokenie przy
            String role = claims.get("role").toString(); // tworzeniu

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userId, null, List.of(new SimpleGrantedAuthority(role))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request, response);
    }
}