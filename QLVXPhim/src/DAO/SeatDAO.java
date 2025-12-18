package DAO;

import model.Seat;
import CRUD.ConnectSQL; // Sử dụng kết nối chung
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    // Sử dụng kết nối từ ConnectSQL
    private Connection connect() throws SQLException {
        return ConnectSQL.getConnection();
    }

    public List<Seat> getAll() {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM Ghe";
        
        try (Connection conn = connect();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            System.out.println("✅ Đang lấy dữ liệu ghế từ database...");
            
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setMaGhe(rs.getInt("MaGhe"));
                seat.setHangGhe(rs.getString("HangGhe"));
                seat.setSoGhe(rs.getInt("SoGhe"));
                seat.setSoHang(rs.getInt("SoHang"));
                seat.setLoaiGhe(rs.getString("LoaiGhe"));
                seat.setTrangThai(rs.getString("TrangThai"));
                list.add(seat);
            }
            System.out.println("✅ Lấy được " + list.size() + " ghế từ database");
            
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy dữ liệu ghế: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // QUAN TRỌNG: Sửa INSERT - KHÔNG truyền MaGhe vì nó là IDENTITY
    public boolean add(Seat seat) {
        String sql = "INSERT INTO Ghe (HangGhe, SoGhe, SoHang, LoaiGhe, TrangThai) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            System.out.println("✅ Đang thêm ghế vào database...");
            System.out.println("📝 Dữ liệu ghế: " + seat.getHangGhe() + ", " + seat.getSoGhe() + ", " + seat.getLoaiGhe() + ", " + seat.getTrangThai());
            
            // KHÔNG set MaGhe vì nó tự động tăng
            st.setString(1, seat.getHangGhe());
            st.setInt(2, seat.getSoGhe());
            st.setInt(3, seat.getSoHang());
            st.setString(4, seat.getLoaiGhe());
            st.setString(5, seat.getTrangThai());
            
            int result = st.executeUpdate();
            System.out.println("✅ Kết quả thêm ghế: " + result + " dòng được thêm");
            
            return result > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm ghế: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Seat seat) {
        String sql = "UPDATE Ghe SET HangGhe=?, SoGhe=?, SoHang=?, LoaiGhe=?, TrangThai=? WHERE MaGhe=?";
        
        try (Connection conn = connect();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            System.out.println("✅ Đang cập nhật ghế mã " + seat.getMaGhe());
            
            st.setString(1, seat.getHangGhe());
            st.setInt(2, seat.getSoGhe());
            st.setInt(3, seat.getSoHang());
            st.setString(4, seat.getLoaiGhe());
            st.setString(5, seat.getTrangThai());
            st.setInt(6, seat.getMaGhe());
            
            int result = st.executeUpdate();
            System.out.println("✅ Kết quả cập nhật ghế: " + result + " dòng được sửa");
            
            return result > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật ghế: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maGhe) {
        String sql = "DELETE FROM Ghe WHERE MaGhe=?";
        
        try (Connection conn = connect();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            System.out.println("✅ Đang xóa ghế mã " + maGhe);
            
            st.setInt(1, maGhe);
            int result = st.executeUpdate();
            System.out.println("✅ Kết quả xóa ghế: " + result + " dòng được xóa");
            
            return result > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa ghế: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Thêm phương thức tìm ghế theo mã
    public Seat getByMaGhe(int maGhe) {
        String sql = "SELECT * FROM Ghe WHERE MaGhe = ?";
        
        try (Connection conn = connect();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, maGhe);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Seat seat = new Seat();
                seat.setMaGhe(rs.getInt("MaGhe"));
                seat.setHangGhe(rs.getString("HangGhe"));
                seat.setSoGhe(rs.getInt("SoGhe"));
                seat.setSoHang(rs.getInt("SoHang"));
                seat.setLoaiGhe(rs.getString("LoaiGhe"));
                seat.setTrangThai(rs.getString("TrangThai"));
                return seat;
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm ghế: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}