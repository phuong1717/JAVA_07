/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

public class SuatChieu {
    private int maSuatChieu;
    private int maPhim;
    private LocalDateTime batDau;
    private LocalDateTime ketThuc;
    private String trangThai;

    // Constructor rỗng
    public SuatChieu() {}

    // Constructor đầy đủ
    public SuatChieu(int maSuatChieu, int maPhim, LocalDateTime batDau,
                     LocalDateTime ketThuc, String trangThai) {
        this.maSuatChieu = maSuatChieu;
        this.maPhim = maPhim;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
        this.trangThai = trangThai;
    }

    public SuatChieu(int parseInt, String maPhim, String batDau, String ketThuc, String trangThai) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getter & Setter
    public int getMaSuatChieu() { return maSuatChieu; }
    public void setMaSuatChieu(int maSuatChieu) { this.maSuatChieu = maSuatChieu; }

    public int getMaPhim() { return maPhim; }
    public void setMaPhim(int maPhim) { this.maPhim = maPhim; }

    public LocalDateTime getBatDau() { return batDau; }
    public void setBatDau(LocalDateTime batDau) { this.batDau = batDau; }

    public LocalDateTime getKetThuc() { return ketThuc; }
    public void setKetThuc(LocalDateTime ketThuc) { this.ketThuc = ketThuc; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return "SuatChieu{" +
                "maSuatChieu=" + maSuatChieu +
                ", maPhim=" + maPhim +
                ", batDau=" + batDau +
                ", ketThuc=" + ketThuc +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}

