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
        .then(async response => {
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Server responded with ${response.status}: ${errorText}`);
            }
            return response.json(); // безопасно, так как response.ok === true
        })
        .then(data => {
            if (data.token) {
                localStorage.setItem('token', data.token);
                document.getElementById('user-info').innerText = 'Authenticated! Token saved.';
            } else {
                document.getElementById('user-info').innerText = 'Authentication failed: ' + (data.error || 'Unknown error');
            }
        })
        .catch(error => {
            document.getElementById('user-info').innerText = 'Error: ' + error.message;
            console.error('Ошибка аутентификации:', error);
        });
}
