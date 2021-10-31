package br.com.baberapp.agendamento.repository;

import br.com.baberapp.agendamento.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);

    Boolean existsByNameAndPassword(String name, String password);
}
