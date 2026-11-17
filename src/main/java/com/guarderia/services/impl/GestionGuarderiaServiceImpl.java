package com.guarderia.services.impl;

import com.guarderia.model.dtos.*;
import com.guarderia.model.entities.*;
import com.guarderia.repositories.*;
import com.guarderia.services.interfaces.IGestionGuarderiaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class GestionGuarderiaServiceImpl implements IGestionGuarderiaService {

    private final ZonaRepository zonaRepo;
    private final GarageRepository garageRepo;
    private final VehiculoRepository vehiculoRepo;
    private final UsuarioRepository usuarioRepo;

    public GestionGuarderiaServiceImpl(ZonaRepository zonaRepo, GarageRepository garageRepo,
                                       VehiculoRepository vehiculoRepo, UsuarioRepository usuarioRepo) {
        this.zonaRepo = zonaRepo;
        this.garageRepo = garageRepo;
        this.vehiculoRepo = vehiculoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public List<ZonaDTO> listarZonas() {
        return zonaRepo.findAll().stream().map(ZonaDTO::new).toList();
    }

    @Override
    public ZonaDTO guardarZona(ZonaDTO dto) {
        Zona z = new Zona(dto.letra(), dto.tipoVehiculos(), dto.numeroVehiculos(), dto.profundidadGarages(), dto.anchoGarages());
        return new ZonaDTO(zonaRepo.save(z));
    }

    @Override
    public void eliminarZona(String letra) {
        zonaRepo.deleteById(letra);
    }

    @Override
    public List<GarageDTO> listarGarages() {
        return garageRepo.findAll().stream().map(GarageDTO::new).toList();
    }

    @Override
    public GarageDTO guardarGarage(GarageDTO dto) {
        Zona zona = zonaRepo.findById(dto.zonaLetra())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        Garage g = new Garage();
        g.setNumeroGarage(dto.numeroGarage());
        g.setLecturaContadorLuz(dto.lecturaContadorLuz());
        g.setTieneMantenimiento(dto.tieneMantenimiento());
        g.setZona(zona);

        return new GarageDTO(garageRepo.save(g));
    }

    @Override
    @Transactional
    public GarageDTO registrarCompraGarage(Integer numeroGarage, Long socioId, LocalDate fechaCompra) {
        Garage garage = garageRepo.findById(numeroGarage)
                .orElseThrow(() -> new RuntimeException("Garage no encontrado"));

        Usuario usuario = usuarioRepo.findById(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

        if (!(usuario instanceof Socio socio)) {
            throw new RuntimeException("El usuario seleccionado no es un Socio");
        }

        // Regla de negocio: fecha de compra independiente de la asignación de vehículo
        garage.setPropietario(socio);
        garage.setFechaCompraSocio(fechaCompra != null ? fechaCompra : LocalDate.now());

        return new GarageDTO(garageRepo.save(garage));
    }

    @Override
    public List<VehiculoDTO> listarVehiculos() {
        return vehiculoRepo.findAll().stream().map(VehiculoDTO::new).toList();
    }

    @Override
    public List<VehiculoDTO> listarVehiculosPorSocio(Long socioId) {
        return vehiculoRepo.findBySocioId(socioId).stream().map(VehiculoDTO::new).toList();
    }

    @Override
    @Transactional
    public VehiculoDTO registrarVehiculoYAsignarGarage(VehiculoDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.socioId())
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

        if (!(usuario instanceof Socio socio)) {
            throw new RuntimeException("El propietario debe ser un Socio");
        }

        Garage garage = garageRepo.findById(dto.numeroGarage())
                .orElseThrow(() -> new RuntimeException("Garage no encontrado"));

        if (garage.getVehiculo() != null) {
            throw new RuntimeException("El garage ya está ocupado por otro vehículo");
        }

        // 1. Creamos el vehículo
        Vehiculo v = new Vehiculo();
        v.setMatricula(dto.matricula());
        v.setNombre(dto.nombre());
        v.setTipo(dto.tipo());
        v.setDimensiones(dto.dimensiones());
        v.setSocio(socio);
        vehiculoRepo.save(v);

        // 2. Asignamos el vehículo al garage con su fecha correspondiente
        garage.setVehiculo(v);
        garage.setFechaAsignacionVehiculo(dto.fechaAsignacion() != null ? dto.fechaAsignacion() : LocalDate.now());
        garageRepo.save(garage);

        v.setGarage(garage);
        return new VehiculoDTO(v);
    }
}
