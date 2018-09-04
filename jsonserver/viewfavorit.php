<?php
include 'config.php';
include 'barang.php';
$return_arr = array();
$fetch = mysqli_query($conn, "SELECT id_barang,nama_barang,terjual FROM barang order by terjual desc");
if ($fetch->num_rows > 0) {
while($row = mysqli_fetch_assoc($fetch)) {
$c = new Barang();
$c->id_barang = $row['id_barang'];
$c->nama_barang = $row['nama_barang'];
$c->terjual = $row['terjual'];

array_push($return_arr, $c);
}
} else {
array_push($return_arr, 'No Data');
}
$conn->close();
echo json_encode(array('barang'=> $return_arr));
?>  
