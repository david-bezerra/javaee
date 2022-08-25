/**
 * Validação de formulário @author David Bezerra
 */
 
 function validar(){
	//Criou a variável nome
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	let email = frmContato.email.value
	
	if(nome === ""){
		alert("Preencha o campo Nome")
		frmContato.nome.focus()
		return false
	}else if(fone === ""){
		alert("Preencha o campo Fone")
		frmContato.fone.focus()
		return false
	}else{
		document.forms["frmContato"].submit()
	}
}