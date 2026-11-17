package com.guarderia.services.interfaces;

import com.guarderia.model.dtos.LoginRequestDTO;
import com.guarderia.model.dtos.LoginResponseDTO;
import com.guarderia.model.dtos.SocioCreateDTO;
import com.guarderia.model.dtos.SocioDTO;

import java.util.List;

public interface IUsuarioService {
    LoginResponseDTO autenticar(LoginRequestDTO loginDTO);
    List<SocioDTO> listarSociosPorAntiguedad();
    SocioDTO registrarSocio(SocioCreateDTO dto);
    void eliminarSocio(Long id);
}