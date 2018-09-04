<?php
include 'config.php';
include 'history.php';
$return_arr = array();
$fetch = mysqli_query($conn, "SELECT barang.nama_barang,transaksi.jumlah,transaksi.total,transaksi.tanggal from barang,transaksi where barang.id_barang=transaksi.id_barang order by transaksi.id_transaksi desc");
if ($fetch->num_rows > 0) {
while($row = mysqli_fetch_assoc($fetch)) {
$c = new History();
$c->nama_barang = $row['nama_barang'];
$c->jumlah = $row['jumlah'];
$c->total = $row['total'];
$c->tanggal = $row['tanggal'];

array_push($return_arr, $c);
}
} else {
array_push($return_arr, 'No Data');
}
$conn->close();
echo json_encode(array('history'=> $return_arr));
?>  
