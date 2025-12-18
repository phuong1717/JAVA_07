/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import java.util.ArrayList;
import model.combo;
import DAO.ComBoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Windows
 */
public class ComboCRUD {
    private ComBoDAO comboDAO;
    
    public ComboCRUD() {
        this.comboDAO = new ComBoDAO();
    }
    
    public ArrayList<combo> getDsCombo(){
        return comboDAO.getAllCombos();
    }
    
    public double getGiaCombo(int maCombo) {
        if (maCombo <= 0) {
            throw new IllegalArgumentException("Mã combo không hợp lệ");
        }
        return comboDAO.getGiaCombo(maCombo);
    }
    
    // Thêm combo - có validate nghiệp vụ
    public boolean themCombo(combo c) {
        // Validate nghiệp vụ trước khi thêm
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên combo không được để trống");
        }
        if (c.getPrice() <= 0) {
            throw new IllegalArgumentException("Giá combo phải lớn hơn 0");
        }
        
        return comboDAO.addCombo(c);
    }
    
    // Sửa combo - có validate nghiệp vụ
    public boolean suaCombo(combo c) {
        // Validate nghiệp vụ
        if (c.getComboID() <= 0) {
            throw new IllegalArgumentException("Mã combo không hợp lệ");
        }
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên combo không được để trống");
        }
        if (c.getPrice() <= 0) {
            throw new IllegalArgumentException("Giá combo phải lớn hơn 0");
        }
        
        return comboDAO.updateCombo(c);
    }
    
    // Xóa combo - có validate nghiệp vụ
    public boolean xoaCombo(int maCombo) {
        if (maCombo <= 0) {
            throw new IllegalArgumentException("Mã combo không hợp lệ");
        }
        
        return comboDAO.deleteCombo(maCombo);
    }
    
    // Tìm kiếm combo
    public ArrayList<combo> timKiemCombo(String keyword) {
        return comboDAO.searchCombo(keyword);
    }
    
    // Kiểm tra combo có tồn tại không (nếu cần)
    public boolean isComboExists(int maCombo) {
        ArrayList<combo> ds = getDsCombo();
        for (combo c : ds) {
            if (c.getComboID() == maCombo) {
                return true;
            }
        }
        return false;
    }
    
    // Phương thức getAllCombo để lấy danh sách combo cho combobox
    public List<Object[]> getAllCombo() {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT MaCombo, TenCombo, GiaBan FROM Combo";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] combo = new Object[3];
                combo[0] = rs.getInt("MaCombo");
                combo[1] = rs.getString("TenCombo");
                combo[2] = rs.getDouble("GiaBan");
                result.add(combo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}