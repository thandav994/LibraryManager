<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fines</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<style type="text/css">
.nav a {
  color: #002933;
  font-size: 13px;
  font-weight: bold;
  padding: 5px 10px;
  text-transform: uppercase;
}

.nav li {
  display: inline;
}
</style>
</head>

<body>
<div class="nav">
      <div class="container">
        <ul class="float-left">
          
          <li><a href="/LibraryManagement/Search">Home</a></li>
        </ul>
        <ul class="float-right">
          <li><a href="/LibraryManagement/manageborrowers">Borrower Management</a></li>
          <li><a href="/LibraryManagement/checkin">Check In</a></li>
          <li><a href="/LibraryManagement/Fines">Fines</a></li>
        </ul>
      </div>
    </div>



${successMessage}
${errorMessage}

<br/><br/>
<p class="lead">
  All fines 
  <div class="form-check">
    <label class="form-check-label">
    <form>
      <input type="checkbox" id="check" class="form-check-input" onclick="filter()">
    </form>
      Filter paid fines
    </label>
  </div>
</p>
<div style="visibility: ${visible}" align="center" >
	<table class="table table-inverse" style="background-color: white">
   	<thead class="thead-dark">
   		<tr>
   		<th class="text-center">Loan ID</th>
     	<th class="text-center">Fine amount</th>
     	<th class="text-center">Borrower</th>
     	<th class="text-center">Payment status</th>
     	<th class="text-center">Checked in status</th>
   		</tr>
   	</thead>
   	<tbody id="fines">
   	<c:forEach var="fine" varStatus="status" items="${fines}">
	    <tr>
	    <td class="text-center"><option value ="10"><c:out value="${fine.loan_id}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${fine.fine_amt}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${fine.borrower.name}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${fine.paid}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${fine.checkedIn}"/></option></td>
	    </tr>
	</c:forEach>
    </tbody>  
    <tfoot>
    </tfoot>
     </table>	
</div>

<p class="lead">
  Pay for checked-in books of a borrower
</p>

<div style="visibility: ${visible}" align="center">
	<table class="table table-inverse" style="background-color: white">
   	<thead class ="thead-dark">
   		<tr>
   		<th class="text-center">Card id</th>
     	<th class="text-center">Name</th>
     	<th class="text-center">Fine amount</th>
     	<th class="text-center">Pay</th>
   		</tr>
   	</thead>
   	<tbody>
   	<c:forEach var="fine" varStatus="status" items="${borrowerFines}">
	    <tr>
	    <td class="text-center"><option value ="10"><c:out value="${fine.borrower.card_id}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${fine.borrower.name}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${fine.fine_amt}"/></option></td>
	    <td class="text-center"><a class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter" name="<c:out value='${borrowerFines[status.index].borrower.card_id}'/>">Make Payment</a></td>
	    </tr>
	</c:forEach>
    </tbody>  
    <tfoot>
    </tfoot>      	
     </table>	
</div> 

<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       <script type="text/javascript">
       $('#exampleModalCenter').on('show.bs.modal', function (e) {
//     	   var href = e.relatedTarget.href;
			$(this).find('.modal-body').html('<form class="form-inline" method="post" action="/LibraryManagement/makePayment"><div class="input-group"><input type="text" name="card_id" value='+e.relatedTarget.name+
					' class="form-control" readonly>&nbsp;Are you sure? &nbsp;&nbsp;<span class="input-group-btn"><button class="btn btn-secondary" type="submit">Pay</button></span></div></form>');
    	})
    	
    	function filter(){
    	   var filter = document.getElementById("check").checked;
    	   if(filter){
	    	   document.getElementById("fines").innerHTML = '<c:forEach var="fine" varStatus="status" items="${fines}"><tr>'+
	    	   '<c:if test="${fine.paid == 'false'}">'+
	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.loan_id}"/></option></td>'+
	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.fine_amt}"/></option></td>'+
	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.borrower.name}"/></option></td>'+
	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.paid}"/></option></td>'+
	   	    '<td class="text-center"><option value ="10"><c:out value="${fine.checkedIn}"/></option></td></tr>'+
	   	    '</c:if>'+
	   	    '</c:forEach></tbody>';
   	    } else{
 	    	   document.getElementById("fines").innerHTML = '<c:forEach var="fine" varStatus="status" items="${fines}"><tr>'+
 	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.loan_id}"/></option></td>'+
 	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.fine_amt}"/></option></td>'+
 	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.borrower.name}"/></option></td>'+
 	    	   '<td class="text-center"><option value ="10"><c:out value="${fine.paid}"/></option></td>'+
 	   	       '<td class="text-center"><option value ="10"><c:out value="${fine.checkedIn}"/></option></td></tr>'+
 	   	       '</c:forEach></tbody>';
    	    }
   	    }
       </script>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>