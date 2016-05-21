<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ping</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function() {
		// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
		$.post('Ping', {
		}, function(responseText) {
			$('#somediv').html(responseText);
		});
	});
</script>
</head>
<body>
<button id="getPongMessages">Obtener mensajes de Ping</button>
        <div id="somediv"></div>
</body>
</html>