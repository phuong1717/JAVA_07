/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CRUD.ConnectSQL;
import java.sql.*;
import java.util.ArrayList;
import model.Phim;

public class PhimDAO {

    // Lấy toàn bộ phim
    public ArrayList<Phim> getAllPhim() {
        ArrayList<Phim> list = new ArrayList<>();
        String sql = "SELECT * FROM Phim";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Phim p = new Phim(
                        rs.getInt("MaPhim"),
                        rs.getString("TenPhim"),
                        rs.getInt("ThoiLuong"),
                        rs.getString("DanhGia"),
                        rs.getString("TheLoai"),
                        rs.getString("MoTa"),
                        rs.getString("NgayPhatHanh")   // ✔ STRING
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm phim
    public boolean insertPhim(Phim p) {
        String sql = "INSERT INTO Phim(TenPhim, ThoiLuong, DanhGia, TheLoai, MoTa, NgayPhatHanh) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getTenPhim());
            ps.setInt(2, p.getThoiLuong());
            ps.setString(3, p.getDanhGia());
            ps.setString(4, p.getTheLoai());
            ps.setString(5, p.getMoTa());
            ps.setString(6, p.getNgayPhatHanh());   // ✔ STRING

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Sửa phim
    public boolean updatePhim(Phim p) {
        String sql = "UPDATE Phim SET TenPhim=?, ThoiLuong=?, DanhGia=?, TheLoai=?, MoTa=?, NgayPhatHanh=? "
                   + "WHERE MaPhim=?";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getTenPhim());
            ps.setInt(2, p.getThoiLuong());
            ps.setString(3, p.getDanhGia());
            ps.setString(4, p.getTheLoai());
            ps.setString(5, p.getMoTa());
            ps.setString(6, p.getNgayPhatHanh());   // ✔ STRING
            ps.setInt(7, p.getMaPhim());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa phim
    public boolean deletePhim(int maPhim) {
        String sql = "DELETE FROM Phim WHERE MaPhim=?";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maPhim);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lấy phim theo ID
    public Phim getPhimById(int id) {
        String sql = "SELECT * FROM Phim WHERE MaPhim=?";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Phim(
                        rs.getInt("MaPhim"),
                        rs.getString("TenPhim"),
                        rs.getInt("ThoiLuong"),
                        rs.getString("DanhGia"),
                        rs.getString("TheLoai"),
                        rs.getString("MoTa"),
                        rs.getString("NgayPhatHanh")    // ✔ STRING
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}


