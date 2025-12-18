/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.hoadon;
import CRUD.ConnectSQL;

/**
 *
 * @author Windows
 */
public class HoaDonDAO {
    
    // Lấy tất cả hoá đơn
    public List<hoadon> getAllHoaDon() {
        List<hoadon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon ORDER BY NgayDat DESC";
//            String sql = "SELECT * FROM HoaDon ";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                hoadon hd = new hoadon(
                    rs.getInt("MaHoaDon"),
                    rs.getInt("MaNguoiDung"),
                    rs.getInt("MaVe"),
                    rs.getInt("MaCombo"),
                    rs.getDate("NgayDat"),
                    rs.getDouble("TongTien"),
                    rs.getString("PhuongThucThanhToan")
                );
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm hoá đơn mới
   public boolean addHoaDon(hoadon hd) {
    String sql = "INSERT INTO HoaDon (MaNguoiDung, MaVe, MaCombo, NgayDat, TongTien, PhuongThucThanhToan) VALUES (?, ?, ?, ?, ?, ?)";
    
    System.out.println("=== DEBUG HoaDonDAO.addHoaDon ==="); // THÊM DÒNG NÀY
    System.out.println("SQL: " + sql); // THÊM DÒNG NÀY
    
    try (Connection conn = ConnectSQL.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        // Debug log các giá trị
        System.out.println("Giá trị hóa đơn:"); // THÊM DÒNG NÀY
        System.out.println("- MaNguoiDung: " + hd.getMaNguoiDung()); // THÊM DÒNG NÀY
        System.out.println("- MaVe: " + hd.getMaVe()); // THÊM DÒNG NÀY
        System.out.println("- MaCombo: " + hd.getMaCombo()); // THÊM DÒNG NÀY
        System.out.println("- NgayDat: " + hd.getNgayDat()); // THÊM DÒNG NÀY
        System.out.println("- TongTien: " + hd.getTongTien()); // THÊM DÒNG NÀY
        System.out.println("- PTTT: " + hd.getPhuongThucThanhToan()); // THÊM DÒNG NÀY
        
        stmt.setInt(1, hd.getMaNguoiDung());
        stmt.setInt(2, hd.getMaVe());
        stmt.setInt(3, hd.getMaCombo());
        stmt.setDate(4, new java.sql.Date(hd.getNgayDat().getTime()));
        stmt.setDouble(5, hd.getTongTien());
        stmt.setString(6, hd.getPhuongThucThanhToan());
        
        int result = stmt.executeUpdate();
        System.out.println("Rows affected: " + result); // THÊM DÒNG NÀY
        System.out.println("=== END DEBUG ==="); // THÊM DÒNG NÀY
        return result > 0;
        
    } catch (SQLException e) {
        System.err.println("SQL Error: " + e.getMessage()); // THÊM DÒNG NÀY
        e.printStackTrace();
        return false;
    }
}
    
    // Xoá hoá đơn
    public boolean deleteHoaDon(int maHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, maHoaDon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm hoá đơn theo mã
    public hoadon findHoaDonByMa(int maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        hoadon hd = null;
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                hd = new hoadon(
                    rs.getInt("MaHoaDon"),
                    rs.getInt("MaNguoiDung"),
                    rs.getInt("MaVe"),
                    rs.getInt("MaCombo"),
                    rs.getDate("NgayDat"),
                    rs.getDouble("TongTien"),
                    rs.getString("PhuongThucThanhToan")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hd;
    }
    
    // Lấy mã hoá đơn cuối cùng
    public int getLastMaHoaDon() {
        String sql = "SELECT MAX(MaHoaDon) as LastID FROM HoaDon";
        int lastID = 0;
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                lastID = rs.getInt("LastID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastID;
    }
    
    // Tìm kiếm hoá đơn theo từ khóa (tìm trong mã hoá đơn, mã người dùng, phương thức thanh toán)
    public List<hoadon> searchHoaDon(String keyword) {
        List<hoadon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE " +
                     "CAST(MaHoaDon AS VARCHAR) LIKE ? OR " +
                     "CAST(MaNguoiDung AS VARCHAR) LIKE ? OR " +
                     "PhuongThucThanhToan LIKE ? " +
                     "ORDER BY NgayDat DESC";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchTerm = "%" + keyword + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                hoadon hd = new hoadon(
                    rs.getInt("MaHoaDon"),
                    rs.getInt("MaNguoiDung"),
                    rs.getInt("MaVe"),
                    rs.getInt("MaCombo"),
                    rs.getDate("NgayDat"),
                    rs.getDouble("TongTien"),
                    rs.getString("PhuongThucThanhToan")
                );
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}