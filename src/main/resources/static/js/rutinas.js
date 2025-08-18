function addCart (formulario){
    var idProducto = formulario.elements[0].value;
    var existencias = formulario.elements[1].value;
    if (existencias>0){
        var ruta = "/carrito/agregar/"+idProducto;
        $("#resultBlock").load(ruta);
    } else {
         alert("no hay existencias");
    }
    
}

