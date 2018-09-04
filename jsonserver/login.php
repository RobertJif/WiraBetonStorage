<?php
include 'config.php';
$json = file_get_contents('php://input');
$obj = json_decode($json);
$login=mysql_query("SELECT * FROM ortu
			WHERE username='".$obj->{'username'}."' AND password='".$obj->{'password'}."'");
$cocok=mysql_num_rows($login);
$r=mysql_fetch_array($login);

if ($cocok > 0){
	echo json_encode(array('posts'=>array("Success")));
	}
else {
echo json_encode(array('posts'=>array("Fail")));
}
$conn->close();
?>