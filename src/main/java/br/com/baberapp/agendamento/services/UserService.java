package br.com.baberapp.agendamento.services;

import br.com.baberapp.agendamento.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDTO create(UserDTO user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void delete(String id);

    Page<UserDTO> findAll(Pageable pageable);

    UserDTO findOne(String id);

    UserDTO update(String id, UserDTO dto);

    UserDetails findByUserId(String userId);
}
