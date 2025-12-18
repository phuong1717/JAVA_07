/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */

  
    
public class Ticket {
    private int maVe;
    private String tenPhim;
    private String suatChieu;
    private String ghe;
    private double giaVe;
    private String moTa;

    private Integer maNguoiDung;
    private Integer maPhim;
    private Integer maSuatChieu;
    private Integer maRap;
    private Integer maHoaDon;

    public int getMaVe() { return maVe; }
    public void setMaVe(int maVe) { this.maVe = maVe; }

    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) { this.tenPhim = tenPhim; }

    public String getSuatChieu() { return suatChieu; }
    public void setSuatChieu(String suatChieu) { this.suatChieu = suatChieu; }

    public String getGhe() { return ghe; }
    public void setGhe(String ghe) { this.ghe = ghe; }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public Integer getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(Integer maNguoiDung) { this.maNguoiDung = maNguoiDung; }

    public Integer getMaPhim() { return maPhim; }
    public void setMaPhim(Integer maPhim) { this.maPhim = maPhim; }

    public Integer getMaSuatChieu() { return maSuatChieu; }
    public void setMaSuatChieu(Integer maSuatChieu) { this.maSuatChieu = maSuatChieu; }


    public Integer getMaRap() { return maRap; }
    public void setMaRap(Integer maRap) { this.maRap = maRap; }

    public Integer getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(Integer maHoaDon) { this.maHoaDon = maHoaDon; }

 

    @Override
    public String toString() {
        return "Ticket{" + "maVe=" + maVe + ", tenPhim=" + tenPhim + ", suatChieu=" + suatChieu + ", ghe=" + ghe + ", giaVe=" + giaVe + ", moTa=" + moTa + ", maNguoiDung=" + maNguoiDung + ", maPhim=" + maPhim + ", maSuatChieu=" + maSuatChieu + ", maRap=" + maRap + ", maHoaDon=" + maHoaDon + '}';
    }

    
}
