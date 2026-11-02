package com.guarderia.service;

import com.guarderia.model.Garage;
import com.guarderia.model.Socio;
import com.guarderia.model.Vehiculo;
import com.guarderia.repository.GarageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de Lógica de Negocio Centralizado para la gestión de Garages y Celdas de Almacenamiento.
 * Aplica el principio de Desacoplamiento Arquitectónico (SOLID - SRP) aislando por completo
 * la capa de control (Web) de la persistencia directa (SQL).
 */
@Service
public class GarageService {

    private final GarageRepository garageRepository;
    private final SocioService socioService;
    private final VehiculoService vehiculoService;

    // Inyección de dependencias exclusivamente por constructor (Inversión de Dependencias)
    public GarageService(GarageRepository garageRepository, SocioService socioService, VehiculoService vehiculoService) {
        this.garageRepository = garageRepository;
        this.socioService = socioService;
        this.vehiculoService = vehiculoService;
    }

    // ============================================================================
    // CONSULTAS GENERALES DE LECTURA (Desacopladas y Optimizadas para Hibernate)
    // ============================================================================

    /**
     * Recupera el listado completo de garages sin filtros.
     * Mantiene la jerarquía de capas impidiendo que el Controller acceda directo al Repository.
     */
    @Transactional(readOnly = true)
    public List<Garage> listarTodos() {
        return garageRepository.findAll();
    }

    /**
     * Busca una celda de garage específica a través de su número identificador.
     */
    @Transactional(readOnly = true)
    public Garage buscarPorId(Integer numeroGarage) {
        return garageRepository.findById(numeroGarage)
                .orElseThrow(() -> new RuntimeException("Error de sistema: El garage Nº " + numeroGarage + " no existe."));
    }

    // ============================================================================
    // FILTROS DE DISPONIBILIDAD (Para optimizar los desplegables de las vistas)
    // ============================================================================

    /**
     * Criterio 1: Garages libres para SER COMPRADOS.
     * Retorna las celdas que no tienen un Socio propietario asignado actualmente (socio_id IS NULL).
     */
    @Transactional(readOnly = true)
    public List<Garage> listarDisponiblesParaVenta() {
        return garageRepository.findBySocioIsNull();
    }

    /**
     * Criterio 2: Garages libres para APARCAR / GUARDAR UN VEHÍCULO.
     * Retorna las celdas donde físicamente no hay ningún motorhome o trailer estacionado (vehiculo_matricula IS NULL),
     * independientemente de si la cochera ya fue vendida o si es stock propio de la guardería.
     */
    @Transactional(readOnly = true)
    public List<Garage> listarDisponiblesParaEstacionar() {
        return garageRepository.findByVehiculoIsNull();
    }

    // ============================================================================
    // FLUJO A: GESTIÓN DE PROPIEDAD - El socio compra el Garage (Vehículo Opcional)
    // ============================================================================
    /**
     * Registra la transacción comercial de compra de una celda por parte de un Socio.
     * Guarda la fecha de compra y el estado del servicio de mantenimiento exigido por la consigna.
     */
    @Transactional
    public void registrarCompraDeGarage(Integer numeroGarage, Long socioId, Boolean tieneMantenimiento) {
        Garage garage = buscarPorId(numeroGarage);
        Socio comprador = socioService.buscarPorId(socioId);

        garage.setSocio(comprador);
        garage.setFechaCompra(LocalDate.now()); // Cumple consigna: "siendo importante la fecha de compra del garage"
        garage.setTieneMantenimiento(tieneMantenimiento != null && tieneMantenimiento); // Atributo descriptivo solicitado

        garageRepository.save(garage);
    }

    // ============================================================================
    // FLUJO B: ACTIVIDAD PRINCIPAL - Guardar / Estacionar un Vehículo en una Celda
    // ============================================================================
    /**
     * Asigna físicamente un vehículo a un garage para su resguardo diario.
     * No requiere 'socioId' porque el objeto Vehiculo ya conoce a su dueño (Única Fuente de Verdad).
     * Permite guardar unidades en celdas cuya propiedad (socio_id) sea NULL (pertenecientes a la guardería).
     */
    @Transactional
    public void guardarVehiculoEnGarage(Integer numeroGarage, String matriculaVehiculo) {
        Garage garage = buscarPorId(numeroGarage);

        if (matriculaVehiculo != null && !matriculaVehiculo.isEmpty()) {
            // Se recupera el vehículo. Este ya arrastra la relación e identidad de su Socio dueño de forma transparente.
            Vehiculo vehiculo = vehiculoService.buscarPorMatricula(matriculaVehiculo);

            // Registramos la ocupación física del espacio y la bitácora temporal
            garage.setVehiculo(vehiculo);
            garage.setFechaAsignacionVehiculo(LocalDate.now()); // Cumple consigna: "Es importante la fecha en que un vehículo es asignado"
        } else {
            // Caso de uso: Retiro físico de la unidad (Desocupar cochera sin alterar al propietario del inmueble)
            if (garage.getVehiculo() != null) {
                garage.setVehiculo(null);
                garage.setFechaAsignacionVehiculo(null);
            }
        }

        garageRepository.save(garage);
    }

    // ============================================================================
    // FLUJO C: LIBERACIÓN TOTAL - Revocación completa de celdas
    // ============================================================================
    /**
     * Blanquea por completo todos los estados de la cochera, devolviéndola al inventario libre de la guardería.
     */
    @Transactional
    public void liberarGarageCompleto(Integer numeroGarage) {
        Garage garage = buscarPorId(numeroGarage);

        garage.setSocio(null);
        garage.setFechaCompra(null);
        garage.setVehiculo(null);
        garage.setFechaAsignacionVehiculo(null);
        garage.setTieneMantenimiento(false);

        garageRepository.save(garage);
    }


    @Transactional
    public void guardarNuevoGarage(Garage garage) {
        if (garageRepository.existsById(garage.getNumeroGarage())) {
            throw new IllegalArgumentException("El garage Nº " + garage.getNumeroGarage() + " ya está registrado en el inventario.");
        }
        garageRepository.save(garage);
    }

    @Transactional
    public void eliminarGarageFisico(Integer numeroGarage) {
        if (!garageRepository.existsById(numeroGarage)) {
            throw new RuntimeException("El garage seleccionado no existe.");
        }
        garageRepository.deleteById(numeroGarage);
    }

}
