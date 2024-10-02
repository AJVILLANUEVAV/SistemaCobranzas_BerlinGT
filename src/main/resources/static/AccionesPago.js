function pagoPositivo(){
	var montoPago=document.getElementById('montoPago').value;
	montoPago=parseFloat(montoPago);
	
//	var adelanto=document.getElementById('adelanto').value;
//	adelanto=parseFloat(adelanto);
//	
//	if (!isNaN(totalDeuda) && !isNaN(adelanto)) {
//        
//        var saldoDeuda = totalDeuda - adelanto;
//        
//        document.getElementById('saldoDeuda').value = saldoDeuda.toFixed(2); // .toFixed(2) limits it to 2 decimal places
//    }
    
    if((montoPago<=0)){
		alert("El deudor no puede tener un pago negativo o cero");
	}

//    window.onload=calcularSaldo;
    console.log("El pago es: "+montoPago);
}

function updateTransferencia() {
	console.log("Cuando carga updateTransferencia");
	var tipoPagoSelect = document.querySelector('select[name="tipoPago"]');
	var documentoTransferenciaInput = document.querySelector('input[name="documentoTransferencia"]');
	if (tipoPagoSelect.value === 'EFECTIVO') {
		documentoTransferenciaInput.readOnly = true;
		documentoTransferenciaInput.value = ''; // Optional: clear the value if Efectivo is selected
	} else {
		documentoTransferenciaInput.readOnly = false;
	}
}

window.addEventListener('load', function() {
	console.log("Cuando carga la pagina");
	var documentoTransferenciaInput = document.querySelector('input[name="documentoTransferencia"]');
	documentoTransferenciaInput.readOnly = true;
	
	/*
	// This function updates the readonly attribute of the documentoTransferencia input
	function updateTransferenciaInput() {
		console.log("Entrooooooooooooooooo");
		var tipoPagoSelect = document.querySelector('select[name="tipoPago"]');
		var documentoTransferenciaInput = document.querySelector('input[name="documentoTransferencia"]');
		if (tipoPagoSelect.value === 'EFECTIVO') {
			documentoTransferenciaInput.readOnly = true;
			documentoTransferenciaInput.value = ''; // Optional: clear the value if Efectivo is selected
		} else {
			documentoTransferenciaInput.readOnly = false;
		}
	}

	// Call the function to set the correct initial state when the page loads
	updateTransferenciaInput();

	// Add an event listener to the Tipo de Pago select element
	document.querySelector('select[name="tipoPago"]').addEventListener('change', function() {
		updateTransferenciaInput();
	});
	*/
});