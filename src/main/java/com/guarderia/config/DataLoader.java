package com.guarderia.config;

import com.guarderia.model.entities.*;
        import com.guarderia.repositories.*;
        import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final ZonaRepository zonaRepo;
    private final GarageRepository garageRepo;
    private final VehiculoRepository vehiculoRepo;
    private final AsignacionRepository asignacionRepo;

    public DataLoader(UsuarioRepository usuarioRepo, ZonaRepository zonaRepo,
                      GarageRepository garageRepo, VehiculoRepository vehiculoRepo,
                      AsignacionRepository asignacionRepo) {
        this.usuarioRepo = usuarioRepo;
        this.zonaRepo = zonaRepo;
        this.garageRepo = garageRepo;
        this.vehiculoRepo = vehiculoRepo;
        this.asignacionRepo = asignacionRepo;
    }

    @Override
    public void run(String... args) {
        // Evitamos duplicar datos si ya existen registros en la BD
        if (usuarioRepo.count() > 0) return;

        // 1. Creamos el Administrador principal
        Administrador admin = new Administrador(
                "Carlos Admin", "Av. Principal 100", "1144556677",
                "admin@guarderia.com", "admin123"
        );
        usuarioRepo.save(admin);

        // 2. Creamos un Socio de prueba
        Socio socio = new Socio(
                "Juan Pérez", "Calle Falsa 123", "1122334455",
                "juan@mail.com", "socio123", "30111222", LocalDate.of(2022, 5, 10)
        );
        usuarioRepo.save(socio);

        // 3. Creamos un Empleado de prueba
        Empleado empleado = new Empleado(
                "Ana Gómez", "San Martín 450", "1199887766",
                "ana@guarderia.com", "emp123", "EMP-01", "Mantenimiento Eléctrico"
        );
        usuarioRepo.save(empleado);

        // 4. Creamos una Zona y un Garage
        Zona zonaA = new Zona("A", "Motorhome Grande", 20, 12.0, 4.5);
        zonaRepo.save(zonaA);

        Garage garage101 = new Garage();
        garage101.setNumeroGarage(101);
        garage101.setLecturaContadorLuz(145.5);
        garage101.setTieneMantenimiento(true);
        garage101.setZona(zonaA);
        garage101.setPropietario(socio);
        garage101.setFechaCompraSocio(LocalDate.of(2023, 1, 15));
        garageRepo.save(garage101);

        // 5. Registramos un Vehículo para el Socio y lo asignamos al Garage 101
        Vehiculo motorhome = new Vehiculo();
        motorhome.setMatricula("AB123CD");
        motorhome.setNombre("La Viajera");
        motorhome.setTipo("Motorhome Integral");
        motorhome.setDimensiones("8.5m x 2.5m");
        motorhome.setSocio(socio);
        vehiculoRepo.save(motorhome);

        garage101.setVehiculo(motorhome);
        garage101.setFechaAsignacionVehiculo(LocalDate.of(2023, 2, 1));
        garageRepo.save(garage101);

        // 6. Asignamos la Empleada Ana a la Zona A con 5 vehículos a cargo
        AsignacionEmpleadoZona asignacion = new AsignacionEmpleadoZona(empleado, zonaA, 5);
        asignacionRepo.save(asignacion);

        System.out.println("✅ Datos iniciales de la Guardería cargados correctamente.");
    }
}

