package br.com.baberapp.agendamento.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public UsernamePasswordAuthenticationToken build(){
        return new UsernamePasswordAuthenticationToken(this.login,this.password);
    }
}
