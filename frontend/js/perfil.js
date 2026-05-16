function toggleUserDropdown() {
    document.querySelector('.user-dropdown').classList.toggle('active');
}

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(false)) return;
    loadUserInitial();

    var nombre = localStorage.getItem('userName') || '';
    var correo = localStorage.getItem('userEmail') || '';
    var rol = localStorage.getItem('userRole') || '';

    var inicial = nombre ? nombre.charAt(0).toUpperCase() : 'U';
    document.getElementById('avatarGrande').textContent = inicial;
    document.getElementById('nombreCompleto').textContent = nombre || '--';
    document.getElementById('correoLabel').textContent = correo;
    document.getElementById('rolLabel').textContent = rol === 'ADMIN' ? 'Administrador' : 'Residente';
    document.getElementById('campoNombre').value = nombre;
    document.getElementById('campoCorreo').value = correo;
    document.getElementById('campoRol').value = rol === 'ADMIN' ? 'Administrador' : 'Residente';

    if (rol === 'ADMIN') {
        document.getElementById('navInicio').href = 'dashboard-admin.html';
        document.getElementById('navLink').href = 'dashboard-admin.html';
        document.getElementById('navLink').textContent = 'Dashboard';
    }

    cargarStats(rol);

    document.addEventListener('click', function(e) {
        var menu = document.querySelector('.user-menu');
        if (menu && !menu.contains(e.target)) {
            var dd = document.querySelector('.user-dropdown');
            if (dd) dd.classList.remove('active');
        }
    });
});

async function cargarStats(rol) {
    try {
        var endpoint = rol === 'ADMIN' ? '/api/peticiones' : '/api/peticiones/mis-peticiones';
        var peticiones = await apiFetch(endpoint);
        document.getElementById('statTotal').textContent = peticiones.length;
        document.getElementById('statResueltas').textContent = peticiones.filter(function(p) { return p.estado === 'RESUELTA'; }).length;
        document.getElementById('statPendientes').textContent = peticiones.filter(function(p) { return p.estado === 'PENDIENTE'; }).length;
    } catch (err) {
        document.getElementById('statTotal').textContent = '--';
        document.getElementById('statResueltas').textContent = '--';
        document.getElementById('statPendientes').textContent = '--';
    }
}
