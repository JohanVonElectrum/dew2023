(() => {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");
    const dni = localStorage.getItem("dni");

    if (!token || !role || !dni) {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        localStorage.removeItem("dni");
        window.location.href = window.location.protocol + "//" + window.location.host + "/nol/login.html";
        return;
    }

    (role === "alu" ? getAlumno : getProfesor)(dni, token).then(data => {
        document.getElementById("username").innerText = data.nombre + " " + data.apellidos;
        document.getElementById("dni").innerText = data.dni;
    }).catch(error => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        localStorage.removeItem("dni");
        window.location.href = window.location.protocol + "//" + window.location.host + "/nol/login.html";
    });

    document.getElementById("data-of-title").innerText = role === "alu" ? "Datos del alumno" : "Datos del profesor";

    getAsignaturas(role, dni, token).then(data => {
        fillAsignaturas(role, data);
    });
})();

const fillAsignaturas = (role, asignaturas) => {
    const container = document.getElementById("asignaturas-container");
    role === "alu" ? fillAlumnoAsignaturas(container, asignaturas) : fillProfesorAsignaturas(container, asignaturas);
}

const fillAlumnoAsignaturas = (container, asignaturas) => {
    const table = document.createElement("table");
    table.className = "table table-bordered";

    const thead = document.createElement("thead");
    const tr = document.createElement("tr");
    const th1 = document.createElement("th");
    th1.scope = "col";
    th1.innerText = "ACRONIMO";
    const th2 = document.createElement("th");
    th2.scope = "col";
    th2.innerText = "ASIGNATURA";
    const th3 = document.createElement("th");
    th3.scope = "col";
    th3.innerText = "CALIFICACION";
    tr.appendChild(th1);
    tr.appendChild(th2);
    tr.appendChild(th3);
    thead.appendChild(tr);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");
    for (const asignatura of asignaturas) {
        const tr = document.createElement("tr");
        const th = document.createElement("th");
        th.scope = "row";
        th.innerText = asignatura.acronimo;
        const td1 = document.createElement("td");
        td1.innerText = asignatura.nombre;
        const td2 = document.createElement("td");
        const b = document.createElement("b");
        b.innerText = asignatura.nota === -1 ? "Sin calificar" : asignatura.nota;
        td2.appendChild(b);
        tr.appendChild(th);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tbody.appendChild(tr);
    }
    table.appendChild(tbody);
    container.appendChild(table);
}

const fillProfesorAsignaturas = (container, asignaturas) => {

}