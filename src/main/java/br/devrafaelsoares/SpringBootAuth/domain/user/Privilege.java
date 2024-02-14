package br.devrafaelsoares.SpringBootAuth.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "privileges")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Privilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}
