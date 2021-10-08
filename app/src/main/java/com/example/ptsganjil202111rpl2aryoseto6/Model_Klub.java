package com.example.ptsganjil202111rpl2aryoseto6;

public class Model_Klub {

    private String image;
    private String nama;
    private String tahun;
    private String deskripsi;



    public Model_Klub(String image, String nama, String tahun, String deskripsi) {
        this.image = image;
        this.nama = nama;
        this.tahun = tahun;
        this.deskripsi = deskripsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }


}
