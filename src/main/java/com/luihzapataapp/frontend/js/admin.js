const API = 'http://localhost:8080';

async function cargarUsuarios() {
    const token = localStorage.getItem('token');

    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    const response = await fetch(`${API}/api/admin/usuarios`, {
        headers: { 'Authorization': 'Bearer ' + token }
    });

    const usuarios = await response.json();
    const tbody = document.getElementById('tabla-usuarios');

    tbody.innerHTML = '';

    usuarios.forEach(u => {
    tbody.innerHTML += `
        <tr>
            <td>${u.nombre}</td>
            <td>${u.correo}</td>
            <td>${u.rol}</td>
            <td>
                <button onclick="editarUsuario(${u.idUsuario})" class="btn btn-sm btn-warning">✏️</button>
                <button onclick="eliminarUsuario(${u.idUsuario})" class="btn btn-sm btn-danger">🗑️</button>
            </td>
        </tr>
    `;
});
}

async function eliminarUsuario(id) {
    if (!confirm('¿Estás seguro de eliminar este usuario?')) return;

    const token = localStorage.getItem('token');
    const idAdmin = localStorage.getItem('idAdmin');

    const response = await fetch(`${API}/api/admin/usuarios/${id}?idAdmin=${idAdmin}`, {
        method: 'DELETE',
        headers: { 'Authorization': 'Bearer ' + token }
    });

    if (response.ok) {
        alert('Usuario eliminado');
        cargarUsuarios();
    } else {
        alert('Error al eliminar');
    }
}

async function editarUsuario(id) {
    const nombre = prompt('Nuevo nombre:');
    const correo = prompt('Nuevo correo:');
    const token = localStorage.getItem('token');

    const response = await fetch(`${API}/api/admin/usuarios/${id}`, {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ nombre, correo })
    });

    if (response.ok) {
        alert('Usuario actualizado');
        cargarUsuarios();
    } else {
        alert('Error al actualizar');
    }
}

document.getElementById('buscador').addEventListener('input', function() {
    const texto = this.value.toLowerCase();
    const filas = document.querySelectorAll('#tabla-usuarios tr');
    
    filas.forEach(fila => {
        const contenido = fila.textContent.toLowerCase();
        fila.style.display = contenido.includes(texto) ? '' : 'none';
    });
});
cargarUsuarios();