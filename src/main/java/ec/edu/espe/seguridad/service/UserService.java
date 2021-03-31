package ec.edu.espe.seguridad.service;

import ec.edu.espe.seguridad.api.dto.UserRq;
import ec.edu.espe.seguridad.exception.CredentialInvalidException;
import ec.edu.espe.seguridad.exception.DocumentNotFoundException;
import ec.edu.espe.seguridad.model.User;
import ec.edu.espe.seguridad.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRq login(String username, String pwd)
            throws DocumentNotFoundException, CredentialInvalidException {
        log.info("Usuario: " + username + " --pwd:" + pwd);
        User user = userRepository.findByUser(username);
        log.info("Usuario: " + user.getUser());
        if (user != null) {
            if (user.getPassword().equals(pwd)) {
                String token = getJwtToken(user);
                user.setDateLogin(new Date());
                userRepository.save(user);
                return UserRq.builder().user(username).token(token).build();
            } else {
                throw new CredentialInvalidException("Credenciales incorrectas");
            }
        } else {
            throw new DocumentNotFoundException("Usuario no existente " + username);
        }
    }

    private String getJwtToken(User user) {
        String secretKey = "DanielAriel-ESPE";
        List<String> roles = user.getRoles();
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        String token = Jwts.builder().setId("softtekJWT").setSubject(user.getUser())
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
