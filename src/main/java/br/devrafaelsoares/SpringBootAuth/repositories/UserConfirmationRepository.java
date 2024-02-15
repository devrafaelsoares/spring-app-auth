package br.devrafaelsoares.SpringBootAuth.repositories;

import br.devrafaelsoares.SpringBootAuth.domain.user.UserConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserConfirmationRepository extends JpaRepository<UserConfirmation, UUID> {
    Optional<UserConfirmation> findByToken(UUID token);
}
