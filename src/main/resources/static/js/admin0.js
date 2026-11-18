document.addEventListener("DOMContentLoaded", () => {
    const user = JSON.parse(localStorage.getItem("usuarioLogueado") || "null");
    if (!user || user.rol !== "ADMIN") {
        window.location.href = "login.html";
        return;
    }
    cargarTodo();
});

function cargarTodo() {
    cargarSocios();
    cargarEmpleados();
    cargarZonas();
    cargarGarages();
    cargarVehiculos();
    cargarAsignaciones();
}

function mostrarAlerta(mensaje, tipo = "success") {
    const alerta = document.getElementById("alerta-global");
    alerta.className = `alert alert-${tipo}`;
    alerta.innerText = mensaje;
    setTimeout(() => alerta.classList.add("d-none"), 4000);
}

// --- 1. SOCIOS ---
async function cargarSocios() {
    const res = await fetch('/api/socios/antiguedad');
    const lista = await res.json();
    document.getElementById("tabla-socios").innerHTML = lista.map(s => `
        <tr>
            <td>${s.id}</td><td>${s.nombre}</td><td>${s.dni}</td><td>${s.email}</td><td>${s.telefono}</td><td>${s.fechaIngreso}</td>
            <td><button class="btn btn-danger btn-sm" onclick="borrarItem('/api/socios/${s.id}', cargarSocios)">Eliminar</button></td>
        </tr>`).join("");
}

document.getElementById("form-socio").addEventListener("submit", async (e) => {
    e.preventDefault();
    const body = {
        nombre: document.getElementById("soc-nombre").value,
        dni: document.getElementById("soc-dni").value,
        direccion: document.getElementById("soc-direccion").value,
        telefono: document.getElementById("soc-telefono").value,
        fechaIngreso: document.getElementById("soc-fecha").value,
        email: document.getElementById("soc-email").value,
        password: document.getElementById("soc-pass").value
    };
    await enviarPost('/api/socios', body, e.target, cargarSocios);
});

// --- 2. EMPLEADOS ---
async function cargarEmpleados() {
    const res = await fetch('/api/empleados');
    const lista = await res.json();
    document.getElementById("tabla-empleados").innerHTML = lista.map(emp => `
        <tr>
            <td>${emp.id}</td><td>${emp.codigo}</td><td>${emp.nombre}</td><td>${emp.especialidad}</td><td>${emp.email}</td>
            <td><button class="btn btn-danger btn-sm" onclick="borrarItem('/api/empleados/${emp.id}', cargarEmpleados)">Eliminar</button></td>
        </tr>`).join("");
}

document.getElementById("form-empleado").addEventListener("submit", async (e) => {
    e.preventDefault();
    const body = {
        nombre: document.getElementById("emp-nombre").value,
        codigo: document.getElementById("emp-codigo").value,
        especialidad: document.getElementById("emp-especialidad").value,
        direccion: document.getElementById("emp-direccion").value,
        telefono: document.getElementById("emp-telefono").value,
        email: document.getElementById("emp-email").value,
        password: document.getElementById("emp-pass").value
    };
    await enviarPost('/api/empleados', body, e.target, cargarEmpleados);
});

// --- 3. ZONAS ---
async function cargarZonas() {
    const res = await fetch('/api/zonas');
    const lista = await res.json();
    document.getElementById("tabla-zonas").innerHTML = lista.map(z => `
        <tr>
            <td>${z.letra}</td><td>${z.tipoVehiculos}</td><td>${z.numeroVehiculos}</td><td>${z.profundidadGarages}m</td><td>${z.anchoGarages}m</td>
            <td><button class="btn btn-danger btn-sm" onclick="borrarItem('/api/zonas/${z.letra}', cargarZonas)">Eliminar</button></td>
        </tr>`).join("");
}

document.getElementById("form-zona").addEventListener("submit", async (e) => {
    e.preventDefault();
    const body = {
        letra: document.getElementById("zona-letra").value.toUpperCase(),
        tipoVehiculos: document.getElementById("zona-tipo").value,
        numeroVehiculos: parseInt(document.getElementById("zona-num").value),
        profundidadGarages: parseFloat(document.getElementById("zona-prof").value),
        anchoGarages: parseFloat(document.getElementById("zona-ancho").value)
    };
    await enviarPost('/api/zonas', body, e.target, cargarZonas);
});

