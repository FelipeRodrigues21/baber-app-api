package br.com.baberapp.agendamento.services.impl;

import br.com.baberapp.agendamento.domain.User;
import br.com.baberapp.agendamento.repository.AuthorityRepository;
import br.com.baberapp.agendamento.repository.UserRepository;
import br.com.baberapp.agendamento.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    static final long EXPIRATION_TIME = 860_000_000;

    static final String SECRET = "fa0c81df30f94c98923091a21642b536";

    static final String TOKEN_PREFIX = "Bearer";

    static final String AUTHORITIES_KEY = "auth";

    public String addAuthentication(Authentication authentication) {

        User user = (User) userService.loadUserByUsername(authentication.getPrincipal().toString());

        String authorities = authorityRepository.findById(user.getAuthorities().stream().findFirst().get().getAuthority()).orElse(null).getAuthority();


        if (!user.getPassword().equals(authentication.getCredentials())) {
            throw new UsernameNotFoundException("Senha Incorreta");
        }

        String JWT = Jwts.builder()
                .setIssuer("Baber App")
                .setSubject(user.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return TOKEN_PREFIX.concat(" ").concat(JWT);

    }

    public Boolean isValid(String jwt) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserId(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }

}
