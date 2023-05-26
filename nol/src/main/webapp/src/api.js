const login = async (dni, password, role) => {
    const requestBody = new URLSearchParams();
    requestBody.append("dni", dni);
    requestBody.append("password", password);

    const baseUrl = window.location.protocol + "//" + window.location.host + "/nol/api/login";
    return fetch(baseUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: requestBody.toString()
    })
        .then(response => response.text())
        .then(token => {
            localStorage.setItem("token", token);
            localStorage.setItem("role", role);
            localStorage.setItem("dni", dni);
        });
}

const logout = async () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("dni");
    window.location.href = window.location.protocol + "//" + window.location.host + "/nol/login.html";
}

const getAlumno = async (dni, token) => {
    const baseUrl = window.location.protocol + "//" + window.location.host +
        "/nol/api/alumnos/" + dni + "?key=" + token;
    return fetch(baseUrl, {
        method: "GET"
    }).then(response => response.json())
}

const getProfesor = async (dni, token) => {
    const baseUrl = window.location.protocol + "//" + window.location.host +
        "/nol/api/profesores/" + dni + "?key=" + token;
    return fetch(baseUrl, {
        method: "GET"
    }).then(response => response.json())
}

const getAsignaturas = async (role, dni, token) => {
    const baseUrl = window.location.protocol + "//" + window.location.host +
        "/nol/api/" + (role === "alu" ? "alumnos" : "profesores") + "/" + dni + "/asignaturas?key=" + token;
    return fetch(baseUrl, {
        method: "GET"
    }).then(response => response.json())
}