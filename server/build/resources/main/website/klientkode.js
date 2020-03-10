document.getElementById("jsknap_hej").onclick = function(){
    fetch("/rest/hej").then(function(response){
        // pars svaret som tekst
        response.text().then(function(text){
            document.getElementById("js_output").innerText = text;
        })
    })

};

document.getElementById("jsknap_info").onclick = function(){
    brugernavn = document.getElementById("jsinput_brugernavn").value;
    console.log("Henter /rest/bruger/"+brugernavn);

    fetch("/rest/bruger/"+brugernavn).then(function(response){
        console.log("Fik svar: "+response);
        // pars svaret som json
        response.json().then(function(bruger_som_json){
            console.log("Fik svar som JSON: "+bruger_som_json);
            document.getElementById("js_output").innerText = brugernavn + " hedder "+bruger_som_json.fornavn;
            // brug JSON.stringify til at lave JSON-objektet om til tekst
            alert(JSON.stringify(bruger_som_json))
        })
    })

};
