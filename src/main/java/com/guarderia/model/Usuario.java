package com.guarderia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia del DER
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;
}
