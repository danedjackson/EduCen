package com.jackson.educen.configurations;

import com.jackson.educen.services.IJwtService;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.IUserService;
import com.jackson.educen.services.impl.JwtService;
import com.jackson.educen.services.impl.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserService userService;
    private final ILogger logger;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain){
        try {
            // Get JWT from the Authorization header in request
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            final String userRole;

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Extract information from the JWT
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            //userRole = jwtService.extractRole(jwt);

            // Validate information from JWT
            if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                //TODO: If Login by Username, change logic to load user by userName and not userEmail
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    // Use role retrieved from JWT
//                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, AuthorityUtils.createAuthorityList(userRole)
//                    );
                    // Use role retrieved from db
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Update our security context
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request, response);
        } catch(Exception e) {
            logger.errorLog("Internal filter error: " + e.getMessage());
        }
    }
}
