package CRUD;

import java.util.ArrayList;
import DAO.HoaDonDAO;
import java.util.List;
import model.hoadon;

/**
 *
 * @author Windows
 */
public class HoaDonCRUD {
    private HoaDonDAO hoaDonDAO;
    
    public HoaDonCRUD() {
        this.hoaDonDAO = new HoaDonDAO();
    }
    
    public ArrayList<hoadon> getDshoadon(){
        return new ArrayList<>(hoaDonDAO.getAllHoaDon());
    }
    
    // Thêm hóa đơn - có validate nghiệp vụ
    public boolean themHoaDon(hoadon hd) {
        // Validate nghiệp vụ
        if (hd.getMaNguoiDung() <= 0) {
            throw new IllegalArgumentException("Mã người dùng không hợp lệ");
        }
        if (hd.getMaVe() <= 0) {
            throw new IllegalArgumentException("Mã vé không hợp lệ");
        }
        if (hd.getMaCombo() <= 0) {
            throw new IllegalArgumentException("Mã combo không hợp lệ");
        }
        if (hd.getTongTien() <= 0) {
            throw new IllegalArgumentException("Tổng tiền phải lớn hơn 0");
        }
        if (hd.getPhuongThucThanhToan() == null || hd.getPhuongThucThanhToan().trim().isEmpty()) {
            throw new IllegalArgumentException("Phương thức thanh toán không được để trống");
        }
        if (hd.getNgayDat() == null) {
            throw new IllegalArgumentException("Ngày đặt không hợp lệ");
        }
        
        return hoaDonDAO.addHoaDon(hd);
    }
    
    // Xóa hóa đơn - có validate nghiệp vụ
    public boolean xoaHoaDon(int maHoaDon) {
        if (maHoaDon <= 0) {
            throw new IllegalArgumentException("Mã hóa đơn không hợp lệ");
        }
        
        return hoaDonDAO.deleteHoaDon(maHoaDon);
    }
    
    // Tìm kiếm hóa đơn theo mã
    public hoadon timKiemHoaDon(int maHoaDon) {
        if (maHoaDon <= 0) {
            throw new IllegalArgumentException("Mã hóa đơn không hợp lệ");
        }
        
        return hoaDonDAO.findHoaDonByMa(maHoaDon);
    }
    
    // Tìm kiếm hóa đơn theo từ khóa
    public ArrayList<hoadon> timKiemHoaDonTheoKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getDshoadon();
        }
        return new ArrayList<>(hoaDonDAO.searchHoaDon(keyword.trim()));
    }
    
    // Lấy mã hóa đơn cuối cùng
    public int getLastMaHoaDon() {
        return hoaDonDAO.getLastMaHoaDon();
    }
    
    // Kiểm tra hóa đơn có tồn tại không
    public boolean isHoaDonExists(int maHoaDon) {
        ArrayList<hoadon> ds = getDshoadon();
        for (hoadon hd : ds) {
            if (hd.getMaHoaDon() == maHoaDon) {
                return true;
            }
        }
        return false;
    }
   

    

}