<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript"
	src="js/manage-shortUrls.js"></script>
  <title>Url Shortening</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Font Awesome -->
  
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mdbootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mdbDatatables.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Bootstrap core CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
<!-- Material Design Bootstrap -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.4/css/mdb.min.css" rel="stylesheet">  
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  
   
  <!-- JQuery -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- Bootstrap tooltips -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.13.0/umd/popper.min.js"></script>
<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/js/bootstrap.min.js"></script>
<!-- MDB core JavaScript -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.4/js/mdb.min.js"></script>
 

  
</head>
<body>


<!--Main Navigation-->

<jsp:include page="/jsp/templete/header.jsp" flush="true" />

<!--Main Navigation-->


<div class="container" style="margin-top:50px">  
  <form>
  <div class="md-form form-sm">
  	<h2>Simplify your Links</h2>
    <input type="text" id="url" name="url" placeholder="Enter URL" class="form-control form-control-sm" style="margin-bottom: 0px">
	    <div style="color:red;display:none;margin-top: 3px;font-size:12px" id="errorNoUrl">Please enter a URL</div>
	     <div style="color:red;display:none;margin-top: 3px;font-size:12px" id="errorInvalidUrl">Please enter a valid URL</div>
	     <div style="color:red;display:none;margin-top: 3px;font-size:12px" id="error"></div>
	     <div style="color:green;display:none;margin-top: 3px;font-size:15px" id="successUrl"></div>
	     <button type="button" class="btn btn-primary btn-sm" id="submitBtn" style="margin-left:0px; margin-top:12px">Shorten URL</button>
  </div>    
  </form>
</div>

<script type="text/javascript">

		$( document ).ready(function() {	
			$("#home").addClass("active");
			shortenUrlClick();					
		});
	
</script>

</body>
</html>