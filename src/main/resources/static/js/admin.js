document.addEventListener("DOMContentLoaded", () => {
    const user = JSON.parse(localStorage.getItem("usuarioLogueado") || "null");
    if (!user || user.rol !== "ADMIN") {
        window.location.href = "login.html";
        return;
    }

    // Cambio de pestañas seguro
    document.querySelectorAll('#adminTabs button[data-bs-target]').forEach(boton => {
        boton.addEventListener('click', (e) => {
            e.preventDefault();
            document.querySelectorAll('#adminTabs .nav-link').forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.tab-content .tab-pane').forEach(p => p.classList.remove('show', 'active'));
            boton.classList.add('active');
            const panel = document.querySelector(boton.getAttribute('data-bs-target'));
            if (panel) panel.classList.add('show', 'active');
        });
    });

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

// =========================================================
// 1. ABM SOCIOS (Ordenados por antigüedad)
// =========================================================
async function cargarSocios() {
    const res = await fetch('/api/socios/antiguedad');
    const lista = await res.json();
    document.getElementById("tabla-socios").innerHTML = lista.map(s => `
        <tr>
            <td>${s.id}</td><td>${s.nombre}</td><td>${s.dni}</td><td>${s.email}</td><td>${s.telefono}</td><td>${s.fechaIngreso}</td>
            <td>
                <button class="btn btn-warning btn-sm me-1" onclick='editarSocio(${JSON.stringify(s)})'>Editar</button>
                <button class="btn btn-danger btn-sm" onclick="borrarItem('/api/socios/${s.id}', cargarSocios)">Eliminar</button>
            </td>
        </tr>`).join("");
}

function editarSocio(s) {
    document.getElementById("soc-id-edit").value = s.id;
    document.getElementById("soc-nombre").value = s.nombre;
    document.getElementById("soc-dni").value = s.dni;
    document.getElementById("soc-direccion").value = s.direccion;
    document.getElementById("soc-telefono").value = s.telefono;
    document.getElementById("soc-fecha").value = s.fechaIngreso;
    document.getElementById("soc-email").value = s.email;
    document.getElementById("soc-pass").required = false;
    document.querySelector("#form-socio button[type='submit']").innerText = "Actualizar Socio";
}

document.getElementById("form-socio").addEventListener("submit", async (e) => {
    e.preventDefault();
    const idEdit = document.getElementById("soc-id-edit").value;
    const body = {
        nombre: document.getElementById("soc-nombre").value,
        dni: document.getElementById("soc-dni").value,
        direccion: document.getElementById("soc-direccion").value,
        telefono: document.getElementById("soc-telefono").value,
        fechaIngreso: document.getElementById("soc-fecha").value,
        email: document.getElementById("soc-email").value,
        password: document.getElementById("soc-pass").value || "sin_cambio"
    };

    const url = idEdit ? `/api/socios/${idEdit}` : '/api/socios';
    const metodo = idEdit ? 'PUT' : 'POST';

    await enviarPeticion(url, metodo, body, () => {
        e.target.reset();
        document.getElementById("soc-id-edit").value = "";
        document.getElementById("soc-pass").required = true;
        document.querySelector("#form-socio button[type='submit']").innerText = "Guardar Socio";
        cargarSocios();
    });
});

// =========================================================
// 2. ABM EMPLEADOS
// =========================================================
async function cargarEmpleados() {
    const res = await fetch('/api/empleados');
    const lista = await res.json();
    document.getElementById("tabla-empleados").innerHTML = lista.map(emp => `
        <tr>
            <td>${emp.id}</td><td>${emp.codigo}</td><td>${emp.nombre}</td><td>${emp.especialidad}</td><td>${emp.email}</td>
            <td>
                <button class="btn btn-warning btn-sm me-1" onclick='editarEmpleado(${JSON.stringify(emp)})'>Editar</button>
                <button class="btn btn-danger btn-sm" onclick="borrarItem('/api/empleados/${emp.id}', cargarEmpleados)">Eliminar</button>
            </td>
        </tr>`).join("");
}

function editarEmpleado(emp) {
    document.getElementById("emp-id-edit").value = emp.id;
    document.getElementById("emp-nombre").value = emp.nombre;
    document.getElementById("emp-codigo").value = emp.codigo;
    document.getElementById("emp-especialidad").value = emp.especialidad;
    document.getElementById("emp-direccion").value = emp.direccion;
    document.getElementById("emp-telefono").value = emp.telefono;
    document.getElementById("emp-email").value = emp.email;
    document.getElementById("emp-pass").required = false;
    document.querySelector("#form-empleado button[type='submit']").innerText = "Actualizar Empleado";
}

document.getElementById("form-empleado").addEventListener("submit", async (e) => {
    e.preventDefault();
    const idEdit = document.getElementById("emp-id-edit").value;
    const body = {
        nombre: document.getElementById("emp-nombre").value,
        codigo: document.getElementById("emp-codigo").value,
        especialidad: document.getElementById("emp-especialidad").value,
        direccion: document.getElementById("emp-direccion").value,
        telefono: document.getElementById("emp-telefono").value,
        email: document.getElementById("emp-email").value,
        password: document.getElementById("emp-pass").value || "sin_cambio"
    };

    const url = idEdit ? `/api/empleados/${idEdit}` : '/api/empleados';
    const metodo = idEdit ? 'PUT' : 'POST';

    await enviarPeticion(url, metodo, body, () => {
        e.target.reset();
        document.getElementById("emp-id-edit").value = "";
        document.getElementById("emp-pass").required = true;
        document.querySelector("#form-empleado button[type='submit']").innerText = "Guardar Empleado";
        cargarEmpleados();
    });
});

// =========================================================
// 3. ABM ZONAS
// =========================================================
async function cargarZonas() {
    const res = await fetch('/api/zonas');
    const lista = await res.json();
    document.getElementById("tabla-zonas").innerHTML = lista.map(z => `
        <tr>
            <td>${z.letra}</td><td>${z.tipoVehiculos}</td><td>${z.numeroVehiculos}</td><td>${z.profundidadGarages}m</td><td>${z.anchoGarages}m</td>
            <td>
                <button class="btn btn-warning btn-sm me-1" onclick='editarZona(${JSON.stringify(z)})'>Editar</button>
                <button class="btn btn-danger btn-sm" onclick="borrarItem('/api/zonas/${z.letra}', cargarZonas)">Eliminar</button>
            </td>
        </tr>`).join("");
}

function editarZona(z) {
    document.getElementById("zona-id-edit").value = z.letra;
    const inputLetra = document.getElementById("zona-letra");
    inputLetra.value = z.letra;
    inputLetra.readOnly = true; // La clave primaria no se cambia al editar
    document.getElementById("zona-tipo").value = z.tipoVehiculos;
    document.getElementById("zona-num").value = z.numeroVehiculos;
    document.getElementById("zona-prof").value = z.profundidadGarages;
    document.getElementById("zona-ancho").value = z.anchoGarages;
    document.querySelector("#form-zona button[type='submit']").innerText = "Actualizar";
}

document.getElementById("form-zona").addEventListener("submit", async (e) => {
    e.preventDefault();
    const idEdit = document.getElementById("zona-id-edit").value;
    const letra = document.getElementById("zona-letra").value.toUpperCase();
    const body = {
        letra: letra,
        tipoVehiculos: document.getElementById("zona-tipo").value,
        numeroVehiculos: parseInt(document.getElementById("zona-num").value),
        profundidadGarages: parseFloat(document.getElementById("zona-prof").value),
        anchoGarages: parseFloat(document.getElementById("zona-ancho").value)
    };

    const url = idEdit ? `/api/zonas/${idEdit}` : '/api/zonas';
    const metodo = idEdit ? 'PUT' : 'POST';

    await enviarPeticion(url, metodo, body, () => {
        e.target.reset();
        document.getElementById("zona-id-edit").value = "";
        document.getElementById("zona-letra").readOnly = false;
        document.querySelector("#form-zona button[type='submit']").innerText = "Crear";
        cargarZonas();
    });
});

// =========================================================
// 4. ABM GARAGES Y COMPRA
// =========================================================
async function cargarGarages() {
    const res = await fetch('/api/garages');
    const lista = await res.json();
    document.getElementById("tabla-garages").innerHTML = lista.map(g => `
        <tr>
            <td>${g.numeroGarage}</td><td>${g.zonaLetra}</td><td>${g.lecturaContadorLuz}</td><td>${g.tieneMantenimiento ? 'Sí' : 'No'}</td>
            <td>${g.socioPropietarioId ?? 'Guardería'}</td><td>${g.fechaCompraSocio ?? '-'}</td>
            <td>${g.vehiculoMatricula ?? 'Libre'}</td><td>${g.fechaAsignacionVehiculo ?? '-'}</td>
            <td>
                <button class="btn btn-warning btn-sm me-1" onclick='editarGarage(${JSON.stringify(g)})'>Editar</button>
                <button class="btn btn-danger btn-sm" onclick="borrarItem('/api/garages/${g.numeroGarage}', cargarGarages)">Eliminar</button>
            </td>
        </tr>`).join("");
}

/////
function editarGarage(g) {
    document.getElementById("gar-id-edit").value = g.numeroGarage;
    const inputNum = document.getElementById("gar-num");
    inputNum.value = g.numeroGarage;
    inputNum.readOnly = true;
    document.getElementById("gar-zona").value = g.zonaLetra;
    document.getElementById("gar-luz").value = g.lecturaContadorLuz;
    document.getElementById("gar-mant").checked = g.tieneMantenimiento;
    document.querySelector("#form-garage button[type='submit']").innerText = "Actualizar Garage";
}

document.getElementById("form-garage").addEventListener("submit", async (e) => {
    e.preventDefault();
    const idEdit = document.getElementById("gar-id-edit").value;
    const num = parseInt(document.getElementById("gar-num").value);
    const body = {
        numeroGarage: num,
        zonaLetra: document.getElementById("gar-zona").value.toUpperCase(),
        lecturaContadorLuz: parseFloat(document.getElementById("gar-luz").value),
        tieneMantenimiento: document.getElementById("gar-mant").checked
    };

    const url = idEdit ? `/api/garages/${idEdit}` : '/api/garages';
    const metodo = idEdit ? 'PUT' : 'POST';

    await enviarPeticion(url, metodo, body, () => {
        e.target.reset();
        document.getElementById("gar-id-edit").value = "";
        document.getElementById("gar-num").readOnly = false;
        document.querySelector("#form-garage button[type='submit']").innerText = "Crear Garage";
        cargarGarages();
    });
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

// =========================================================
// 5. ABM VEHÍCULOS
// =========================================================
async function cargarVehiculos() {
    const res = await fetch('/api/vehiculos');
    const lista = await res.json();
    document.getElementById("tabla-vehiculos").innerHTML = lista.map(v => `
        <tr>
            <td>${v.matricula}</td><td>${v.nombre}</td><td>${v.tipo}</td><td>${v.dimensiones}</td>
            <td>${v.socioId}</td><td>${v.numeroGarage ?? '-'}</td><td>${v.fechaAsignacion ?? '-'}</td>
            <td>
                <button class="btn btn-warning btn-sm me-1" onclick='editarVehiculo(${JSON.stringify(v)})'>Editar</button>
                <button class="btn btn-danger btn-sm" onclick="borrarItem('/api/vehiculos/${v.matricula}', () => { cargarVehiculos(); cargarGarages(); })">Eliminar</button>
            </td>
        </tr>`).join("");
}

function editarVehiculo(v) {
    document.getElementById("veh-id-edit").value = v.matricula;
    const inputMat = document.getElementById("veh-mat");
    inputMat.value = v.matricula;
    inputMat.readOnly = true;
    document.getElementById("veh-nom").value = v.nombre;
    document.getElementById("veh-tipo").value = v.tipo;
    document.getElementById("veh-dim").value = v.dimensiones;
    document.getElementById("veh-soc").value = v.socioId;
    document.getElementById("veh-gar").value = v.numeroGarage;
    document.getElementById("veh-fecha").value = v.fechaAsignacion;
    document.querySelector("#form-vehiculo button[type='submit']").innerText = "Actualizar Vehículo";
}

document.getElementById("form-vehiculo").addEventListener("submit", async (e) => {
    e.preventDefault();
    const idEdit = document.getElementById("veh-id-edit").value;
    const mat = document.getElementById("veh-mat").value.toUpperCase();
    const body = {
        matricula: mat,
        nombre: document.getElementById("veh-nom").value,
        tipo: document.getElementById("veh-tipo").value,
        dimensiones: document.getElementById("veh-dim").value,
        socioId: parseInt(document.getElementById("veh-soc").value),
        numeroGarage: parseInt(document.getElementById("veh-gar").value),
        fechaAsignacion: document.getElementById("veh-fecha").value
    };

    const url = idEdit ? `/api/vehiculos/${idEdit}` : '/api/vehiculos';
    const metodo = idEdit ? 'PUT' : 'POST';

    await enviarPeticion(url, metodo, body, () => {
        e.target.reset();
        document.getElementById("veh-id-edit").value = "";
        document.getElementById("veh-mat").readOnly = false;
        document.querySelector("#form-vehiculo button[type='submit']").innerText = "Registrar y Estacionar Vehículo";
        cargarVehiculos();
        cargarGarages();
    });
});

// =========================================================
// 6. ASIGNACIÓN EMPLEADOS A ZONAS
// =========================================================
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
    await enviarPeticion('/api/empleados/asignar-zona', 'POST', body, () => {
        e.target.reset();
        cargarAsignaciones();
    });
});

// =========================================================
// UTILIDADES GENERALES
// =========================================================
async function enviarPeticion(url, metodo, body, callback) {
    const res = await fetch(url, {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    if (res.ok) {
        mostrarAlerta(metodo === 'PUT' ? "Registro actualizado con éxito" : "Registro guardado con éxito");
        callback();
    } else {
        const err = await res.json().catch(() => ({}));
        mostrarAlerta(err.error || "Verifique los datos ingresados", "danger");
    }
}

async function borrarItem(url, callback) {
    if (!confirm("¿Confirmar eliminación del registro?")) return;
    const res = await fetch(url, { method: 'DELETE' });
    if (res.ok) {
        mostrarAlerta("Registro eliminado", "warning");
        callback();
    } else {
        mostrarAlerta("No se puede eliminar porque tiene registros asociados", "danger");
    }
}

function cerrarSesion() {
    localStorage.removeItem("usuarioLogueado");
    window.location.href = "login.html";
}

