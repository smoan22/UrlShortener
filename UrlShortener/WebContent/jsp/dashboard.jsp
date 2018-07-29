<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>

<!-- stylesheet ENDS  -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">	
	<script
			  src="https://code.jquery.com/jquery-1.11.2.js"
			  integrity="sha256-WMJwNbei5YnfOX5dfgVCS5C4waqvc+/0fV7W2uy3DyU="
			  crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Material Design Bootstrap -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.4/css/mdb.min.css" rel="stylesheet">  

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mdbootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mdbDatatables.css">


<!-- MDB core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.4/js/mdb.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/dataTables.bootstrap4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
	
<script type="text/javascript">		
		var urlClicks=new Array();
		var table;
		<%
		if (request.getAttribute("urlClicks")!=null){
		%>
		urlClicks=<%=((com.google.gson.JsonArray)request.getAttribute("urlClicks"))%>;
		//alert(urlClicks);
		<%	
		}		
		%>		
	</script>
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/manage-shortUrls.js"></script>
		
<title>Url Shortening</title>
<meta http-equiv="Content-Entry" content="text/html; charset=iso-8859-1">
</head>
<body>


<!--Main Navigation-->

<jsp:include page="/jsp/templete/header.jsp" flush="true" />

<!--Main Navigation-->

   <canvas id="myChart"></canvas>
		
</body>
</html>