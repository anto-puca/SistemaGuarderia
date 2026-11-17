package com.guarderia.model.entities;

//import com.guarderia.model.entities.Usuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {

    // Constructor vacío obligatorio para que Hibernate pueda instanciar la entidad
    public Administrador() {
        super();
    }

    // Constructor parametrizado opcional para facilitar la creación de objetos en Java
    public Administrador(String nombre, String direccion, String telefono, String email, String password) {
        super();
        this.setNombre(nombre);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setPassword(password);
    }
}