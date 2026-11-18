package com.guarderia.services.impl;


import com.guarderia.model.dtos.LoginRequestDTO;
import com.guarderia.model.dtos.LoginResponseDTO;
import com.guarderia.model.dtos.SocioCreateDTO;
import com.guarderia.model.dtos.SocioDTO;
import com.guarderia.model.entities.Administrador;
import com.guarderia.model.entities.Empleado;
import com.guarderia.model.entities.Socio;
import com.guarderia.model.entities.Usuario;
import com.guarderia.repositories.UsuarioRepository;
import com.guarderia.services.interfaces.IUsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public LoginResponseDTO autenticar(LoginRequestDTO loginDTO) {
        Usuario user = usuarioRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.getPassword().equals(loginDTO.password())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Identificamos qué subclase de Hibernate ingresó
        String rol = switch (user) {
            case Administrador a -> "ADMIN";
            case Socio s -> "SOCIO";
            case Empleado e -> "EMPLEADO";
            default -> "DESCONOCIDO";
        };

        return new LoginResponseDTO(user.getId(), user.getNombre(), user.getEmail(), rol);
    }

    @Override
    public List<SocioDTO> listarSociosPorAntiguedad() {
        return usuarioRepository.findAllSociosByAntiguedad().stream()
                .map(SocioDTO::new)
                .toList();
    }

    @Override
    public SocioDTO registrarSocio(SocioCreateDTO dto) {
        Socio socio = new Socio();
        socio.setNombre(dto.nombre());
        socio.setDireccion(dto.direccion());
        socio.setTelefono(dto.telefono());
        socio.setEmail(dto.email());
        socio.setPassword(dto.password());
        socio.setDni(dto.dni());
        socio.setFechaIngreso(dto.fechaIngreso());

        return new SocioDTO(usuarioRepository.save(socio));
    }

    @Override
    public void eliminarSocio(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public SocioDTO actualizarSocio(Long id, SocioCreateDTO dto) {
        Socio socio = (Socio) usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
        socio.setNombre(dto.nombre());
        socio.setDireccion(dto.direccion());
        socio.setTelefono(dto.telefono());
        socio.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isBlank()) {
            socio.setPassword(dto.password());
        }
        socio.setDni(dto.dni());
        socio.setFechaIngreso(dto.fechaIngreso());
        return new SocioDTO(usuarioRepository.save(socio));
    }
}

