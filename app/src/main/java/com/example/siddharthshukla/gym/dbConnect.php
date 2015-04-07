<?php
    function getConnection()
      {

       $con = mysql_connect("localhost","newuser","password");
         return $con;
    }
?>