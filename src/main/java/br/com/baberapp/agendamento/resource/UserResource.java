package br.com.baberapp.agendamento.resource;

import br.com.baberapp.agendamento.constantes.Roles;
import br.com.baberapp.agendamento.dto.LoginDTO;
import br.com.baberapp.agendamento.dto.TokenDTO;
import br.com.baberapp.agendamento.dto.UserDTO;
import br.com.baberapp.agendamento.services.UserService;
import br.com.baberapp.agendamento.services.impl.TokenServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class UserResource {

    private UserService service;

    private TokenServiceImpl tokenService;

    public UserResource(UserService service, TokenServiceImpl tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createdUser(@Valid @RequestBody UserDTO dto) throws URISyntaxException {
        UserDTO result = service.create(dto);
        return ResponseEntity.created(new URI("/api/users/" + dto.getEmail())).body(result);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserDTO dto) {
        UserDTO result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{id}")
    @RolesAllowed(Roles.ROLE_USER)
    public ResponseEntity<UserDTO> findOneUser(@PathVariable("id") String id) {
        UserDTO result = service.findOne(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    @RolesAllowed(Roles.ROLE_USER)
    public ResponseEntity<Page<UserDTO>> findAllUser(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/auth")
    public ResponseEntity<TokenDTO> auth(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = loginDTO.build();
        TokenDTO token = new TokenDTO();
        token.setToken(tokenService.addAuthentication(authenticationToken));
        return ResponseEntity.ok(token);
    }
}
