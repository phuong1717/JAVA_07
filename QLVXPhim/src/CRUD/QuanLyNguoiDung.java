package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import model.User;
import java.util.ArrayList;

public class QuanLyNguoiDung {

    public void loadToTable(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM NguoiDung";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaNguoiDung"),
                    rs.getString("TenNguoiDung"),
                    rs.getString("Email"),
                    rs.getString("MatKhau"),
                    rs.getString("SDT"),
                    rs.getString("VaiTro")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User u) {
        String sql = "INSERT INTO NguoiDung (TenNguoiDung, MatKhau, Email, SDT, VaiTro) VALUES (?,?,?,?,?)";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSdt());
            ps.setString(5, u.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User u) {
        String sql = "UPDATE NguoiDung SET TenNguoiDung=?, MatKhau=?, Email=?, SDT=?, VaiTro=? WHERE MaNguoiDung=?";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSdt());
            ps.setString(5, u.getRole());
            ps.setInt(6, u.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM NguoiDung WHERE MaNguoiDung=?";

        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
public boolean existsUsername(String username) {
    String sql = "SELECT COUNT(*) FROM NguoiDung WHERE TenNguoiDung = ?";

    try (Connection con = ConnectSQL.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public boolean existsUsernameExceptId(String username, int id) {
    String sql = "SELECT COUNT(*) FROM NguoiDung WHERE TenNguoiDung = ? AND MaNguoiDung != ?";

    try (Connection con = ConnectSQL.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);
        ps.setInt(2, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;   // true = đã tồn tại username khác trùng
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


public ArrayList<User> getAllUsers() {
    ArrayList<User> list = new ArrayList<>();
    String sql = "SELECT * FROM NguoiDung";

    try (Connection con = ConnectSQL.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new User(
                rs.getInt("MaNguoiDung"),
                rs.getString("TenNguoiDung"),
                rs.getString("MatKhau"),
                rs.getString("Email"),
                rs.getString("SDT"),
                rs.getString("VaiTro")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

   public ArrayList<User> searchUser(String keyword) {
    ArrayList<User> list = new ArrayList<>();
    String sql = "SELECT * FROM NguoiDung "
               + "WHERE TenNguoiDung LIKE ? "
               + "OR Email LIKE ? "
               + "OR SDT LIKE ? "
               + "OR VaiTro LIKE ?";

    try (Connection con = ConnectSQL.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");
        ps.setString(4, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new User(
                rs.getInt("MaNguoiDung"),
                rs.getString("TenNguoiDung"),
                rs.getString("MatKhau"),
                rs.getString("Email"),
                rs.getString("SDT"),
                rs.getString("VaiTro")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
   

}
