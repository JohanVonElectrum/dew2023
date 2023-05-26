const onLoginSubmit = async (event,) => {
    event.preventDefault();

    const roleButtons = document.querySelectorAll('input[type=radio][name="role-group"]');

    // Find the currently selected radio button
    let selectedRole;
    for (const radioButton of roleButtons) {
        if (radioButton.checked) {
            selectedRole = radioButton.id;
            break;
        }
    }

    const dni = document.getElementById("dni").value;
    const password = document.getElementById("password").value;

    login(dni, password, selectedRole).then(session => {
        window.location.href = "index.html";
    }).catch(error => {
        console.log(error);
    });
}