package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.persistence.*;

@Entity(name = "phone")
@Table(name = "\"phone\"")
@Data //Genera automaticamente getters, setters, equals, toString, hashcode, etc; al momento de compilar.
@AllArgsConstructor //Genera automaticamente los constructores con todos los argumentos.
@NoArgsConstructor //Genera automaticamente el constructor sin argumentos.
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne
    private User user;
}
