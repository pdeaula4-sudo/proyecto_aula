var peticionId = null;
var archivosSeleccionados = [];

function toggleUserDropdown() {
    document.querySelector('.user-dropdown').classList.toggle('active');
}

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(true)) return;
    loadUserInitial();

    var params = new URLSearchParams(window.location.search);
    peticionId = params.get('id');
    if (!peticionId) {
        showMsg('alertBox', 'No se especificó una petición.', 'danger');
        return;
    }

    cargarPeticion(peticionId);
    cargarMiembros();
    setupFileUpload();

    document.getElementById('responseForm').addEventListener('submit', enviarRespuesta);

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
        document.getElementById('campoEstado').innerHTML = estadoBadge(p.estado);
        document.getElementById('campoFecha').textContent = formatDate(p.fechaCreacion);
        document.getElementById('campoSolicitante').textContent = p.miembro ? p.miembro.nombre : '--';
        document.getElementById('campoDescripcion').textContent = p.descripcion || '--';
        if (p.responsable) {
            document.getElementById('responsable').value = p.responsable.id || '';
        }
    } catch (err) {
        showMsg('alertBox', 'Error al cargar la petición: ' + err.message, 'danger');
    }
}

async function cargarMiembros() {
    try {
        var miembros = await apiFetch('/api/miembros');
        var select = document.getElementById('responsable');
        miembros.forEach(function(m) {
            var opt = document.createElement('option');
            opt.value = m.id;
            opt.textContent = m.nombreMiembro + ' ' + m.apellidoMiembro + ' (' + m.rol + ')';
            select.appendChild(opt);
        });
    } catch (err) {
        console.warn('No se pudieron cargar los miembros:', err.message);
    }
}

function setupFileUpload() {
    var zona = document.getElementById('fileUpload');
    var input = document.getElementById('fileInput');
    zona.addEventListener('click', function() { input.click(); });
    zona.addEventListener('dragover', function(e) { e.preventDefault(); zona.classList.add('active'); });
    zona.addEventListener('dragleave', function() { zona.classList.remove('active'); });
    zona.addEventListener('drop', function(e) {
        e.preventDefault();
        zona.classList.remove('active');
        agregarArchivos(e.dataTransfer.files);
    });
    input.addEventListener('change', function(e) { agregarArchivos(e.target.files); });
}

function agregarArchivos(files) {
    Array.from(files).forEach(function(file) {
        archivosSeleccionados.push(file);
        var lista = document.getElementById('fileList');
        var item = document.createElement('div');
        item.className = 'file-item';
        item.innerHTML =
            '<div class="file-item-name">' +
                '<div class="file-item-icon">' + (file.type.includes('image') ? '🖼️' : file.type.includes('pdf') ? '📄' : '📎') + '</div>' +
                '<div class="file-item-info">' +
                    '<div class="file-item-filename">' + file.name + '</div>' +
                    '<div class="file-item-size">' + (file.size / 1024).toFixed(1) + ' KB</div>' +
                '</div>' +
            '</div>' +
            '<button type="button" class="file-item-remove" onclick="this.closest(\'.file-item\').remove()"><i class="fas fa-trash"></i></button>';
        lista.appendChild(item);
    });
}

async function enviarRespuesta(e) {
    e.preventDefault();
    hideMsg('alertBox');

    var texto = document.getElementById('respuesta').value.trim();
    var nuevoEstado = document.getElementById('nuevoEstado').value;
    var responsableId = document.getElementById('responsable').value;
    var observacion = document.getElementById('observacion').value.trim();

    if (!texto) { showMsg('alertBox', 'Escribe una respuesta para el ciudadano.', 'danger'); return; }
    if (!nuevoEstado) { showMsg('alertBox', 'Selecciona el nuevo estado de la petición.', 'danger'); return; }

    var btn = document.getElementById('btnEnviar');
    btn.disabled = true;
    btn.textContent = 'Enviando...';

    try {
        await apiFetch('/api/peticiones/' + peticionId + '/respuestas', {
            method: 'POST',
            body: JSON.stringify({ texto: texto })
        });

        var cuerpoEstado = { nuevoEstado: nuevoEstado };
        if (responsableId) cuerpoEstado.responsableId = responsableId;
        if (observacion) cuerpoEstado.observacion = observacion;

        await apiFetch('/api/peticiones/' + peticionId + '/estado', {
            method: 'PUT',
            body: JSON.stringify(cuerpoEstado)
        });

        for (var i = 0; i < archivosSeleccionados.length; i++) {
            try { await apiUpload(peticionId, archivosSeleccionados[i]); }
            catch (errUpload) { console.warn('No se pudo subir:', archivosSeleccionados[i].name); }
        }

        showMsg('alertBox', 'Respuesta enviada correctamente. Redirigiendo...', 'success');
        setTimeout(function() { window.location.href = 'peticiones-admin.html'; }, 1500);

    } catch (err) {
        showMsg('alertBox', err.message || 'Error al enviar la respuesta.', 'danger');
        btn.disabled = false;
        btn.innerHTML = '<i class="fas fa-paper-plane"></i> Enviar Respuesta';
    }
}
