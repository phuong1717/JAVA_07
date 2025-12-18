package CRUD;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class ThongKeDoanhThu {

    // ------------------ VALIDATE NGÀY ------------------
    public LocalDate validateTuNgay(String tuNgayText) throws Exception {
        if (tuNgayText == null || tuNgayText.trim().isEmpty()) {
            throw new Exception("Ngày bắt đầu không được để trống!");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(tuNgayText.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new Exception("Ngày bắt đầu sai định dạng! (dd/MM/yyyy)");
        }
    }

    public LocalDate validateDenNgay(String denNgayText, LocalDate tuNgay) throws Exception {
        if (denNgayText == null || denNgayText.trim().isEmpty()) {
            throw new Exception("Ngày kết thúc không được để trống!");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate denNgay = LocalDate.parse(denNgayText.trim(), formatter);

            if (denNgay.isBefore(tuNgay)) {
                throw new Exception("Ngày kết thúc phải >= ngày bắt đầu!");
            }
            return denNgay;
        } catch (DateTimeParseException e) {
            throw new Exception("Ngày kết thúc sai định dạng! (dd/MM/yyyy)");
        }
    }

    // ------------------ THỐNG KÊ THEO PHIM ------------------
    public ArrayList<Object[]> thongKeDoanhThu(String tenPhim, java.util.Date tuNgay, java.util.Date denNgay) {
        ArrayList<Object[]> ketQua = new ArrayList<>();

        String sql = """
            SELECT 
                p.TenPhim,
                COUNT(t.MaVe) AS SoLuongVe,
                SUM(h.TongTien) AS DoanhThu
            FROM HoaDon h
            JOIN Ticket t ON h.MaVe = t.MaVe
            JOIN Phim p ON t.MaPhim = p.MaPhim
            WHERE h.NgayDat >= ? AND h.NgayDat < ?
        """;

        if (tenPhim != null && !tenPhim.trim().isEmpty()) {
            sql += " AND p.TenPhim LIKE ?";
        }

        sql += " GROUP BY p.TenPhim";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp start = new Timestamp(tuNgay.getTime());

            Calendar cal = Calendar.getInstance();
            cal.setTime(denNgay);
            cal.add(Calendar.DATE, 1);
            Timestamp endExclusive = new Timestamp(cal.getTimeInMillis());

            stmt.setTimestamp(1, start);
            stmt.setTimestamp(2, endExclusive);

            if (tenPhim != null && !tenPhim.trim().isEmpty()) {
                stmt.setString(3, "%" + tenPhim + "%");
            }

            System.out.println("QUERY DATE: " + start + " đến " + endExclusive);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ketQua.add(new Object[]{
                    rs.getString("TenPhim"),
                    rs.getInt("SoLuongVe"),
                    rs.getDouble("DoanhThu")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }


    // ------------------ TỔNG VÉ + TỔNG DOANH THU ------------------
    public Object[] layTongVeVaDoanhThu(String tenPhim, java.util.Date tuNgay, java.util.Date denNgay) {
        Object[] ketQua = new Object[2];

        String sql = """
                SELECT 
                    COUNT(*) AS TongVe,
                    SUM(TongTien) AS TongDoanhThu
                FROM HoaDon
                WHERE NgayDat >= ? AND NgayDat < ?
            """;

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp start = new Timestamp(tuNgay.getTime());

            Calendar cal = Calendar.getInstance();
            cal.setTime(denNgay);
            cal.add(Calendar.DATE, 1);
            Timestamp endExclusive = new Timestamp(cal.getTimeInMillis());

            stmt.setTimestamp(1, start);
            stmt.setTimestamp(2, endExclusive);

            System.out.println("QUERY TOTAL DATE: " + start + " đến " + endExclusive);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ketQua[0] = rs.getInt("TongVe");
                ketQua[1] = rs.getDouble("TongDoanhThu");
                if (rs.wasNull()) ketQua[1] = 0.0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thống kê: " + e.getMessage());
            ketQua[0] = 0;
            ketQua[1] = 0.0;
        }

        return ketQua;
    }
}
