/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import CRUD.ConnectSQL;
import java.sql.*;
import java.util.ArrayList;
import model.Phim;

public class PhimCRUD1 {

    // ====== LẤY TẤT CẢ PHIM ======
    public ArrayList<Phim> getAllPhim() {
        ArrayList<Phim> list = new ArrayList<>();
        String sql = "SELECT * FROM Phim";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Phim p = new Phim();
                p.setMaPhim(rs.getInt("MaPhim"));
                p.setTenPhim(rs.getString("TenPhim"));
                p.setThoiLuong(rs.getInt("ThoiLuong"));
                p.setDanhGia(rs.getString("DanhGia"));
                p.setTheLoai(rs.getString("TheLoai"));
                p.setMoTa(rs.getString("MoTa"));
                p.setNgayPhatHanh(rs.getString("NgayPhatHanh"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ====== THÊM PHIM ======
    public boolean insertPhim(Phim p) {
        String sql = "INSERT INTO Phim (TenPhim, TheLoai, ThoiLuong, MoTa) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTenPhim());
            ps.setString(2, p.getTheLoai());
            ps.setInt(3, p.getThoiLuong());
            ps.setString(4, p.getMoTa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ====== SỬA PHIM ======
    public boolean updatePhim(Phim p) {
        String sql = "UPDATE Phim SET TenPhim = ?, TheLoai = ?, ThoiLuong = ?, MoTa = ? WHERE MaPhim = ?";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTenPhim());
            ps.setString(2, p.getTheLoai());
            ps.setInt(3, p.getThoiLuong());
            ps.setString(4, p.getMoTa());
            ps.setInt(5, p.getMaPhim());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ====== XÓA PHIM ======
    public boolean deletePhim(int maPhim) {
        String sql = "DELETE FROM Phim WHERE MaPhim = ?";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhim);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public Phim getPhimById(int id) {
    Phim p = null;
    try {
        Connection con = ConnectSQL.getConnection();
        String sql = "SELECT * FROM Phim WHERE MaPhim = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            p = new Phim(
                rs.getInt("MaPhim"),
                rs.getString("TenPhim"),
                rs.getInt("ThoiLuong"),
                rs.getString("DanhGia"),
                rs.getString("TheLoai"),
                rs.getString("MoTa"),
                rs.getString("NgayPhatHanh")
            );
        }

        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return p;
}

}

