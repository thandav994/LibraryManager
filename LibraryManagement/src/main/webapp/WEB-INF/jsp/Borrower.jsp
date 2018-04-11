<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Borrower</title>
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
  <h2>Add a borrower</h2>
  <form class="form-horizontal" action="/LibraryManagement/manageborrowers" method="post">
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-10">
        <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="ssn">SSN:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="ssn" placeholder="Enter ssn" name="ssn" required>
      </div>
    </div>

    <div class="form-group">
      <label class="control-label col-sm-2" for = "ssn">Borrower Name:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="fname" placeholder="Enter borrower name" name="name" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for = "address">Address:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="address" placeholder="Enter Address here" name="address">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for = "city">City:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="city" placeholder="Enter City here" name="city">
      </div>
    </div>
     <div class="form-group">
      <label class="control-label col-sm-2" for = "state">State:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="state" placeholder="Enter State" name="state">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for = "phone">Phone Number:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="phone" placeholder="Enter Phone Number" name="phone">
      </div>
    </div>
    
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" id = "submitButton" class="btn btn-default">Submit</button>
      </div>
    </div>
  </form>
</div>

${successMessage}
${errorMessage}


</body>
</html>