package sakura.softwareProject.security;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.repository.MemberRepository;

import java.util.Enumeration;
import java.util.Optional;

@AllArgsConstructor
public class Interceptor implements HandlerInterceptor {

    final private JwtProvider jwtProvider;
    final private MemberRepository memberRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("Authorization") == null){
            return false;
        }

        String token = request.getHeader("Authorization").substring(7);

        if(!jwtProvider.validateToken(token)) {
            return false;
        }
        Long id = Long.parseLong(jwtProvider.getId(token));
        Optional<Member> member= memberRepository.findById(id);
        if(member.isPresent()){
            request.setAttribute("member", member.get());
            return true;
        } else {
            return false;
        }
    }
}
