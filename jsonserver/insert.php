<?php  
include 'config.php';
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
//echo $json;
//Insert Query
$result = mysqli_query($conn, "INSERT INTO `wirabeton`.`barang` (nama_barang, harga, stok, terjual)
VALUES ('".$obj->{'nama_barang'}."', ".$obj->{'harga'}.", ".$obj->{'stok'}.", ".$obj->{'terjual'}.")");
//mysql_close($con);
$conn->close();
//
//$posts = array($json);
if($result) {
echo json_encode(array('posts'=>array("Success"))); 
} else {
echo json_encode(array('posts'=>array("Fail"))); 
}
//$posts = array(1);
//header('Content-type: application/json');
//echo json_encode(array('posts'=>$posts)); 
?>
