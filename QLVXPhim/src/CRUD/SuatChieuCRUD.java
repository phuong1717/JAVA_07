/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import CRUD.ConnectSQL;
import java.sql.*;
import java.util.ArrayList;
import model.SuatChieu;


public class SuatChieuCRUD {
    
    // ===== LẤY TẤT CẢ SUẤT CHIẾU =====
    public ArrayList<SuatChieu> getAllSuatChieu() {
        ArrayList<SuatChieu> list = new ArrayList<>();
        String sql = "SELECT * FROM SuatChieu";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SuatChieu sc = new SuatChieu();
                sc.setMaSuatChieu(rs.getInt("MaSuatChieu"));
                sc.setMaPhim(rs.getInt("MaPhim"));
                sc.setBatDau(rs.getTimestamp("BatDau").toLocalDateTime());
                sc.setKetThuc(rs.getTimestamp("KetThuc").toLocalDateTime());
                sc.setTrangThai(rs.getString("TrangThai"));

                list.add(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== THÊM SUẤT CHIẾU =====
    public boolean insertSuatChieu(SuatChieu sc) {
        String sql = "INSERT INTO SuatChieu (MaPhim, BatDau, KetThuc, TrangThai) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sc.getMaPhim());
            ps.setTimestamp(2, Timestamp.valueOf(sc.getBatDau()));
            ps.setTimestamp(3, Timestamp.valueOf(sc.getKetThuc()));
            ps.setString(4, sc.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===== SỬA SUẤT CHIẾU =====
    public boolean updateSuatChieu(SuatChieu sc) {
        String sql = "UPDATE SuatChieu SET MaPhim=?, BatDau=?, KetThuc=?, TrangThai=? WHERE MaSuatChieu=?";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sc.getMaPhim());
            ps.setTimestamp(2, Timestamp.valueOf(sc.getBatDau()));
            ps.setTimestamp(3, Timestamp.valueOf(sc.getKetThuc()));
            ps.setString(4, sc.getTrangThai());
            ps.setInt(5, sc.getMaSuatChieu());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===== XÓA SUẤT CHIẾU =====
    public boolean delete(int maSuatChieu) {
        String sql = "DELETE FROM SuatChieu WHERE MaSuatChieu=?";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maSuatChieu);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
}

