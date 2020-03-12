document.getElementById("jsknap_hej").onclick = function(){
    fetch("/rest/hej").then(function(response){
        // pars svaret som tekst
        response.text().then(function(text){
            document.getElementById("js_output").innerText = text;
        })
    })

};

document.getElementById("login_knap").onclick = function(){
    brugernavn = document.getElementById("jsinput_brugernavn").value;
    adgangskode = document.getElementById("jsinput_adgangskode").value;
    console.log("Henter /rest/bruger/"+brugernavn);
    open("/rest/bruger/"+brugernavn+"?adgangskode="+adgangskode);

    fetch("/rest/bruger/"+brugernavn+"?adgangskode="+adgangskode).then(function(response){
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
