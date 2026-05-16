var todasLasPeticiones = [];

function toggleUserDropdown() {
    document.querySelector('.user-dropdown').classList.toggle('active');
}

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(true)) return;
    loadUserInitial();
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

async function cargarPeticiones() {
    try {
        todasLasPeticiones = await apiFetch('/api/peticiones');
        renderTabla(todasLasPeticiones);
    } catch (err) {
        showMsg('alertBox', 'Error al cargar peticiones: ' + err.message, 'danger');
        document.getElementById('tablaCuerpo').innerHTML = '<tr><td colspan="7" style="padding:30px; text-align:center; color:var(--danger);">Error al cargar.</td></tr>';
    }
}

function aplicarFiltros() {
    var busqueda = document.getElementById('searchInput').value.toLowerCase();
    var estado = document.getElementById('statusFilter').value;
    var tipo = document.getElementById('typeFilter').value;
    var filtradas = todasLasPeticiones.filter(function(p) {
        var coincideTexto = !busqueda || (p.descripcion && p.descripcion.toLowerCase().includes(busqueda)) || (p.miembro && p.miembro.nombre && p.miembro.nombre.toLowerCase().includes(busqueda));
        var coincideEstado = !estado || p.estado === estado;
        var coincideTipo = !tipo || p.tipoPeticion === tipo;
        return coincideTexto && coincideEstado && coincideTipo;
    });
    renderTabla(filtradas);
}

function limpiarFiltros() {
    document.getElementById('searchInput').value = '';
    document.getElementById('statusFilter').value = '';
    document.getElementById('typeFilter').value = '';
    renderTabla(todasLasPeticiones);
}

function renderTabla(lista) {
    if (lista.length === 0) {
        document.getElementById('tablaCuerpo').innerHTML = '<tr><td colspan="7" style="padding:40px; text-align:center; color:var(--gray-500);">No se encontraron peticiones.</td></tr>';
        return;
    }
    var filas = lista.map(function(p) {
        var desc = p.descripcion || '';
        var descCorta = desc.length > 50 ? desc.substring(0, 50) + '...' : desc;
        var puedeResponder = p.estado !== 'RESUELTA';
        return (
            '<tr>' +
                '<td style="padding:15px 20px;"><strong>' + shortId(p.id) + '</strong></td>' +
                '<td style="padding:15px 20px;">' + descCorta + '</td>' +
                '<td style="padding:15px 20px;">' + tipoBadge(p.tipoPeticion) + '</td>' +
                '<td style="padding:15px 20px;">' + (p.miembro ? p.miembro.nombre : '--') + '</td>' +
                '<td style="padding:15px 20px;">' + estadoBadge(p.estado) + '</td>' +
                '<td style="padding:15px 20px;">' + formatDate(p.fechaCreacion) + '</td>' +
                '<td style="padding:15px 20px;">' +
                    (puedeResponder
                        ? '<button class="btn btn-sm btn-primary" onclick="window.location.href=\'responder-peticion.html?id=' + p.id + '\'" title="Responder"><i class="fas fa-reply"></i></button> '
                        : '') +
                    '<button class="btn btn-sm btn-secondary" onclick="window.location.href=\'ver-peticion.html?id=' + p.id + '\'" title="Ver"><i class="fas fa-eye"></i></button>' +
                '</td>' +
            '</tr>'
        );
    });
    document.getElementById('tablaCuerpo').innerHTML = filas.join('');
}
