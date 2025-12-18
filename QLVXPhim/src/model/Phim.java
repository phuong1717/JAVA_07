/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Phim {
    private int maPhim;
    private String tenPhim;
    private int thoiLuong;
    private String danhGia;
    private String theLoai;
    private String moTa;
    private String ngayPhatHanh;

    // Constructor rỗng
    public Phim() {}

    // Constructor đầy đủ
    public Phim(int maPhim, String tenPhim, int thoiLuong, String danhGia,
                String theLoai, String moTa, String ngayPhatHanh) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.thoiLuong = thoiLuong;
        this.danhGia = danhGia;
        this.theLoai = theLoai;
        this.moTa = moTa;
        this.ngayPhatHanh = ngayPhatHanh;
    }

    // Getter - Setter
    public int getMaPhim() { return maPhim; }
    public void setMaPhim(int maPhim) { this.maPhim = maPhim; }

    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) { this.tenPhim = tenPhim; }

    public int getThoiLuong() { return thoiLuong; }
    public void setThoiLuong(int thoiLuong) { this.thoiLuong = thoiLuong; }

    public String getDanhGia() { return danhGia; }
    public void setDanhGia(String danhGia) { this.danhGia = danhGia; }

    public String getTheLoai() { return theLoai; }
    public void setTheLoai(String theLoai) { this.theLoai = theLoai; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getNgayPhatHanh() { return ngayPhatHanh; }
    public void setNgayPhatHanh(String ngayPhatHanh) { this.ngayPhatHanh = ngayPhatHanh; }

    @Override
    public String toString() {
        return "Phim{" +
                "maPhim=" + maPhim +
                ", tenPhim='" + tenPhim + '\'' +
                ", thoiLuong=" + thoiLuong +
                ", danhGia='" + danhGia + '\'' +
                ", theLoai='" + theLoai + '\'' +
                ", moTa='" + moTa + '\'' +
                ", ngayPhatHanh=" + ngayPhatHanh +
                '}';
    }

    public String getThoiGianPhatHanh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

