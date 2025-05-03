function authenticate() {
    const initData = window.Telegram.WebApp.initData;
    if (!initData) {
        document.getElementById('user-info').innerText = 'Error: initData not available';
        return;
    }

    fetch('/api/auth/telegram', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ initData }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem('token', data.token);
                document.getElementById('user-info').innerText = 'Authenticated! Token saved.';
                // Дополнительная логика, например, загрузка данных
            } else {
                document.getElementById('user-info').innerText = 'Authentication failed: ' + (data.error || 'Unknown error');
            }
        })
        .catch(error => {
            document.getElementById('user-info').innerText = 'Error: ' + error.message;
        });
}

// Инициализация
window.Telegram.WebApp.ready();
