<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">	
	<script
			  src="https://code.jquery.com/jquery-1.11.2.js"
			  integrity="sha256-WMJwNbei5YnfOX5dfgVCS5C4waqvc+/0fV7W2uy3DyU="
			  crossorigin="anonymous"></script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

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

	
<script type="text/javascript">		
		var shortUrls=new Array();
		var table;
		<%
		if (request.getAttribute("shortUrls")!=null){
		%>
		shortUrls=<%=((com.google.gson.JsonArray)request.getAttribute("shortUrls"))%>;
		//alert(shortUrls);
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


	<div class="cover"></div>	
	<div class="col-md-12" style="margin-top: 12px">
	<table id="shortUrls" class="table table-striped table-bordered" width="100%" cellspacing="0">
        <thead>
			<tr>
				<th>Id</th>				
				<th>Orignal Url</th>
				<th>Short Url</th>	
				<th>Expiry Date</th>
				<th>Hits</th>															
			</tr>
		</thead>										        
        <tbody></tbody>
    </table>
    
    <div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
    
    </div>
		<script type="text/javascript">

		$( document ).ready(function() {	
			initData();	
			$("#urlTable").addClass("active");				
		});
	
		</script>

		
</body>
</html>