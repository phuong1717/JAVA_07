/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Windows
 */

public class Seat {
    private int maGhe;
    private String hangGhe;
    private int soGhe;
    private int soHang;
    private String loaiGhe;
    private String trangThai;

    public Seat() {
    }

    public Seat(int maGhe, String hangGhe, int soGhe, int soHang, String loaiGhe, String trangThai) {
        this.maGhe = maGhe;
        this.hangGhe = hangGhe;
        this.soGhe = soGhe;
        this.soHang = soHang;
        this.loaiGhe = loaiGhe;
        this.trangThai = trangThai;
    }

    // Getter – Setter
    public int getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(int maGhe) {
        this.maGhe = maGhe;
    }

    public String getHangGhe() {
        return hangGhe;
    }

    public void setHangGhe(String hangGhe) {
        this.hangGhe = hangGhe;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public int getSoHang() {
        return soHang;
    }

    public void setSoHang(int soHang) {
        this.soHang = soHang;
    }

    public String getLoaiGhe() {
        return loaiGhe;
    }

    public void setLoaiGhe(String loaiGhe) {
        this.loaiGhe = loaiGhe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Seat{" + "maGhe=" + maGhe + ", hangGhe=" + hangGhe + ", soGhe=" + soGhe + ", soHang=" + soHang + ", loaiGhe=" + loaiGhe + ", trangThai=" + trangThai + '}';
    }
}

