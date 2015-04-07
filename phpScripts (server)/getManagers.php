<?php
    $con = $con = mysql_connect("localhost","newuser","password");

    if (!$con)
    {
      die('Could not connect: ' . mysql_error());
    }
    mysql_select_db("gymDB", $con);

    $result = mysql_query("SELECT * FROM Managers");

    while($row = mysql_fetch_assoc($result))
    {
        $output[]=$row;
    }

    print(json_encode($output));

    mysql_close($con);
?>
