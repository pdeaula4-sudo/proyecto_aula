function toggleUserDropdown() {
    document.querySelector('.user-dropdown').classList.toggle('active');
}

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(true)) return;
    loadUserInitial();
    cargarDatos();
    document.addEventListener('click', function(e) {
        var menu = document.querySelector('.user-menu');
        if (menu && !menu.contains(e.target)) {
            var dd = document.querySelector('.user-dropdown');
            if (dd) dd.classList.remove('active');
        }
    });
});

async function cargarDatos() {
    try {
        var peticiones = await apiFetch('/api/peticiones');

        var total = peticiones.length;
        var resueltas = peticiones.filter(function(p) { return p.estado === 'RESUELTA'; }).length;
        var asignadas = peticiones.filter(function(p) { return p.estado === 'ASIGNADA'; }).length;
        var pendientes = peticiones.filter(function(p) { return p.estado === 'PENDIENTE'; }).length;

        document.getElementById('statTotal').textContent = total;
        document.getElementById('statResueltas').textContent = resueltas;
        document.getElementById('statAsignadas').textContent = asignadas;
        document.getElementById('statPendientes').textContent = pendientes;

        renderResumenEstados(total, pendientes, asignadas, resueltas);
        renderResumenTipos(peticiones);
        renderTablaRecientes(peticiones.slice(0, 5));

    } catch (err) {
        showMsg('alertBox', 'Error al cargar los datos: ' + err.message, 'danger');
    }
}

function barraProgreso(valor, total, color) {
    var pct = total > 0 ? Math.round((valor / total) * 100) : 0;
    return (
        '<div style="display:flex; justify-content:space-between; margin-bottom:4px;">' +
            '<span>' + pct + '%</span><span>' + valor + '</span>' +
        '</div>' +
        '<div style="background:var(--gray-200); border-radius:4px; height:8px; margin-bottom:16px;">' +
            '<div style="background:' + color + '; width:' + pct + '%; height:8px; border-radius:4px;"></div>' +
        '</div>'
    );
}

function renderResumenEstados(total, pendientes, asignadas, resueltas) {
    document.getElementById('resumenEstados').innerHTML = (
        '<div style="font-weight:600; color:var(--gray-700); margin-bottom:6px;">Pendiente</div>' +
        barraProgreso(pendientes, total, 'var(--warning)') +
        '<div style="font-weight:600; color:var(--gray-700); margin-bottom:6px;">Asignada</div>' +
        barraProgreso(asignadas, total, 'var(--primary)') +
        '<div style="font-weight:600; color:var(--gray-700); margin-bottom:6px;">Resuelta</div>' +
        barraProgreso(resueltas, total, 'var(--success)')
    );
}

function renderResumenTipos(peticiones) {
    var tipos = { PETICION: 0, QUEJA: 0, RECLAMO: 0, SUGERENCIA: 0 };
    peticiones.forEach(function(p) { if (tipos[p.tipoPeticion] !== undefined) tipos[p.tipoPeticion]++; });
    var colores = { PETICION: 'var(--primary)', QUEJA: 'var(--warning)', RECLAMO: 'var(--danger)', SUGERENCIA: 'var(--success)' };
    var labels = { PETICION: 'Petición', QUEJA: 'Queja', RECLAMO: 'Reclamo', SUGERENCIA: 'Sugerencia' };
    var html = '';
    Object.keys(tipos).forEach(function(t) {
        html += (
            '<div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:15px;">' +
                '<span style="color:var(--gray-600); font-weight:500;">' + labels[t] + '</span>' +
                '<div style="display:flex; gap:10px; align-items:center;">' +
                    '<div style="width:20px; height:20px; background:' + colores[t] + '; border-radius:3px;"></div>' +
                    '<span style="font-weight:700;">' + tipos[t] + '</span>' +
                '</div>' +
            '</div>'
        );
    });
    document.getElementById('resumenTipos').innerHTML = html;
}

function renderTablaRecientes(lista) {
    if (lista.length === 0) {
        document.getElementById('tablaRecientes').innerHTML = '<tr><td colspan="7" style="padding:30px; text-align:center; color:var(--gray-500);">No hay peticiones.</td></tr>';
        return;
    }
    var filas = lista.map(function(p) {
        var desc = p.descripcion || '';
        var descCorta = desc.length > 40 ? desc.substring(0, 40) + '...' : desc;
        return (
            '<tr>' +
                '<td style="padding:15px 20px;"><strong>' + shortId(p.id) + '</strong></td>' +
                '<td style="padding:15px 20px; max-width:200px;">' + descCorta + '</td>' +
                '<td style="padding:15px 20px;">' + tipoBadge(p.tipoPeticion) + '</td>' +
                '<td style="padding:15px 20px;">' + (p.miembro ? p.miembro.nombre : '--') + '</td>' +
                '<td style="padding:15px 20px;">' + estadoBadge(p.estado) + '</td>' +
                '<td style="padding:15px 20px;">' + formatDate(p.fechaCreacion) + '</td>' +
                '<td style="padding:15px 20px;">' +
                    (p.estado !== 'RESUELTA'
                        ? '<button class="btn btn-sm btn-primary" onclick="window.location.href=\'responder-peticion.html?id=' + p.id + '\'" title="Responder"><i class="fas fa-reply"></i></button> '
                        : '') +
                    '<button class="btn btn-sm btn-secondary" onclick="window.location.href=\'ver-peticion.html?id=' + p.id + '\'" title="Ver"><i class="fas fa-eye"></i></button>' +
                '</td>' +
            '</tr>'
        );
    });
    document.getElementById('tablaRecientes').innerHTML = filas.join('');
}
