package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "user")
@Table(name = "\"user\"")
@Data //Genera automaticamente getters, setters, equals, toString, hashcode, etc; al momento de compilar.
@AllArgsConstructor //Genera automaticamente los constructores con todos los argumentos.
@NoArgsConstructor //Genera automaticamente el constructor sin argumentos.
@IdClass(UserId.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Id
    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime creationTime = LocalDateTime.now();

    private LocalDateTime modificationTime;

    private LocalDateTime lastLogin = LocalDateTime.now();

    private boolean isActive;

    @OneToMany(targetEntity = Phone.class, mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<Phone> phones;
}
