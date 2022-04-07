let admin = {
    userName: "egor",
    password: "egor"
}

async function auth(url) {
    return await fetch(url, {
        method: 'POST',

        headers: {
            Accept: 'application/json',
            'Content-Type': "application/json;charset=utf-8",
        },

        AccessControlAllowOrigin: 'http://localhost:9090/auth/login',

        body: JSON.stringify(admin)
    })
}

let url = 'http://localhost:9090/auth/login'
auth(url)
    .then(response => response.json())
    .then(data => {
        alert(JSON.stringify(data))
    })