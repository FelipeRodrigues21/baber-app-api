package br.com.baberapp.agendamento.services.impl;

import br.com.baberapp.agendamento.domain.User;
import br.com.baberapp.agendamento.dto.UserDTO;
import br.com.baberapp.agendamento.mapper.UserMapper;
import br.com.baberapp.agendamento.repository.UserRepository;
import br.com.baberapp.agendamento.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO create(UserDTO user) {
        User saved = userRepository.save(userMapper.toEntity(user));
        return userMapper.toDTO(saved);
    }

    @Transactional
    public UserDTO update(String id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    public UserDTO findOne(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        return userMapper.toDTO(user);
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        List<UserDTO> result = userRepository.findAll(pageable)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(result);
    }

    @Transactional
    public void delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> possibleUser = userRepository.findByName(username);
        return possibleUser.orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrarar o usuário ".concat(username)));
    }

    public UserDetails findByUserId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Não foi possível encontrar o usuário com o ID:".concat(" ").concat(userId)));
    }

}
