var todasLasPeticiones = [];

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(false)) return;
    var rol = localStorage.getItem('userRole');
    if (rol === 'ADMIN') { window.location.href = 'dashboard-admin.html'; return; }
    loadUserInitial();
    var nombre = localStorage.getItem('userName') || '';
    if (nombre) document.getElementById('saludo').textContent = 'Bienvenido, ' + nombre;
    cargarPeticiones();
    document.getElementById('searchInput').addEventListener('input', aplicarFiltros);
    document.getElementById('statusFilter').addEventListener('change', aplicarFiltros);
    document.getElementById('typeFilter').addEventListener('change', aplicarFiltros);
    document.addEventListener('click', function(e) {
        var menu = document.querySelector('.user-menu');
        if (menu && !menu.contains(e.target)) {
            var dd = document.querySelector('.user-dropdown');
            if (dd) dd.classList.remove('active');
        }
    });
});

function toggleUserDropdown() {
    document.querySelector('.user-dropdown').classList.toggle('active');
}

async function cargarPeticiones() {
    document.getElementById('petitionsList').innerHTML = '<p style="text-align:center; color:var(--gray-500); padding:40px;">Cargando peticiones...</p>';
    try {
        todasLasPeticiones = await apiFetch('/api/peticiones/mis-peticiones');
        actualizarStats(todasLasPeticiones);
        renderLista(todasLasPeticiones);
    } catch (err) {
        showMsg('alertBox', 'No se pudieron cargar las peticiones: ' + err.message, 'danger');
        document.getElementById('petitionsList').innerHTML = '';
    }
}

function actualizarStats(lista) {
    document.getElementById('statTotal').textContent = lista.length;
    document.getElementById('statResueltas').textContent = lista.filter(function(p) { return p.estado === 'RESUELTA'; }).length;
    document.getElementById('statAsignadas').textContent = lista.filter(function(p) { return p.estado === 'ASIGNADA'; }).length;
    document.getElementById('statPendientes').textContent = lista.filter(function(p) { return p.estado === 'PENDIENTE'; }).length;
}

function aplicarFiltros() {
    var busqueda = document.getElementById('searchInput').value.toLowerCase();
    var estado = document.getElementById('statusFilter').value;
    var tipo = document.getElementById('typeFilter').value;
    var filtradas = todasLasPeticiones.filter(function(p) {
        var coincideTexto = !busqueda || (p.descripcion && p.descripcion.toLowerCase().includes(busqueda));
        var coincideEstado = !estado || p.estado === estado;
        var coincideTipo = !tipo || p.tipoPeticion === tipo;
        return coincideTexto && coincideEstado && coincideTipo;
    });
    renderLista(filtradas);
}

function limpiarFiltros() {
    document.getElementById('searchInput').value = '';
    document.getElementById('statusFilter').value = '';
    document.getElementById('typeFilter').value = '';
    renderLista(todasLasPeticiones);
}

function renderLista(lista) {
    var contenedor = document.getElementById('petitionsList');
    if (lista.length === 0) {
        contenedor.innerHTML = '<div class="card"><div class="card-body" style="text-align:center; padding:60px; color:var(--gray-500);"><i class="fas fa-inbox" style="font-size:3rem; margin-bottom:20px; display:block;"></i>No se encontraron peticiones.</div></div>';
        return;
    }
    contenedor.innerHTML = lista.map(function(p) { return buildCard(p); }).join('');
}

function buildCard(p) {
    var claseEstado = estadoCardClass(p.estado);
    var desc = p.descripcion || '';
    var descCorta = desc.length > 120 ? desc.substring(0, 120) + '...' : desc;
    var fecha = formatDate(p.fechaCreacion);
    var tipo = p.tipoPeticion ? (p.tipoPeticion.charAt(0) + p.tipoPeticion.slice(1).toLowerCase()) : '--';
    return (
        '<div class="petition-card ' + claseEstado + '" style="margin-bottom:20px;">' +
            '<div class="petition-header">' +
                '<div>' +
                    '<div class="petition-title">' + tipo + '</div>' +
                    '<div class="petition-id">' + shortId(p.id) + '</div>' +
                '</div>' +
                estadoBadge(p.estado) +
            '</div>' +
            '<div class="petition-content">' +
                '<p class="petition-description">' + descCorta + '</p>' +
                '<div class="petition-meta">' +
                    '<div class="petition-meta-item"><span class="petition-meta-label">Tipo</span><span class="petition-meta-value">' + tipo + '</span></div>' +
                    '<div class="petition-meta-item"><span class="petition-meta-label">Fecha</span><span class="petition-meta-value">' + fecha + '</span></div>' +
                    (p.responsable ? '<div class="petition-meta-item"><span class="petition-meta-label">Responsable</span><span class="petition-meta-value">' + p.responsable.nombre + '</span></div>' : '') +
                '</div>' +
            '</div>' +
            '<div class="petition-footer">' +
                '<div style="font-size:0.875rem; color:var(--gray-500);">Creada el ' + fecha + '</div>' +
                '<div class="petition-actions">' +
                    '<button class="btn btn-sm btn-primary" onclick="window.location.href=\'ver-peticion.html?id=' + p.id + '\'">' +
                        '<i class="fas fa-eye"></i> Ver' +
                    '</button>' +
                '</div>' +
            '</div>' +
        '</div>'
    );
}
