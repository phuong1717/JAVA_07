package DAO;

import CRUD.ConnectSQL;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    public User login(String username, String password) {
        String sql = "SELECT * FROM NguoiDung WHERE TenNguoiDung = ? AND MatKhau = ?";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("MaNguoiDung"),
                        rs.getString("TenNguoiDung"),
                        rs.getString("MatKhau"),
                        rs.getString("Email"),
                        rs.getString("SDT"),
                        rs.getString("VaiTro")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
