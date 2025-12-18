package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Seat;

public class SeatCRUD {

    // === LẤY DANH SÁCH GHẾ ===
    public ArrayList<Seat> getDsSeat() {
        ArrayList<Seat> dsSeat = new ArrayList<>();
        String sql = "SELECT * FROM Ghe";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("✅ Đang lấy danh sách ghế từ database...");

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setMaGhe(rs.getInt("MaGhe"));
                seat.setHangGhe(rs.getString("HangGhe"));
                seat.setSoGhe(rs.getInt("SoGhe"));
                seat.setSoHang(rs.getInt("SoHang"));
                seat.setLoaiGhe(rs.getString("LoaiGhe"));
                seat.setTrangThai(rs.getString("TrangThai"));
                dsSeat.add(seat);
            }

            System.out.println("✅ Lấy được " + dsSeat.size() + " ghế từ database");

        } catch (SQLException ex) {
            System.out.println("❌ Lỗi khi lấy danh sách ghế: " + ex.getMessage());
            Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsSeat;
    }

    // === THÊM GHẾ (CÓ VALIDATION) ===
    public boolean themGhe(Seat seat) {
        // VALIDATION DỮ LIỆU
        if (seat.getHangGhe() == null || seat.getHangGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Hàng ghế không được để trống!");
        }
        if (seat.getSoGhe() <= 0) {
            throw new IllegalArgumentException("Số ghế phải lớn hơn 0!");
        }
        if (seat.getSoHang() <= 0) {
            throw new IllegalArgumentException("Số hàng phải lớn hơn 0!");
        }
        if (seat.getLoaiGhe() == null || seat.getLoaiGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Loại ghế không được để trống!");
        }
        if (seat.getTrangThai() == null || seat.getTrangThai().trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được để trống!");
        }

        String sql = "INSERT INTO Ghe (HangGhe, SoGhe, SoHang, LoaiGhe, TrangThai) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("✅ Đang thêm ghế vào database...");
            System.out.println("📝 Dữ liệu ghế: " + seat.getHangGhe() + ", Số ghế: " + seat.getSoGhe() + 
                             ", Loại: " + seat.getLoaiGhe() + ", Trạng thái: " + seat.getTrangThai());

            stmt.setString(1, seat.getHangGhe());
            stmt.setInt(2, seat.getSoGhe());
            stmt.setInt(3, seat.getSoHang());
            stmt.setString(4, seat.getLoaiGhe());
            stmt.setString(5, seat.getTrangThai());

            int result = stmt.executeUpdate();
            System.out.println("✅ Kết quả thêm ghế: " + result + " dòng được thêm");

            return result > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Lỗi khi thêm ghế: " + ex.getMessage());
            Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // === SỬA GHẾ (CÓ VALIDATION) ===
    public boolean suaGhe(Seat seat) {
        // Kiểm tra ghế có tồn tại không
        if (!kiemTraMaGheTonTai(seat.getMaGhe())) {
            throw new IllegalArgumentException("Ghế mã " + seat.getMaGhe() + " không tồn tại!");
        }

        // VALIDATION DỮ LIỆU
        if (seat.getHangGhe() == null || seat.getHangGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Hàng ghế không được để trống!");
        }
        if (seat.getSoGhe() <= 0) {
            throw new IllegalArgumentException("Số ghế phải lớn hơn 0!");
        }
        if (seat.getSoHang() <= 0) {
            throw new IllegalArgumentException("Số hàng phải lớn hơn 0!");
        }
        if (seat.getLoaiGhe() == null || seat.getLoaiGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Loại ghế không được để trống!");
        }
        if (seat.getTrangThai() == null || seat.getTrangThai().trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được để trống!");
        }

        String sql = "UPDATE Ghe SET HangGhe=?, SoGhe=?, SoHang=?, LoaiGhe=?, TrangThai=? WHERE MaGhe=?";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("✅ Đang cập nhật ghế mã " + seat.getMaGhe());

            stmt.setString(1, seat.getHangGhe());
            stmt.setInt(2, seat.getSoGhe());
            stmt.setInt(3, seat.getSoHang());
            stmt.setString(4, seat.getLoaiGhe());
            stmt.setString(5, seat.getTrangThai());
            stmt.setInt(6, seat.getMaGhe());

            int result = stmt.executeUpdate();
            System.out.println("✅ Kết quả cập nhật ghế: " + result + " dòng được sửa");

            return result > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Lỗi khi sửa ghế: " + ex.getMessage());
            Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // === XÓA GHẾ ===
    public boolean xoaGhe(int maGhe) {
        // Kiểm tra ghế có tồn tại không
        if (!kiemTraMaGheTonTai(maGhe)) {
            throw new IllegalArgumentException("Ghế mã " + maGhe + " không tồn tại!");
        }

        String sql = "DELETE FROM Ghe WHERE MaGhe=?";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("✅ Đang xóa ghế mã " + maGhe);

            stmt.setInt(1, maGhe);
            int result = stmt.executeUpdate();
            System.out.println("✅ Kết quả xóa ghế: " + result + " dòng được xóa");

            return result > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Lỗi khi xóa ghế: " + ex.getMessage());
            Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // === TÌM KIẾM GHẾ (ĐÃ SỬA LẠI) ===
   public ArrayList<Seat> timKiemGhe(String keyword) {
    ArrayList<Seat> ketQua = new ArrayList<>();
    
    if (keyword == null || keyword.trim().isEmpty()) {
        return getDsSeat(); // Trả về tất cả nếu keyword rỗng
    }

    String sql = "SELECT * FROM Ghe WHERE " +
                 "HangGhe LIKE ? OR " +
                 "LoaiGhe LIKE ? OR " +
                 "TrangThai LIKE ? OR " +
                 "CAST(MaGhe AS NVARCHAR(10)) LIKE ? OR " +
                 "CAST(SoGhe AS NVARCHAR(10)) LIKE ? OR " +
                 "CAST(SoHang AS NVARCHAR(10)) LIKE ?";

    try (Connection conn = ConnectSQL.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        String searchTerm = "%" + keyword + "%";
        System.out.println("🔍 Tìm kiếm ghế với từ khóa: '" + keyword + "'");
        
        stmt.setString(1, searchTerm);  // HangGhe
        stmt.setString(2, searchTerm);  // LoaiGhe
        stmt.setString(3, searchTerm);  // TrangThai
        stmt.setString(4, searchTerm);  // MaGhe
        stmt.setString(5, searchTerm);  // SoGhe
        stmt.setString(6, searchTerm);  // SoHang

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Seat seat = new Seat();
            seat.setMaGhe(rs.getInt("MaGhe"));
            seat.setHangGhe(rs.getString("HangGhe"));
            seat.setSoGhe(rs.getInt("SoGhe"));
            seat.setSoHang(rs.getInt("SoHang"));
            seat.setLoaiGhe(rs.getString("LoaiGhe"));
            seat.setTrangThai(rs.getString("TrangThai"));
            ketQua.add(seat);
        }

        System.out.println("✅ Tìm thấy " + ketQua.size() + " ghế phù hợp");

    } catch (SQLException ex) {
        System.out.println("❌ Lỗi khi tìm kiếm ghế: " + ex.getMessage());
        Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return ketQua;
}

    // === KIỂM TRA MÃ GHẾ ĐÃ TỒN TẠI CHƯA ===
    public boolean kiemTraMaGheTonTai(int maGhe) {
        String sql = "SELECT COUNT(*) FROM Ghe WHERE MaGhe = ?";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, maGhe);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException ex) {
            System.out.println("❌ Lỗi khi kiểm tra mã ghế: " + ex.getMessage());
            Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // === LẤY GHẾ THEO MÃ (THÊM MỚI) ===
    public Seat getGheByMa(int maGhe) {
        String sql = "SELECT * FROM Ghe WHERE MaGhe = ?";
        
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, maGhe);
            ResultSet rs = stmt.executeQuery();
            
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
            
        } catch (SQLException ex) {
            System.out.println("❌ Lỗi khi lấy ghế theo mã: " + ex.getMessage());
            Logger.getLogger(SeatCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}