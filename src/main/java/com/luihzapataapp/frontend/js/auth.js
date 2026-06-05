const API = 'http://localhost:8080';

async function login() {
    const correo = document.getElementById('label-correo').value;
    const contrasena = document.getElementById('label-contraseña').value;

    const response = await fetch(`${API}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ correo, contrasena })
    });

    const data = await response.json();
    

    if (response.ok) {
        localStorage.setItem('token', data.token);
        localStorage.setItem('rol', data.rol);
        localStorage.setItem('nombre', data.nombre);
        localStorage.setItem('idAdmin', data.id);

        if (data.rol === 'admin') {
            window.location.href = 'admin.html';
        } else {
            window.location.href = 'dashboard.html';
        }
    } else {
        alert('Correo o contraseña incorrectos');
    }
}

async function register() {
    const nombre = document.getElementById('label-nombre').value;
    const apellido = document.getElementById('label-apellido').value;
    const correo = document.getElementById('label-correo').value;
    const passwordHash = document.getElementById('label-contraseña').value;

    const response = await fetch(`${API}/api/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nombre, apellido, correo, passwordHash })
    });

    if (response.ok) {
        alert('Usuario registrado exitosamente');
        window.location.href = 'login.html';
    } else {
        alert('Error al registrar');
    }
}
async function editarPerfil() {
    const token = localStorage.getItem('token');
    const nombre = document.getElementById('label-nombre').value;
    const correo = document.getElementById('label-correo').value;
    const nuevaContrasena = document.getElementById('label-contraseña').value;

    const response = await fetch(`${API}/api/me/editar`, {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ nombre, correo, nuevaContrasena })
    });

    if (response.ok) {
        alert('Perfil actualizado');
        window.location.href = 'dashboard.html';
    } else {
        alert('Error al actualizar');
    }
}