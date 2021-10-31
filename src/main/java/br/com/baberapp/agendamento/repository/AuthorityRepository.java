package br.com.baberapp.agendamento.repository;

import br.com.baberapp.agendamento.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Role, String> {


}
