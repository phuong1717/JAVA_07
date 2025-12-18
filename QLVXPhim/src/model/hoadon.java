/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Windows
 */public class hoadon {
    private int MaHoaDon;
    private int MaNguoiDung;
    private int MaVe;
    private int MaCombo;  
    private Date NgayDat;
    private double TongTien;
    private String PhuongThucThanhToan;
    
  
    public hoadon() {
    }
    

    public hoadon(int MaHoaDon, int MaNguoiDung, int MaVe, int MaCombo, Date NgayDat, double TongTien, String PhuongThucThanhToan) {
        this.MaHoaDon = MaHoaDon;
        this.MaNguoiDung = MaNguoiDung;
        this.MaVe = MaVe;
        this.MaCombo = MaCombo;
        this.NgayDat = NgayDat;
        this.TongTien = TongTien;
        this.PhuongThucThanhToan = PhuongThucThanhToan;
    }
    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public int getMaNguoiDung() {
        return MaNguoiDung;
    }

    public int getMaVe() {
        return MaVe;
    }

    public int getMaCombo() {
        return MaCombo;
    }

    public Date getNgayDat() {
        return NgayDat;
    }

    public double getTongTien() {
        return TongTien;
    }

    public String getPhuongThucThanhToan() {
        return PhuongThucThanhToan;
    }

    public void setMaHoaDon(int MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public void setMaNguoiDung(int MaNguoiDung) {
        this.MaNguoiDung = MaNguoiDung;
    }

    public void setMaVe(int MaVe) {
        this.MaVe = MaVe;
    }

    public void setMaCombo(int MaCombo) {
        this.MaCombo = MaCombo;
    }

    public void setNgayDat(Date NgayDat) {
        this.NgayDat = NgayDat;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public void setPhuongThucThanhToan(String PhuongThucThanhToan) {
        this.PhuongThucThanhToan = PhuongThucThanhToan;
    }

    @Override
    public String toString() {
        return "hoadon{" + "MaHoaDon=" + MaHoaDon + ", MaNguoiDung=" + MaNguoiDung + ", MaVe=" + MaVe + ", MaCombo=" + MaCombo + ", NgayDat=" + NgayDat + ", TongTien=" + TongTien + ", PhuongThucThanhToan=" + PhuongThucThanhToan + '}';
    }
   
    

    }
         
        