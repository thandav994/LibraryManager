<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check In</title>
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


<div class="container">
	<div class="row">
        <div class="col-md-10">
    		<h2>Search for a book to check-in</h2>
    		<form class="form-inline" method="post" action="/LibraryManagement/fetchBooksForCheckIn">
            <div class="col-lg-6">
		    <div class="input-group">
		      <input type="text" name="query" class="form-control" placeholder="Enter isbn, card_no or borrower name">
		      <span class="input-group-btn">
		        <button class="btn btn-secondary" type="submit">Search</button>
		      </span>
		    </div>
		  </div>
		</div></form>
        </div>
	</div>
</div>
<br/>


${successMessage}
${errorMessage}

<br/><br/>
<div style="visibility: ${visible}" align="center" >
	<table class="table table-striped table-hover table-bordered" style="background-color: white">
   	<thead>
   		<tr>
   		<th class="text-center">Loan ID</th>
     	<th class="text-center">ISBN</th>
     	<th class="text-center">card_id</th>
     	<th class="text-center">borrowerName</th>
     	<th class="text-center">date_out</th>
     	<th class="text-center">due_date</th>
   		</tr>
   	</thead>
   	<tbody>
   	<c:forEach var="loan" varStatus="status" items="${loans}">
	    <tr>
	    <td class="text-center"><option value ="10"><c:out value="${loan.loan_id}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${loan.isbn}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${loan.card_id}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${loan.borrowerName}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${loan.date_out}"/></option></td>
	    <td class="text-center"><option value ="10"><c:out value="${loan.due_date}"/></option></td>
	    <td class="text-center"><a class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter" name="<c:out value='${loans[status.index].loan_id}'/>">CheckIn</a></td>
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
        <h5 class="modal-title" id="exampleModalLongTitle">You are about to check-in</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       <script type="text/javascript">
       $('#exampleModalCenter').on('show.bs.modal', function (e) {
//     	   var href = e.relatedTarget.href;
			$(this).find('.modal-body').html('<form class="form-inline" method="post" action="/LibraryManagement/checkin"><div class="input-group"><input type="text" name="loan_id" value='+e.relatedTarget.name+
					' class="form-control" readonly>&nbsp;Are you sure? &nbsp;&nbsp;<span class="input-group-btn"><button class="btn btn-secondary" type="submit">Check In</button></span></div></form>');
    	})
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