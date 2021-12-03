package pl.kamilnowak.flatrentalmanagementsystem.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

public class JWTFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(authorization);
            if (authenticationToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is no valid");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String authorization) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512("a8e3ff9bbb091fd856302903cfc1b9f5fcb3f87cca03df816f05b60c242a086d")).build();
        DecodedJWT verify;
        try {
            verify = jwtVerifier.verify(authorization.substring(7));
        } catch (Exception e) {
            return null;
        }
        String name = verify.getClaim("mail").asString();
        String role = verify.getClaim("role").asString();
        long validDate = verify.getClaim("validTo").asLong();
        long createDate = verify.getClaim("createDate").asLong();

        if (validDate < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) {
            return null;
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, null, Collections.singleton(simpleGrantedAuthority));
        return token;
    }
}
