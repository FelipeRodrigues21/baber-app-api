package br.com.baberapp.agendamento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;

    @NotBlank(message = "Favor informar o login")
    private String name;

    @NotBlank
    @Email(message = "Email Invalído")
    private String email;

    @NotBlank(message = "Favor informar a senha")
    private String password;
}
