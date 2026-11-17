document.addEventListener("DOMContentLoaded", async () => {
    const usuarioStr = localStorage.getItem("usuarioLogueado");
    if (!usuarioStr) {
        window.location.href = "login.html";
        return;
    }

    const usuario = JSON.parse(usuarioStr);
    document.getElementById("bienvenida").innerText = `Hola, ${usuario.nombre} (${usuario.rol})`;

    if (usuario.rol === "SOCIO") {
        document.getElementById("panel-socio").classList.remove("d-none");
        await cargarDatosSocio(usuario.id);
    } else if (usuario.rol === "EMPLEADO") {
        document.getElementById("panel-empleado").classList.remove("d-none");
        await cargarDatosEmpleado(usuario.id);
    }
});

async function cargarDatosSocio(socioId) {
    const res = await fetch(`/api/vehiculos/socio/${socioId}`);
    const vehiculos = await res.json();
    document.getElementById("tabla-vehiculos-socio").innerHTML = vehiculos.map(v => `
        <tr>
            <td>${v.matricula}</td>
            <td>${v.nombre}</td>
            <td>${v.tipo}</td>
            <td>${v.dimensiones}</td>
            <td>${v.numeroGarage ?? 'Sin asignar'}</td>
            <td>${v.fechaAsignacion ?? '-'}</td>
        </tr>
    `).join("");
}

async function cargarDatosEmpleado(empleadoId) {
    const res = await fetch(`/api/empleados/${empleadoId}/asignaciones`);
    const asignaciones = await res.json();
    document.getElementById("tabla-zonas-empleado").innerHTML = asignaciones.map(a => `
        <tr>
            <td>Zona ${a.zonaLetra}</td>
            <td>${a.tipoVehiculosZona}</td>
            <td>${a.cantidadVehiculosACargo} vehículos</td>
        </tr>
    `).join("");
}

function cerrarSesion() {
    localStorage.removeItem("usuarioLogueado");
    window.location.href = "login.html";
}
