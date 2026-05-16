var archivosSeleccionados = [];

document.addEventListener('DOMContentLoaded', function() {
    if (!requireAuth(false)) return;
    loadUserInitial();
    setupFileUpload();
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
        if (file.size > 10 * 1024 * 1024) {
            showMsg('alertBox', 'El archivo "' + file.name + '" supera 10MB.', 'danger');
            return;
        }
        archivosSeleccionados.push(file);
        var lista = document.getElementById('fileList');
        var item = document.createElement('div');
        item.className = 'file-item';
        item.dataset.nombre = file.name;
        item.innerHTML =
            '<div class="file-item-name">' +
                '<div class="file-item-icon">' + iconoArchivo(file.type) + '</div>' +
                '<div class="file-item-info">' +
                    '<div class="file-item-filename">' + file.name + '</div>' +
                    '<div class="file-item-size">' + (file.size / 1024).toFixed(1) + ' KB</div>' +
                '</div>' +
            '</div>' +
            '<button type="button" class="file-item-remove" onclick="quitarArchivo(this, \'' + file.name + '\')"><i class="fas fa-trash"></i></button>';
        lista.appendChild(item);
    });
}

function quitarArchivo(btn, nombre) {
    archivosSeleccionados = archivosSeleccionados.filter(function(f) { return f.name !== nombre; });
    btn.closest('.file-item').remove();
}

function iconoArchivo(tipo) {
    if (tipo.includes('pdf')) return '📄';
    if (tipo.includes('image')) return '🖼️';
    if (tipo.includes('word')) return '📝';
    return '📎';
}

async function enviarPeticion() {
    hideMsg('alertBox');
    var tipo = document.getElementById('petitionType').value;
    var desc = document.getElementById('description').value.trim();

    if (!tipo) { showMsg('alertBox', 'Selecciona el tipo de petición.', 'danger'); return; }
    if (!desc) { showMsg('alertBox', 'Escribe la descripción de tu petición.', 'danger'); return; }

    var btn = document.getElementById('btnEnviar');
    btn.disabled = true;
    btn.textContent = 'Enviando...';

    try {
        var peticion = await apiFetch('/api/peticiones', {
            method: 'POST',
            body: JSON.stringify({ descripcion: desc, tipoPeticion: tipo })
        });

        for (var i = 0; i < archivosSeleccionados.length; i++) {
            try {
                await apiUpload(peticion.id, archivosSeleccionados[i]);
            } catch (errUpload) {
                console.warn('No se pudo subir:', archivosSeleccionados[i].name);
            }
        }

        showMsg('alertBox', 'Petición enviada correctamente. Redirigiendo...', 'success');
        setTimeout(function() { window.location.href = 'dashboard-ciudadano.html'; }, 1500);

    } catch (err) {
        showMsg('alertBox', err.message || 'Error al enviar la petición.', 'danger');
        btn.disabled = false;
        btn.innerHTML = '<i class="fas fa-paper-plane"></i> Enviar Petición';
    }
}

function cancelar() {
    if (confirm('¿Cancelar? Se perderán los cambios.')) {
        window.location.href = 'dashboard-ciudadano.html';
    }
}
