package DAO;

import CRUD.ConnectSQL;
import model.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    // Lấy toàn bộ vé
    public List<Ticket> getAll() {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM Ticket";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setMaVe(rs.getInt("MaVe"));
                t.setTenPhim(rs.getString("TenPhim"));
                t.setSuatChieu(rs.getString("SuatChieu"));
                t.setGhe(rs.getString("Ghe"));
                t.setGiaVe(rs.getDouble("GiaVe"));
                t.setMoTa(rs.getString("MoTa"));

                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm vé và trả về mã vé mới được tạo
    public int insertAndGetId(Ticket t) {
        String sql = "INSERT INTO Ticket (TenPhim, SuatChieu, Ghe, GiaVe, MoTa) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getTenPhim());
            ps.setString(2, t.getSuatChieu());
            ps.setString(3, t.getGhe());
            ps.setDouble(4, t.getGiaVe());
            ps.setString(5, t.getMoTa());

            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    // Thêm vé (giữ nguyên cho tương thích)
    public boolean insert(Ticket t) {
        String sql = "INSERT INTO Ticket (TenPhim, SuatChieu, Ghe, GiaVe, MoTa) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTenPhim());
            ps.setString(2, t.getSuatChieu());
            ps.setString(3, t.getGhe());
            ps.setDouble(4, t.getGiaVe());
            ps.setString(5, t.getMoTa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật vé
    public boolean update(Ticket t) {
        String sql = "UPDATE Ticket SET TenPhim=?, SuatChieu=?, Ghe=?, GiaVe=?, MoTa=? WHERE MaVe=?";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTenPhim());
            ps.setString(2, t.getSuatChieu());
            ps.setString(3, t.getGhe());
            ps.setDouble(4, t.getGiaVe());
            ps.setString(5, t.getMoTa());
            ps.setInt(6, t.getMaVe());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa vé
    public boolean delete(int maVe) {
        String sql = "DELETE FROM Ticket WHERE MaVe=?";
        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maVe);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm vé theo mã
    public Ticket getByMaVe(int maVe) {
        String sql = "SELECT * FROM Ticket WHERE MaVe = ?";
        
        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, maVe);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Ticket t = new Ticket();
                t.setMaVe(rs.getInt("MaVe"));
                t.setTenPhim(rs.getString("TenPhim"));
                t.setSuatChieu(rs.getString("SuatChieu"));
                t.setGhe(rs.getString("Ghe"));
                t.setGiaVe(rs.getDouble("GiaVe"));
                t.setMoTa(rs.getString("MoTa"));
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra kết nối
    public boolean testConnection() {
        try (Connection con = ConnectSQL.getConnection()) {
            return con != null && !con.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}