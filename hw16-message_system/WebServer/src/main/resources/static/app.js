let stompClient = null;

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/client', (client) => showSavedClient(client.body));
        stompClient.subscribe('/topic/clients', (clientsList) => showAllClients(JSON.parse(clientsList.body)));
    });
}

const saveClient = (clientName, clientRole) => stompClient.send("/app/client", {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
}, JSON.stringify({name: clientName, role: clientRole}))
const showSavedClient = (client) => $("#savedClient").text('Создан клиент: ' + client)

const getAllClients = () => stompClient.send("/app/clients", {},)
const showAllClients = (clients) => jsonToHtmlTable(clients, '#clients')

function jsonToHtmlTable(jsonObj, selector) {
    clearRows(selector);
    addRows(jsonObj, selector);
}

function clearRows(selector) {
    $(selector + ' tbody').empty()
}

function addRows(jsonObj, selector) {
    var tbody = $(selector + ' tbody');
    $.each(jsonObj, function (i, d) {
        var row = '<tr>';
        $.each(d, function (j, e) {
            row += '<td>' + e + '</td>';
        });
        row += '</tr>';
        tbody.append(row);
    });
}

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
});
