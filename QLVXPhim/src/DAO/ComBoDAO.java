package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import CRUD.ConnectSQL;
import model.combo;

public class ComBoDAO {
    
    // Lấy tất cả combo
    public ArrayList<combo> getAllCombos() {
        ArrayList<combo> list = new ArrayList<>();
        String sql = "SELECT * FROM Combo";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(new combo(
                    rs.getInt("MaCombo"),
                    rs.getString("TenCombo"),
                    rs.getString("MoTa"),
                    rs.getDouble("GiaBan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm combo mới
    public boolean addCombo(combo c) {
        String sql = "INSERT INTO Combo (TenCombo, MoTa, GiaBan) VALUES (?, ?, ?)";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getDesc());
            stmt.setDouble(3, c.getPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật combo
    public boolean updateCombo(combo c) {
        String sql = "UPDATE Combo SET TenCombo = ?, MoTa = ?, GiaBan = ? WHERE MaCombo = ?";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getDesc());
            stmt.setDouble(3, c.getPrice());
            stmt.setInt(4, c.getComboID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa combo
    public boolean deleteCombo(int maCombo) {
        String sql = "DELETE FROM Combo WHERE MaCombo = ?";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maCombo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm combo
    public ArrayList<combo> searchCombo(String keyword) {
        ArrayList<combo> list = new ArrayList<>();
        String sql = "SELECT * FROM Combo WHERE TenCombo LIKE ? OR MaCombo LIKE ?";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new combo(
                    rs.getInt("MaCombo"),
                    rs.getString("TenCombo"),
                    rs.getString("MoTa"),
                    rs.getDouble("GiaBan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public double getGiaCombo(int maCombo) {
        String sql = "SELECT GiaBan FROM Combo WHERE MaCombo = ?";
        double giaCombo = 0;
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, maCombo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                giaCombo = rs.getDouble("GiaBan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giaCombo;
    }
}