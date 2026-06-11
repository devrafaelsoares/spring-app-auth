package br.devrafaelsoares.SpringBootAuth.seeders;

import br.devrafaelsoares.SpringBootAuth.domain.user.Role;
import br.devrafaelsoares.SpringBootAuth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        log.info("Iniciando o seeder de Roles...");

        List<String> defaultRoles = List.of("USER", "ADMIN");

        for (String roleName : defaultRoles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = Role.builder()
                        .name(roleName)
                        .build();
                roleRepository.save(role);
                log.info("Role '{}' criada com sucesso.", roleName);
            }
        }

        log.info("Seeder de Roles finalizado.");
    }
}
