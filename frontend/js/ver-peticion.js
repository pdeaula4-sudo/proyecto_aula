function switchTab(tab, btn) {
    document.querySelectorAll('.auth-tab-content').forEach(function(el) { el.classList.remove('active'); });
    document.querySelectorAll('.auth-tab').forEach(function(el) { el.classList.remove('active'); });
    document.getElementById(tab).classList.add('active');
    btn.classList.add('active');
}

function toggleUserDropdown() {
    document.querySelector('.user-dropdown').classList.toggle('active');
}

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(false)) return;
    loadUserInitial();

    var rol = localStorage.getItem('userRole');
    if (rol === 'ADMIN') {
        document.getElementById('navInicio').href = 'peticiones-admin.html';
        document.getElementById('navInicio').textContent = 'Peticiones';
        document.getElementById('navBrand').href = 'dashboard-admin.html';
    }

    var params = new URLSearchParams(window.location.search);
    var id = params.get('id');
    if (!id) {
        showMsg('alertBox', 'No se especificó una petición.', 'danger');
        return;
    }

    cargarPeticion(id);
    cargarHistorial(id);

    document.addEventListener('click', function(e) {
        var menu = document.querySelector('.user-menu');
        if (menu && !menu.contains(e.target)) {
            var dd = document.querySelector('.user-dropdown');
            if (dd) dd.classList.remove('active');
        }
    });
});

async function cargarPeticion(id) {
    try {
        var p = await apiFetch('/api/peticiones/' + id);
        document.getElementById('subtitulo').textContent = 'Petición ' + shortId(p.id);
        document.getElementById('campoId').textContent = shortId(p.id);

        var tipo = p.tipoPeticion ? (p.tipoPeticion.charAt(0) + p.tipoPeticion.slice(1).toLowerCase()) : '--';
        document.getElementById('campoTipo').textContent = tipo;
        document.getElementById('campoFecha').textContent = formatDate(p.fechaCreacion);
        document.getElementById('campoSolicitante').textContent = p.miembro ? p.miembro.nombre : '--';
        document.getElementById('campoDescripcion').textContent = p.descripcion || '--';
        document.getElementById('badgeEstado').innerHTML = estadoBadge(p.estado);
        document.getElementById('campoEstado').innerHTML = estadoBadge(p.estado);

        if (p.responsable) {
            document.getElementById('contenedorResponsable').style.display = '';
            document.getElementById('campoResponsable').textContent = p.responsable.nombre;
        }

        renderRespuestas(p.respuestas || []);
        renderEvidencias(p.evidencias || []);
    } catch (err) {
        showMsg('alertBox', 'Error al cargar la petición: ' + err.message, 'danger');
    }
}

async function cargarHistorial(id) {
    try {
        var historial = await apiFetch('/api/peticiones/' + id + '/historial');
        renderHistorial(historial || []);
    } catch (err) {
        document.getElementById('timelineContenido').innerHTML = '<p style="color:var(--gray-500);">No se pudo cargar el historial.</p>';
    }
}

function renderHistorial(historial) {
    var contenedor = document.getElementById('timelineContenido');
    if (historial.length === 0) {
        contenedor.innerHTML = '<p style="color:var(--gray-500);">Sin cambios registrados aún.</p>';
        return;
    }
    var html = '<div class="petition-status-timeline">';
    historial.forEach(function(h) {
        var estadoLabel = h.estado === 'PENDIENTE' ? 'Pendiente' : h.estado === 'ASIGNADA' ? 'Asignada' : 'Resuelta';
        html += (
            '<div class="timeline-step active">' +
                '<div class="timeline-dot"><i class="fas fa-check" style="font-size:0.75rem;"></i></div>' +
                '<div class="timeline-label">' + estadoLabel + '</div>' +
                '<div class="text-sm text-muted">' + formatDateTime(h.fecha) + '</div>' +
                (h.responsable ? '<div class="text-sm text-muted">Por: ' + h.responsable.nombre + '</div>' : '') +
                (h.observacion ? '<div style="margin-top:5px; font-size:0.875rem; color:var(--gray-600);">' + h.observacion + '</div>' : '') +
            '</div>'
        );
    });
    html += '</div>';
    contenedor.innerHTML = html;
}

function renderRespuestas(respuestas) {
    var contenedor = document.getElementById('respuestasContenido');
    if (respuestas.length === 0) {
        contenedor.innerHTML = '<p style="color:var(--gray-500);">Aún no hay respuestas para esta petición.</p>';
        return;
    }
    var html = '';
    respuestas.forEach(function(r) {
        var iniciales = r.miembroNombre ? r.miembroNombre.split(' ').map(function(n) { return n[0]; }).join('').substring(0, 2).toUpperCase() : 'AD';
        html += (
            '<div class="response-card">' +
                '<div class="response-header">' +
                    '<div class="response-author">' +
                        '<div class="response-avatar">' + iniciales + '</div>' +
                        '<div class="response-author-info">' +
                            '<div class="response-author-name">' + (r.miembroNombre || 'Administrador') + '</div>' +
                            '<div class="response-author-role">Administrador PQRS</div>' +
                        '</div>' +
                    '</div>' +
                    '<div class="response-date">' + formatDateTime(r.fecha) + '</div>' +
                '</div>' +
                '<div class="response-body"><p>' + r.texto + '</p></div>' +
            '</div>'
        );
    });
    contenedor.innerHTML = html;
}

function renderEvidencias(evidencias) {
    var contenedor = document.getElementById('evidenciaContenido');
    if (evidencias.length === 0) {
        contenedor.innerHTML = '<p style="color:var(--gray-500);">No hay archivos adjuntos.</p>';
        return;
    }
    var html = '<div class="attachment-list">';
    evidencias.forEach(function(ev) {
        var icono = ev.nombreArchivo && ev.nombreArchivo.match(/\.(jpg|jpeg|png)$/i) ? '🖼️' : ev.nombreArchivo && ev.nombreArchivo.match(/\.pdf$/i) ? '📄' : '📎';
        html += (
            '<div class="attachment-item">' +
                '<div class="attachment-icon">' + icono + '</div>' +
                '<div class="attachment-name">' +
                    (ev.url ? '<a href="http://localhost:8080' + ev.url + '" target="_blank">' + ev.nombreArchivo + '</a>' : ev.nombreArchivo) +
                '</div>' +
            '</div>'
        );
    });
    html += '</div>';
    contenedor.innerHTML = html;
}
