
var editBottons = document.querySelectorAll(".editOnTable")
editBottons.forEach(botton => setOpenEditModal(botton))

var deleteBottons = document.querySelectorAll(".deleteOnTable")
deleteBottons.forEach(botton => setOpenDeleteModal(botton))

var varAddForm = document.querySelector("#addUserForm")
varAddForm.addEventListener('submit', addUser)

var varEditForm = document.querySelector("#editForm")
varEditForm.addEventListener('submit', editUser)

var varDeleteForm = document.querySelector("#deleteForm")
varDeleteForm.addEventListener('submit', deleteUser)

var varTabNew = document.querySelector("#hrefNewUser")

$('[data-toggle="tab"]').on('show.bs.tab', async function (e) {

    if ($(e.target)[0].innerText == "New User"){

        const requestRoles = new Request("http://localhost:8080/admin/allRoles", {
            method: 'get'
        })

        try {
            const responseRoles = await fetch(requestRoles)
            var roles = await responseRoles.json()
        } catch (error) {
            console.error(error)
        }

        document.querySelectorAll("#rolesNew option").forEach(option => option.remove())

        for (var role of roles){
            var newOption = document.createElement("option")
            newOption.value = role.role
            newOption.text = role.role.replace("ROLE_", "")
            newOption.selected = false
            document.querySelector("#rolesNew").appendChild(newOption)
        }
    }
})

async function getUser(event, id){

    const request = new Request("http://localhost:8080/admin/getUser/" + id, {
        method: 'get'
    })

    try {
        const response = await fetch(request)
        var user = await response.json()
    } catch (error) {
        console.error(error)
    }

    const requestRoles = new Request("http://localhost:8080/admin/allRoles", {
        method: 'get'
    })

    try {
        const responseRoles = await fetch(requestRoles)
        var roles = await responseRoles.json()
    } catch (error) {
        console.error(error)
    }

    window.editForm.reset()
    window.deleteForm.reset()

    document.querySelector("#id" + event).setAttribute('value', id)
    document.querySelector("#firstname" + event).setAttribute('value', user.firstName)
    document.querySelector("#lastname" + event).setAttribute('value', user.lastName)
    document.querySelector("#age" + event).setAttribute('value', user.age)
    document.querySelector("#email" + event).setAttribute('value', user.email)
    document.querySelector("#password" + event).setAttribute('value', user.password)

    document.querySelectorAll("#roles" + event +" option").forEach(option => option.remove())

    var rolesOfUser = [];
    for (var role of user.roles){
        rolesOfUser.push(role.role)
    }

    for (var role of roles){
        var newOption = document.createElement("option")
        newOption.value = role.role
        newOption.text = role.role.replace("ROLE_", "")
        newOption.selected = -1 != rolesOfUser.indexOf(role.role)
        document.querySelector("#roles" + event).appendChild(newOption)
    }
}

function setOpenDeleteModal(botton) {

    botton.addEventListener('click', async function () {

        const varID = botton.getAttribute("id").replace("deleteOnTable", "")

        getUser("Delete", varID)

        $('#deleteUserModal').modal('show')
    })
}

function setOpenEditModal(botton) {

    botton.addEventListener('click', async function () {

        const varID = botton.getAttribute("id").replace("editOnTable", "")

        await getUser("Edit", varID)

        $('#editUserModal').modal('show')
    })
}

async function addUser(event){
    event.preventDefault()

    var rolesValue = []
    var rolesText = []
    var options = window.addUserForm.roles.options

    for(i = 0; i < options.length; i++){
        if (options[i].selected){
            rolesValue.push(options[i].value)
            rolesText.push(options[i].text)
        }
    }

    const formData = {
        firstName : window.addUserForm.firstName.value,
        lastName : window.addUserForm.lastName.value,
        age : window.addUserForm.age.value,
        email : window.addUserForm.email.value,
        password : window.addUserForm.password.value,
        roles : rolesValue
    }

    const request = new Request("http://localhost:8080/admin/saveUser", {
        method : 'post',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        body : JSON.stringify(formData)
    })

    try {
        var response = await fetch(request)
        var user = await response.json()
    }catch (error){
        console.error(error)
    }

    var newRow = document.querySelector("#adminTableBody").insertRow()

    newRow.id = "rowid" + user.id

    var cellID = newRow.insertCell(0)
    var cellFirstName = newRow.insertCell(1)
    var cellLastName = newRow.insertCell(2)
    var cellAge = newRow.insertCell(3)
    var cellEmail = newRow.insertCell(4)
    var cellRoles = newRow.insertCell(5)
    var cellEditButtom = newRow.insertCell(6)
    var cellDeleteButtom = newRow.insertCell(7)

    var buttonEdit = document.createElement("Button")
    buttonEdit.setAttribute('id', "editOnTable" + user.id)
    buttonEdit.setAttribute('class', "btn btn-info editOnTable")
    buttonEdit.innerHTML="Edit"

    var buttonDelete = document.createElement("Button")
    buttonDelete.setAttribute('id', "deleteOnTable" + user.id)
    buttonDelete.setAttribute('class', "btn btn-danger")
    buttonDelete.innerHTML="Delete"

    setOpenEditModal(buttonEdit)
    setOpenDeleteModal(buttonDelete)

    cellID.innerHTML = user.id
    cellFirstName.innerHTML = user.firstName
    cellLastName.innerHTML = user.lastName
    cellAge.innerHTML = user.age
    cellEmail.innerHTML = user.email
    cellRoles.innerHTML = rolesText
    cellRoles.innerHTML = cellRoles.innerHTML.replaceAll(",", " ")
    cellEditButtom.appendChild(buttonEdit)
    cellDeleteButtom.appendChild(buttonDelete)

    // userTableBody

    if (rolesValue.indexOf("ROLE_USER") != -1) {

        var newRow = document.querySelector("#userTableBody").insertRow()

        newRow.id = "userRowid" + user.id

        var cellID = newRow.insertCell(0)
        var cellFirstName = newRow.insertCell(1)
        var cellLastName = newRow.insertCell(2)
        var cellAge = newRow.insertCell(3)
        var cellEmail = newRow.insertCell(4)
        var cellRoles = newRow.insertCell(5)

        cellID.innerHTML = user.id
        cellFirstName.innerHTML = user.firstName
        cellLastName.innerHTML = user.lastName
        cellAge.innerHTML = user.age
        cellEmail.innerHTML = user.email
        cellRoles.innerHTML = rolesText
        cellRoles.innerHTML = cellRoles.innerHTML.replaceAll(",", " ")
    }

    window.addUserForm.reset()
}

