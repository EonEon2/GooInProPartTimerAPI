package org.gooinpro.gooinproparttimerapi.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.security.auth.CustomUserPrincipal;
import org.gooinpro.gooinproparttimerapi.security.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;


@Log4j2
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        log.info("--------------------------------------");
        log.info("shouldNotFilter!!!");

        String uri = request.getRequestURI();

       if(uri.startsWith("/api/v1/login/read")) return false;
       if(uri.startsWith("/api/v1/login/edit")) return false;

        return true;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("++++++++++++++++++++++++++++++++++++++++++");
        log.info("doFilterInternal!!! 다음 단계로 넘어감");

        log.info(request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            makeError(response, Map.of("status", 401, "msg", "No Access Token"));
            return;
        }

        // JWT validate
        try {
            Map<String, Object> claims = jwtUtil.validateToken(token);
            log.info(claims);

            // 이메일만 추출
            String email = (String) claims.get("email");

            // CustomUserPrincipal 생성 (role은 제외)
            Principal userPrincipal = new CustomUserPrincipal(email);

            // role 없이 인증 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());

            // SecurityContext에 인증 정보 설정
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.info(e.getClass().getName());
            log.info(e.getMessage());
            log.info("===================================");

            String classFullName = e.getClass().getName();
            String shortClassName = classFullName.substring(classFullName.lastIndexOf(".") + 1);

            makeError(response, Map.of("status", 401, "msg", shortClassName));

            e.printStackTrace();
        }
    }



    private void makeError(HttpServletResponse response, Map<String, Object> map) {

        Gson gson = new Gson();

        String jsonStr = gson.toJson(map);

        response.setContentType("application/json");
        response.setStatus((int)map.get("status"));
        try{

            Writer writer = response.getWriter();

            out.println(jsonStr);
            out.close();
        }catch(IOException e) {

            throw new RuntimeException(e);
        }

    }
}
