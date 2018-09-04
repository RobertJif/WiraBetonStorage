package com.project.robert.Robert;

/**
 * Created by Robert on 01/11/2016.
 */

public class Transaksi {
        String id_transaksi;
        String id_barang;
        String jumlah;
        String total;
        String tanggal;
        public String getId_transaksi() {
            return id_transaksi;
        }
        public void setId_transaksi(String id_transaksi) {
            this.id_transaksi = id_transaksi;
        }
       public String getId_barang() {
        return id_barang;
    }
        public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getJumlah() {

            return jumlah;
        }
        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }
        public String getTotal() {
            return total;
        }
        public void setTotal(String total) {
            this.total = total;
        }
         public String getTanggal() {
           return tanggal;
       }
          public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
      }

}
