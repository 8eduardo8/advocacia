
var ws;

$(document).ready(
    function connect() {

        var idProcesso = $("#chatForm\\:idProcesso").val();

        var dominio = '${dominio.url}';

        ws = new WebSocket("ws://" + dominio + "/advocacia/chat/" + idProcesso);

        ws.onopen = function(event) {
            console.log("Socket conectado com sucesso: ", event.message)
        }

        ws.onmessage = function(event) {
            var json = JSON.parse(event.data);

            atualizaChat(json)

            console.log("mensagem recebida -> " + event.data);
        }

        ws.onclose = function(event) {
            console.log('Socket está fechado. Reconexão será em até 1 segundo.', event.data);
            setTimeout(function() {
                connect();
            }, 1000);
        }


        ws.onerror = function(event) {
            console.error('Socket encontrou um erro: ', event.data, 'Fechando socket');
            ws.close()
        };


        autoScroll();

    }
);

$(document).ready(

    function () {

        $('#select_file').click(function () {
            $('#file').click();
            $('#file').change(function () {
                var filename = $('#file').val();
                $('#select_file').html(filename);
            });
        })
    }
);


function getBase64(file) {
    return new Promise((resolve, reject) => {
        if (typeof file != "undefined") {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
                let encoded = reader.result.toString().replace(/^data:(.*,)?/, '');
                if ((encoded.length % 4) > 0) {
                    encoded += '='.repeat(4 - (encoded.length % 4));
                }
                resolve(encoded);
            };
            reader.onerror = error => reject(error);
        }
    });
}

function downloadFile(idFile, type){
    $.ajax({
        type: "GET",
        url: "/rest/v1/documento/"+idFile +"/download",
        contentType: type,
        success: function(json, status){
            if (status != "success") {
                log("Error loading data");
                return;
            }
            log("Data loaded!");
        },
        error: function(result, status, err) {
            log("Error loading data");
            return;
        }
    });
}

async function sendMessage() {

    var data = new Date();
    var usuario = $("#chatForm\\:nomeUsuario").val();
    var messageForm = $("#message");
    var file = $('#file')[0].files[0];
    var message = {
        "idUsuario": $("#chatForm\\:idUsuario").val(),
        "nomeUsuario": usuario,
        "content": messageForm.val(),
        "tipoNota": 1,
        "file": await getBase64(file),
        "nomeFile": file.name,
        "formatoFile": file.type,
        'data': data
    }
    ws.send(JSON.stringify(message));

    messageForm.val('');

    $('#file').val('');

    $('#select_file').html("Please select image");
}

function atualizaChat(json){

    json.content, json.data, json.nomeUsuario, json.tipoNota

    var usuario = $("#chatForm\\:nomeUsuario").val()
    var mensagens = $("#messages");

    if (json.tipoNota == 1) {

        if (json.nomeUsuario == usuario) {

            mensagens.append('<div class="ui-outputpanel ui-widget">' +
                '<div align="right"> '  +
                '  <div class="balaoEnvio" style="text-align: left; margin-bottom: 10px; white-space: normal;"> '  +
                '    <p>' + json.content + '</p> '  +
                '    <div align="right" style="text-align: right;text-size-adjust: auto;font-size: smaller;"> '  +
                '        <p style="color:#303030">' + json.nomeUsuario + ' - ' + json.data + '</p> '  +
                '         '  +
                '    </div> '  +
                '  </div> '  +
                '  </div> '  +
                '</div>');

        } else {

            mensagens.append('<div class="ui-outputpanel ui-widget">' +
                '<div class="balao" style="margin-bottom: 10px; white-space: normal;"> '  +
                '    <p>' + json.content + '</p> '  +
                '    <div style="text-align: right;text-size-adjust: auto;font-size: smaller;"> '  +
                '        <p style="color:#303030">' + json.nomeUsuario + ' - ' + json.data + '</p> '  +
                '         '  +
                '    </div> '  +
                '  </div>' +
                '</div>')

        }

    } else {

        mensagens.append('<div class="ui-outputpanel ui-widget">' +
            '   <div align="center"> '  +
            '       <div class="balao-notificacao" style="text-align: center; margin-bottom: 10px; white-space: normal;"> '  +
            '       <label>' + json.content + '</label> '  +
            '       <div align="right" style="text-align: right;text-size-adjust: auto;"> '  +
            '           <p style="color:#303030">' + json.nomeUsuario + ' - ' + json.data + '</p> '  +
            '           '  +
            '       </div> '  +
            '        </div> '  +
            '        </div> '  +
            '</div>');

    }

    autoScroll();
}

function autoScroll(){

    let messages = $("#messages");

    messages.animate({
        scrollTop: 9999
    }, 1000);
}