// --- 4. GARAGES Y COMPRA ---
async function cargarGarages() {
    const res = await fetch('/api/garages');
    const lista = await res.json();
    document.getElementById("tabla-garages").innerHTML = lista.map(g => `
        <tr>
            <td>${g.numeroGarage}</td><td>${g.zonaLetra}</td><td>${g.lecturaContadorLuz}</td><td>${g.tieneMantenimiento ? 'Sí' : 'No'}</td>
            <td>${g.socioPropietarioId ?? 'Guardería'}</td><td>${g.fechaCompraSocio ?? '-'}</td>
            <td>${g.vehiculoMatricula ?? 'Libre'}</td><td>${g.fechaAsignacionVehiculo ?? '-'}</td>
        </tr>`).join("");
}

document.getElementById("form-garage").addEventListener("submit", async (e) => {
    e.preventDefault();
    const body = {
        numeroGarage: parseInt(document.getElementById("gar-num").value),
        zonaLetra: document.getElementById("gar-zona").value.toUpperCase(),
        lecturaContadorLuz: parseFloat(document.getElementById("gar-luz").value),
        tieneMantenimiento: document.getElementById("gar-mant").checked
    };
    await enviarPost('/api/garages', body, e.target, cargarGarages);
});

document.getElementById("form-compra-garage").addEventListener("submit", async (e) => {
    e.preventDefault();
    const num = document.getElementById("comp-gar-num").value;
    const socId = document.getElementById("comp-soc-id").value;
    const fecha = document.getElementById("comp-fecha").value;
    const res = await fetch(`/api/garages/${num}/comprar?socioId=${socId}&fechaCompra=${fecha}`, { method: 'PUT' });
    if (res.ok) {
        mostrarAlerta("Compra registrada correctamente");
        e.target.reset();
        cargarGarages();
    } else {
        const err = await res.json();
        mostrarAlerta(err.error || "Error al registrar compra", "danger");
    }
});

// --- 5. VEHÍCULOS ---
async function cargarVehiculos() {
    const res = await fetch('/api/vehiculos');
    const lista = await res.json();
    document.getElementById("tabla-vehiculos").innerHTML = lista.map(v => `
        <tr>
            <td>${v.matricula}</td><td>${v.nombre}</td><td>${v.tipo}</td><td>${v.dimensiones}</td>
            <td>${v.socioId}</td><td>${v.numeroGarage}</td><td>${v.fechaAsignacion}</td>
        </tr>`).join("");
}

document.getElementById("form-vehiculo").addEventListener("submit", async (e) => {
    e.preventDefault();
    const body = {
        matricula: document.getElementById("veh-mat").value.toUpperCase(),
        nombre: document.getElementById("veh-nom").value,
        tipo: document.getElementById("veh-tipo").value,
        dimensiones: document.getElementById("veh-dim").value,
        socioId: parseInt(document.getElementById("veh-soc").value),
        numeroGarage: parseInt(document.getElementById("veh-gar").value),
        fechaAsignacion: document.getElementById("veh-fecha").value
    };
    await enviarPost('/api/vehiculos', body, e.target, () => { cargarVehiculos(); cargarGarages(); });
});

// --- 6. ASIGNACIÓN EMPLEADOS A ZONAS ---
async function cargarAsignaciones() {
    const res = await fetch('/api/empleados/asignaciones');
    const lista = await res.json();
    document.getElementById("tabla-asignaciones").innerHTML = lista.map(a => `
        <tr><td>${a.empleadoId}</td><td>${a.nombreEmpleado}</td><td>${a.zonaLetra}</td><td>${a.tipoVehiculosZona}</td><td>${a.cantidadVehiculosACargo}</td></tr>`).join("");
}

document.getElementById("form-asignacion").addEventListener("submit", async (e) => {
    e.preventDefault();
    const body = {
        empleadoId: parseInt(document.getElementById("asig-emp").value),
        zonaLetra: document.getElementById("asig-zona").value.toUpperCase(),
        cantidadVehiculosACargo: parseInt(document.getElementById("asig-cant").value)
    };
    await enviarPost('/api/empleados/asignar-zona', body, e.target, cargarAsignaciones);
});

// --- UTILIDADES ---
async function enviarPost(url, body, form, callback) {
    const res = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    if (res.ok) {
        mostrarAlerta("Operación realizada con éxito");
        form.reset();
        callback();
    } else {
        const err = await res.json();
        mostrarAlerta(err.error || "Verifique los datos ingresados", "danger");
    }
}

async function borrarItem(url, callback) {
    if (!confirm("¿Confirmar eliminación?")) return;
    await fetch(url, { method: 'DELETE' });
    mostrarAlerta("Registro eliminado", "warning");
    callback();
}

function cerrarSesion() {
    localStorage.removeItem("usuarioLogueado");
    window.location.href = "login.html";
}