async function editUser(event){
    event.preventDefault()

    const id = window.editForm.id.value
    const firstName = window.editForm.firstName.value
    const lastName = window.editForm.lastName.value
    const age = window.editForm.age.value
    const email = window.editForm.email.value
    const password = window.editForm.password.value

    const varRow = document.querySelector("#rowid" + id)

    var rolesValue = []
    var rolesText = []
    var options = window.editForm.roles.options

    for(i = 0; i < options.length; i++){
        if (options[i].selected){
            rolesValue.push(options[i].value)
            rolesText.push(options[i].text)
        }
    }

    const formData = {
        id : id,
        firstName : firstName,
        lastName : lastName,
        age : age,
        email : email,
        password : password,
        roles : rolesValue
    }

    const request = new Request("http://localhost:8080/admin/mergeUser", {
        method : 'put',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        body : JSON.stringify(formData)
    })

    try {
       await fetch(request)
    }catch (error){
        console.error(error)
    }

    varRow.cells[0].innerText = id
    varRow.cells[1].innerText = firstName
    varRow.cells[2].innerText = lastName
    varRow.cells[3].innerText = age
    varRow.cells[4].innerText = email
    varRow.cells[5].innerText = rolesText
    varRow.cells[5].innerText = varRow.cells[5].innerText.replaceAll(",", " ")

    var userVarRow = document.querySelector("#userRowid" + id)

    if ((userVarRow != null) & (rolesValue.indexOf("ROLE_USER") == -1)){
        userVarRow.remove()
    }

    if (rolesValue.indexOf("ROLE_USER") != -1) {

        if (userVarRow == null){

            var userVarRow = document.querySelector("#userTableBody").insertRow()

            userVarRow.id = "userRowid" + id

            var cellID = userVarRow.insertCell(0)
            var cellFirstName = userVarRow.insertCell(1)
            var cellLastName = userVarRow.insertCell(2)
            var cellAge = userVarRow.insertCell(3)
            var cellEmail = userVarRow.insertCell(4)
            var cellRoles = userVarRow.insertCell(5)

            cellID.innerHTML = id
            cellFirstName.innerHTML = firstName
            cellLastName.innerHTML = lastName
            cellAge.innerHTML = age
            cellEmail.innerHTML = email
            cellRoles.innerHTML = rolesText
            cellRoles.innerHTML = cellRoles.innerHTML.replaceAll(",", " ")

        }else {

            userVarRow.cells[0].innerText = id
            userVarRow.cells[1].innerText = firstName
            userVarRow.cells[2].innerText = lastName
            userVarRow.cells[3].innerText = age
            userVarRow.cells[4].innerText = email
            userVarRow.cells[5].innerText = rolesText
            userVarRow.cells[5].innerText = userVarRow.cells[5].innerText.replaceAll(",", " ")
        }
    }

    window.editForm.reset()
    $('#editUserModal').modal('hide')
}

async function deleteUser(event){
    event.preventDefault()

    const id = window.deleteForm.id.value

    const varRow = document.querySelector("#rowid" + id)
    const userVarRow = document.querySelector("#userRowid" + id)

    const request = new Request("http://localhost:8080/admin/removeUser/" + id, {
        method : 'delete'
    })

    try {
        await fetch(request)
    }catch (error){
        console.error(error)
    }

    varRow.remove()

    if (userVarRow != null){
        userVarRow.remove()
    }

    window.deleteForm.reset()
    $('#deleteUserModal').modal('hide')
}

