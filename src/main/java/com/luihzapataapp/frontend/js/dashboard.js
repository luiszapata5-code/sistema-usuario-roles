const API = 'http://localhost:8080';

async function cargarDashboard() {
    const token = localStorage.getItem('token');

    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    const response = await fetch(`${API}/api/me/perfil`, {
        headers: { 'Authorization': 'Bearer ' + token }
    });

    if (!response.ok) {
        window.location.href = 'login.html';
        return;
    }

    const data = await response.json();

    document.getElementById('nombre-usuario').textContent = data.nombre;
    document.getElementById('fecha-ingreso').textContent = data.fechaRegistro;
    document.getElementById('rol-usuario').textContent = data.rol;
    document.getElementById('fecha-registro').textContent = data.fechaRegistro;
    document.getElementById('ultimo-acceso').textContent = data.ultimoAcceso;
    document.getElementById('estado').textContent = data.estado ? 'Activo' : 'Inactivo';
    document.getElementById('info-nombre').textContent = data.nombre;
    document.getElementById('info-correo').textContent = data.correo;
    document.getElementById('info-rol').textContent = data.rol;
}

function cerrarSesion() {
    localStorage.clear();
    window.location.href = 'login.html';
}

cargarDashboard();