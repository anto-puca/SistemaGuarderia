package com.guarderia.services.interfaces;

import com.guarderia.model.dtos.*;
import java.time.LocalDate;
import java.util.List;

public interface IGestionGuarderiaService {
    // Zonas
    List<ZonaDTO> listarZonas();
    ZonaDTO guardarZona(ZonaDTO dto);
    void eliminarZona(String letra);

    // Garages y Compra
    List<GarageDTO> listarGarages();
    GarageDTO guardarGarage(GarageDTO dto);
    GarageDTO registrarCompraGarage(Integer numeroGarage, Long socioId, LocalDate fechaCompra);

    // Vehículos y Asignación
    List<VehiculoDTO> listarVehiculos();
    List<VehiculoDTO> listarVehiculosPorSocio(Long socioId);
    VehiculoDTO registrarVehiculoYAsignarGarage(VehiculoDTO dto);


    void eliminarGarage(Integer numeroGarage);
    void eliminarVehiculo(String matricula);
////
    VehiculoDTO actualizarVehiculo(String matricula, VehiculoDTO dto);


}

