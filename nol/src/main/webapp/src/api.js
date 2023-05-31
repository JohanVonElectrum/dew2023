const login = async (dni, password) => {
    const requestBody = new URLSearchParams();
    requestBody.append("dni", dni);
    requestBody.append("password", password);

    const baseUrl = window.location.protocol + "//" + window.location.host + "/" +
        window.location.pathname.split("/")[1] + "/api/login";
    return fetch(baseUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: requestBody.toString()
    })
        .then(response=> response.status === 200);
}

const logout = () => {
    localStorage.removeItem("role");
    localStorage.removeItem("dni");
    window.location.href = window.location.protocol + "//" + window.location.host + "/" +
        window.location.pathname.split("/")[1] + "/login.html";
}

const getAlumno = async (dni) => {
    const baseUrl = window.location.protocol + "//" + window.location.host +
        "/" + window.location.pathname.split("/")[1] + "/api/alumnos/" + dni;
    return fetch(baseUrl, {
        method: "GET"
    }).then(response => response.json())
}

const getProfesor = async (dni) => {
    const baseUrl = window.location.protocol + "//" + window.location.host +
        "/" + window.location.pathname.split("/")[1] + "/api/profesores/" + dni;
    return fetch(baseUrl, {
        method: "GET"
    }).then(response => response.json())
}

const getAsignaturas = async (role, dni) => {
    const baseUrl = window.location.protocol + "//" + window.location.host +
        "/" + window.location.pathname.split("/")[1] + "/api/" +
        (role === "alu" ? "alumnos" : "profesores") + "/" + dni + "/asignaturas";
    return fetch(baseUrl, {
        method: "GET"
    }).then(response => response.json())
}