const API_BASE = 'http://localhost:8080';

async function apiFetch(endpoint, options = {}) {
    const token = localStorage.getItem('token');
    const headers = { 'Content-Type': 'application/json' };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    if (options.headers) Object.assign(headers, options.headers);

    const res = await fetch(API_BASE + endpoint, { ...options, headers });

    if (res.status === 401) {
        localStorage.clear();
        window.location.href = 'index.html';
        throw new Error('Sesión expirada');
    }

    if (res.status === 204) return null;

    const data = await res.json().catch(() => null);

    if (!res.ok) {
        throw new Error((data && (data.error || data.message)) || 'Error ' + res.status);
    }

    return data;
}

async function apiUpload(peticionId, file) {
    const token = localStorage.getItem('token');
    const formData = new FormData();
    formData.append('file', file);
    formData.append('peticionId', peticionId);

    const res = await fetch(API_BASE + '/api/upload/evidencia', {
        method: 'POST',
        headers: { 'Authorization': 'Bearer ' + token },
        body: formData
    });

    if (!res.ok) throw new Error('Error al subir el archivo: ' + file.name);
    return res.json();
}

function formatDate(d) {
    if (!d) return '--';
    return new Date(d).toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit', year: 'numeric' });
}

function formatDateTime(d) {
    if (!d) return '--';
    return new Date(d).toLocaleString('es-CO', {
        day: '2-digit', month: '2-digit', year: 'numeric',
        hour: '2-digit', minute: '2-digit'
    });
}

function estadoBadge(estado) {
    const cfg = {
        PENDIENTE: { cls: 'badge-warning',  icon: 'clock',        label: 'Pendiente'  },
        ASIGNADA:  { cls: 'badge-primary',  icon: 'user-check',   label: 'Asignada'   },
        RESUELTA:  { cls: 'badge-success',  icon: 'check-circle', label: 'Resuelta'   }
    };
    const c = cfg[estado] || { cls: 'badge-gray', icon: 'question', label: estado };
    return `<span class="badge ${c.cls}"><i class="fas fa-${c.icon}"></i> ${c.label}</span>`;
}

function tipoBadge(tipo) {
    const cls = {
        PETICION:   'badge-primary',
        QUEJA:      'badge-warning',
        RECLAMO:    'badge-danger',
        SUGERENCIA: 'badge-gray'
    };
    const label = tipo.charAt(0) + tipo.slice(1).toLowerCase();
    return `<span class="badge ${cls[tipo] || 'badge-gray'}">${label}</span>`;
}

function estadoCardClass(estado) {
    const map = { ASIGNADA: 'estado-en-proceso', RESUELTA: 'estado-resuelto' };
    return map[estado] || '';
}

function plazoDias(tipo) {
    return (tipo === 'PETICION' || tipo === 'SUGERENCIA') ? 30 : 15;
}

function shortId(id) {
    return id ? '#' + id.substring(0, 8).toUpperCase() : '--';
}
