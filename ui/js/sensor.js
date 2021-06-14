
const FUNCTION_URL = 'http://192.168.0.111:8080/function/sensorsettings/';

async function persistData() {
    var email = $("#inputEmail").val();
    var minimumMoisture = $("#inputMoisture").val();
    var text = '{ "email":"' + email + '",  "min_moisture":' + minimumMoisture + '}';

    var xhr = new XMLHttpRequest();
    xhr.open("POST", FUNCTION_URL, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    try {
        xhr.send(text)
        var response = xhr.responseText;
        alert(response);
    } catch (err) {
        alert("Request failed");
    }

}