document.getElementById("form-login").addEventListener("submit", async (e) => {
    e.preventDefault();
    const msgError = document.getElementById("mensaje-error");
    msgError.classList.add("d-none");

    const datos = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    try {
        const res = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datos)
        });

        const texto = await res.text();
        const data = texto ? JSON.parse(texto) : {};

        if (res.ok) {
            localStorage.setItem("usuarioLogueado", JSON.stringify(data));
            if (data.rol === "ADMIN") {
                window.location.href = "admin.html";
            } else {
                window.location.href = "consultas.html";
            }
        } else {
            msgError.innerText = data.error || "Credenciales inválidas";
            msgError.classList.remove("d-none");
        }
    } catch (err) {
        msgError.innerText = "Detalle: " + err.message;
    }
});
