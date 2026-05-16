function switchTab(tab, btn) {
    document.querySelectorAll('.auth-tab-content').forEach(function(el) { el.classList.remove('active'); });
    document.querySelectorAll('.auth-tab').forEach(function(el) { el.classList.remove('active'); });
    document.getElementById(tab).classList.add('active');
    btn.classList.add('active');
    hideMsg();
}

function showMsg(msg, type) {
    var box = document.getElementById('alertBox');
    box.style.display = '';
    var icon = type === 'success' ? 'check-circle' : 'exclamation-circle';
    box.innerHTML = '<div class="alert alert-' + (type || 'danger') + '"><i class="fas fa-' + icon + '"></i> ' + msg + '</div>';
}

function hideMsg() {
    var box = document.getElementById('alertBox');
    box.style.display = 'none';
    box.innerHTML = '';
}

function guardarSesion(data) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('userEmail', data.correo);
    localStorage.setItem('userRole', data.rol);
    localStorage.setItem('userName', data.nombre);
    localStorage.setItem('miembroId', data.miembroId);
}

function redirigirPorRol(rol) {
    if (rol === 'ADMIN') {
        window.location.href = 'dashboard-admin.html';
    } else {
        window.location.href = 'dashboard-ciudadano.html';
    }
}

document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    hideMsg();
    var btn = document.getElementById('btnLogin');
    btn.disabled = true;
    btn.textContent = 'Ingresando...';

    try {
        var data = await apiFetch('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify({
                correo: document.getElementById('correo').value,
                password: document.getElementById('password').value
            })
        });
        guardarSesion(data);
        redirigirPorRol(data.rol);
    } catch (err) {
        showMsg(err.message || 'Correo o contraseña incorrectos', 'danger');
        btn.disabled = false;
        btn.innerHTML = '<i class="fas fa-arrow-right"></i> Ingresar';
    }
});

document.getElementById('registerForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    hideMsg();

    var pass = document.getElementById('regPassword').value;
    var confirm = document.getElementById('confirmPassword').value;
    if (pass !== confirm) {
        showMsg('Las contraseñas no coinciden.', 'danger');
        return;
    }

    var btn = document.getElementById('btnRegister');
    btn.disabled = true;
    btn.textContent = 'Registrando...';

    try {
        var data = await apiFetch('/api/auth/registro', {
            method: 'POST',
            body: JSON.stringify({
                nombreMiembro: document.getElementById('nombre').value,
                apellidoMiembro: document.getElementById('apellido').value,
                telMiembro: document.getElementById('telefono').value,
                correoMiembro: document.getElementById('regCorreo').value,
                password: pass
            })
        });
        guardarSesion(data);
        redirigirPorRol(data.rol);
    } catch (err) {
        showMsg(err.message || 'Error al registrar. Verifica los datos.', 'danger');
        btn.disabled = false;
        btn.innerHTML = '<i class="fas fa-user-plus"></i> Registrarse';
    }
});
