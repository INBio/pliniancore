<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="generic"/>
<title>CRBio</title>
</head>
<body>
  <div class="body">
  ${results }
	  <div class="taxonomy">
	  <p>Total records: ${totalRecords }</p>
	  	<ul>
	  		<li><g:message code="scientificName"/>: ${results.scientificNameSimple}</li>
	  	</ul>
	  </div>
  </div>
</body>
</html>