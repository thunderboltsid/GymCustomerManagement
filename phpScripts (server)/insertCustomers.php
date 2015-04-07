<?php
	$host='localhost';
	$uname='newuser';
	$pwd='password';
	$db="gymDB";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$firstName=$_REQUEST['firstName'];
	$secondName=$_REQUEST['secondName'];
	$contactNumber=$_REQUEST['contactNumber'];
	$manager=$_REQUEST['manager'];

	$flag['code']=0;

	if($r=mysql_query("INSERT INTO Customers(firstName,secondName,contactNumber,manager) VALUES('$firstName','$secondName','$contactNumber','$manager') ",$con))
	{
		$flag['code']=1;
		echo"hi";
	}

	print(json_encode($flag));
	mysql_close($con);
?>