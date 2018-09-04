package com.project.robert.Robert;

/**
 * Created by Robert on 01/11/2016.
 */

public class Barang {
        String id_barang;
        String nama_barang;
        String harga;

        String stok;
        String terjual;
        public String getId_barang() {
            return id_barang;
        }
        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }
        public String getNama_barang() {
            return nama_barang;
        }
        public void setNama_barang(String nama_barang) {
            this.nama_barang = nama_barang;
        }
        public String getHarga() {

            return harga;
        }
        public void setHarga(String harga) {
            this.harga = harga;
        }
        public String getStok() {
            return stok;
        }
        public void setStok(String stok) {
            this.stok = stok;
        }
         public String getTerjual() {
           return terjual;
       }
          public void setTerjual(String terjual) {
            this.terjual = terjual;
      }

}
