/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import model.hoadon;
import CRUD.HoaDonCRUD;
import CRUD.TicketCRUD;
import CRUD.ComboCRUD;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 *
 * @author ADMIN
 */
public class QuanLyHoaDonForm extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuanLyHoaDonForm.class.getName());
    
    private HoaDonCRUD hoaDonCRUD;
    private TicketCRUD ticketCRUD;
    private ComboCRUD comboCRUD;
    private DefaultTableModel tableModel;
    
    private List<Object[]> danhSachVe;
    private List<Object[]> danhSachCombo;
    
    public QuanLyHoaDonForm() {
        initComponents();
        hoaDonCRUD = new HoaDonCRUD();
        ticketCRUD = new TicketCRUD();
        comboCRUD = new ComboCRUD();
        gancn();
        setupTable();
        initData();
        loadDataFromDatabase();
          this.pack();
    this.setLocationRelativeTo(null);
     setIconImage(new ImageIcon(getClass().getResource("/image/ava-01.png")).getImage());
    FlatSVGIcon iconAdmin = new FlatSVGIcon("image/admin.svg", 50, 50);
        lbAdmin.setIcon(iconAdmin);
    }
    
    private String formatTien(double amount) {
        return String.format("%,.0f", amount);
    }
    
    private void initData() {
        // Load dữ liệu vé và combo từ database
        loadVeDataFromDB();
        loadComboDataFromDB();
        
        // Đặt ngày lập mặc định
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtNgaylap.setText(sdf.format(new Date()));
        
        // Tạo mã hoá đơn mới
        generateMaHoaDon();
        
        // Khởi tạo danh sách nếu null
        if (danhSachVe == null) danhSachVe = new ArrayList<>();
        if (danhSachCombo == null) danhSachCombo = new ArrayList<>();
        
        // Thêm sự kiện tính tổng tiền
        comboVe.addActionListener(e -> tinhTongTien());
        comboCombo.addActionListener(e -> tinhTongTien());
        
        // Thêm sự kiện tìm kiếm real-time
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiemHoaDonRealTime();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiemHoaDonRealTime();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiemHoaDonRealTime();
            }
        });
        
        // Khởi tạo combobox phương thức thanh toán
        initPhuongThucThanhToan();
        
        // Set mã hoá đơn không cho edit
        txtMahoadon.setEditable(false);
        
        // Tính tổng tiền ban đầu
        tinhTongTien();
    }

    private void initPhuongThucThanhToan() {
        jComboBox3.removeAllItems();
        jComboBox3.addItem("Tiền mặt");
        jComboBox3.addItem("Chuyển khoản");
        jComboBox3.addItem("Thẻ tín dụng");
        jComboBox3.addItem("Ví điện tử");
    }

    // Load dữ liệu vé từ database
    private void loadVeDataFromDB() {
        comboVe.removeAllItems();
        danhSachVe = ticketCRUD.getAllVe();
        
        if (danhSachVe != null && !danhSachVe.isEmpty()) {
            for (Object[] ve : danhSachVe) {
                int maVe = (int) ve[0];
                String tenVe = (String) ve[1];
                double giaVe = (double) ve[2];
                
                String displayText = String.format("%s - %s", tenVe, formatTien(giaVe));
                comboVe.addItem(displayText);
            }
        } else {
            comboVe.addItem("Không có dữ liệu vé");
        }
    }

    // Load dữ liệu combo từ database
    private void loadComboDataFromDB() {
        comboCombo.removeAllItems();
        danhSachCombo = comboCRUD.getAllCombo();
        
        if (danhSachCombo != null && !danhSachCombo.isEmpty()) {
            for (Object[] combo : danhSachCombo) {
                int maCombo = (int) combo[0];
                String tenCombo = (String) combo[1];
                double giaCombo = (double) combo[2];
                
                String displayText = String.format("%s - %s", tenCombo, formatTien(giaCombo));
                comboCombo.addItem(displayText);
            }
        } else {
            comboCombo.addItem("Không có dữ liệu combo");
        }
    }

    // Phương thức tính tổng tiền tự động
    private void tinhTongTien() {
        int selectedVeIndex = comboVe.getSelectedIndex();
        int selectedComboIndex = comboCombo.getSelectedIndex();
        
        double tongTien = 0;
        
        // Tính giá vé nếu có chọn và không phải là item "Không có dữ liệu"
        if (selectedVeIndex >= 0 && danhSachVe != null && !danhSachVe.isEmpty() 
            && !comboVe.getSelectedItem().toString().equals("Không có dữ liệu vé")) {
            try {
                int maVe = (int) danhSachVe.get(selectedVeIndex)[0];
                double giaVe = ticketCRUD.getGiaVe(maVe);
                tongTien += giaVe;
            } catch (Exception e) {
                System.err.println("Lỗi khi lấy giá vé: " + e.getMessage());
            }
        }
        
        // Tính giá combo nếu có chọn và không phải là item "Không có dữ liệu"
        if (selectedComboIndex >= 0 && danhSachCombo != null && !danhSachCombo.isEmpty()
            && !comboCombo.getSelectedItem().toString().equals("Không có dữ liệu combo")) {
            try {
                int maCombo = (int) danhSachCombo.get(selectedComboIndex)[0];
                double giaCombo = comboCRUD.getGiaCombo(maCombo);
                tongTien += giaCombo;
            } catch (Exception e) {
                System.err.println("Lỗi khi lấy giá combo: " + e.getMessage());
            }
        }
        
        txtTongtien.setText(formatTien(tongTien));
    }

    // Phương thức tìm kiếm real-time
    private void timKiemHoaDonRealTime() {
        String keyword = txtTimKiem.getText().trim();
        
        if (keyword.isEmpty()) {
            loadDataFromDatabase(); // Load lại toàn bộ dữ liệu nếu không có từ khóa
            return;
        }
        
        try {
            ArrayList<hoadon> ketQuaTimKiem = hoaDonCRUD.timKiemHoaDonTheoKeyword(keyword);
            hienThiDanhSachHoaDon(ketQuaTimKiem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataFromDatabase() {
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        ArrayList<hoadon> danhSachHoaDon = hoaDonCRUD.getDshoadon();
            List<hoadon> danhSachHoaDon = hoaDonCRUD.getDshoadon();

        
        if (danhSachHoaDon != null) {
            for (hoadon hd : danhSachHoaDon) {
                tableModel.addRow(new Object[]{
                    hd.getMaHoaDon(),
                    hd.getMaNguoiDung(),
                    sdf.format(hd.getNgayDat()),
                    formatTien(hd.getTongTien()),
                    hd.getPhuongThucThanhToan()
                });
            }
        }
        tbHoadon.setModel(tableModel);
    }

    private void generateMaHoaDon() {
        try {
            int lastID = hoaDonCRUD.getLastMaHoaDon();
            txtMahoadon.setText(String.valueOf(lastID + 1));
        } catch (Exception e) {
            txtMahoadon.setText("1"); // Mặc định nếu có lỗi
        }
    }
    
    private void setupTable() {
        String[] columnNames = {"Mã hoá đơn", "Mã người dùng", "Ngày lập", "Tổng tiền", "Phương thức TT"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbHoadon.setModel(tableModel);
        
        // Thêm sự kiện click cho bảng
        tbHoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
    }
    
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tbHoadon.getSelectedRow();
        
        if (selectedRow >= 0) {
            try {
                // Lấy thông tin từ bảng
                int maHoaDon = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                int maNguoiDung = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
                String ngayLap = tableModel.getValueAt(selectedRow, 2).toString();
                String tongTien = tableModel.getValueAt(selectedRow, 3).toString();
                String phuongThucTT = tableModel.getValueAt(selectedRow, 4).toString();
                
                // Hiển thị thông tin lên các ô
                txtMahoadon.setText(String.valueOf(maHoaDon));
                txtManguoidung.setText(String.valueOf(maNguoiDung));
                txtNgaylap.setText(ngayLap);
                txtTongtien.setText(tongTien);
                jComboBox3.setSelectedItem(phuongThucTT);
                
                // Tìm và hiển thị thông tin vé và combo từ database
                hoadon hd = hoaDonCRUD.timKiemHoaDon(maHoaDon);
                if (hd != null) {
                    setSelectedVe(hd.getMaVe());
                    setSelectedCombo(hd.getMaCombo());
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị thông tin hoá đơn: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private boolean validateInput() {
        if (txtManguoidung.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Kiểm tra nếu chọn item "Không có dữ liệu"
        if (comboVe.getSelectedItem().toString().equals("Không có dữ liệu vé") || 
            comboCombo.getSelectedItem().toString().equals("Không có dữ liệu combo")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé và combo hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Integer.parseInt(txtManguoidung.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã người dùng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void taoHoaDon() {
        if (!validateInput()) {
            return;
        }
        
        try {
            // Lấy mã vé và mã combo từ danh sách
            int selectedVeIndex = comboVe.getSelectedIndex();
            int selectedComboIndex = comboCombo.getSelectedIndex();
            
            int maVe = (int) danhSachVe.get(selectedVeIndex)[0];
            int maCombo = (int) danhSachCombo.get(selectedComboIndex)[0];
            int maNguoiDung = Integer.parseInt(txtManguoidung.getText().trim());
            String phuongThucTT = jComboBox3.getSelectedItem().toString();
            
            // Lấy giá từ database để tính tổng tiền
            double giaVe = ticketCRUD.getGiaVe(maVe);
            double giaCombo = comboCRUD.getGiaCombo(maCombo);
            double tongTien = giaVe + giaCombo;
            
            // Tạo đối tượng hoá đơn mới
            hoadon hoaDonMoi = new hoadon();
            hoaDonMoi.setMaNguoiDung(maNguoiDung);
            hoaDonMoi.setMaVe(maVe);
            hoaDonMoi.setMaCombo(maCombo);
            hoaDonMoi.setNgayDat(new Date());
            hoaDonMoi.setTongTien(tongTien);
            hoaDonMoi.setPhuongThucThanhToan(phuongThucTT);
            
            // Gọi CRUD để thêm
            hoaDonCRUD.themHoaDon(hoaDonMoi);
            
            JOptionPane.showMessageDialog(this, "Tạo hoá đơn thành công!\nTổng tiền: " + formatTien(tongTien), 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadDataFromDatabase();
            huyHoaDon();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void timKiemHoaDon() {
        String keyword = txtTimKiem.getText().trim();
        
        if (keyword.isEmpty()) {
            loadDataFromDatabase(); // Load lại toàn bộ dữ liệu nếu không có từ khóa
            return;
        }
        
        try {
            ArrayList<hoadon> ketQuaTimKiem = hoaDonCRUD.timKiemHoaDonTheoKeyword(keyword);
            hienThiDanhSachHoaDon(ketQuaTimKiem);
            
            if (ketQuaTimKiem.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hoá đơn phù hợp với từ khóa: " + keyword, 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức hiển thị danh sách hoá đơn
    private void hienThiDanhSachHoaDon(ArrayList<hoadon> danhSachHoaDon) {
        tableModel.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (danhSachHoaDon != null) {
            for (hoadon hd : danhSachHoaDon) {
                tableModel.addRow(new Object[]{
                    hd.getMaHoaDon(),
                    hd.getMaNguoiDung(),
                    sdf.format(hd.getNgayDat()),
                    formatTien(hd.getTongTien()),
                    hd.getPhuongThucThanhToan()
                });
            }
        }
    }

    private void hienThiThongTinHoaDon(hoadon hd) {
        txtMahoadon.setText(String.valueOf(hd.getMaHoaDon()));
        txtManguoidung.setText(String.valueOf(hd.getMaNguoiDung()));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtNgaylap.setText(sdf.format(hd.getNgayDat()));
        
        txtTongtien.setText(formatTien(hd.getTongTien()));
        jComboBox3.setSelectedItem(hd.getPhuongThucThanhToan());
        
        // Tìm và chọn vé/combo tương ứng
        setSelectedVe(hd.getMaVe());
        setSelectedCombo(hd.getMaCombo());
    }
    
    // Tìm và chọn vé trong combobox
    private void setSelectedVe(int maVe) {
        boolean found = false;
        for (int i = 0; i < danhSachVe.size(); i++) {
            if ((int) danhSachVe.get(i)[0] == maVe) {
                comboVe.setSelectedIndex(i);
                found = true;
                break;
            }
        }
        if (!found && comboVe.getItemCount() > 0) {
            comboVe.setSelectedIndex(0);
        }
    }
    
    // Tìm và chọn combo trong combobox
    private void setSelectedCombo(int maCombo) {
        boolean found = false;
        for (int i = 0; i < danhSachCombo.size(); i++) {
            if ((int) danhSachCombo.get(i)[0] == maCombo) {
                comboCombo.setSelectedIndex(i);
                found = true;
                break;
            }
        }
        if (!found && comboCombo.getItemCount() > 0) {
            comboCombo.setSelectedIndex(0);
        }
    }
   
    private void xoaHoaDon() {
        int selectedRow = tbHoadon.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn cần xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int maHoaDon = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xoá hoá đơn này?", 
                "Xác nhận xoá", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                hoaDonCRUD.xoaHoaDon(maHoaDon);
                JOptionPane.showMessageDialog(this, "Xoá hoá đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataFromDatabase();
                huyHoaDon();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void huyHoaDon() {
        txtManguoidung.setText("");
        txtTongtien.setText("");
        txtTimKiem.setText("");
        
        // Đặt lại ngày lập về hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtNgaylap.setText(sdf.format(new Date()));
        
        // Reset combobox về index 0 nếu có dữ liệu
        if (comboVe.getItemCount() > 0 && !comboVe.getItemAt(0).equals("Không có dữ liệu vé")) {
            comboVe.setSelectedIndex(0);
        }
        if (comboCombo.getItemCount() > 0 && !comboCombo.getItemAt(0).equals("Không có dữ liệu combo")) {
            comboCombo.setSelectedIndex(0);
        }
        if (jComboBox3.getItemCount() > 0) {
            jComboBox3.setSelectedIndex(0);
        }
        
        generateMaHoaDon();
        tbHoadon.clearSelection();
        tinhTongTien();
        
        // Load lại toàn bộ dữ liệu
        loadDataFromDatabase();
    }
    
    // Phần còn lại của code (gancn và initComponents) giữ nguyên...
    
    public void gancn() {

    // ======= 1. Phim & Suất chiếu =======
    txtPhimVaSuatChieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    txtPhimVaSuatChieu.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            txtPhimVaSuatChieu.setForeground(java.awt.Color.RED);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            txtPhimVaSuatChieu.setForeground(java.awt.Color.WHITE);
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            PhimVaSuatChieuForm form = new PhimVaSuatChieuForm();
            form.setVisible(true);
            dispose();
        }
    });


    // ======= 2. Vé & Ghế ngồi =======
    txtVeVaGheNgoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    txtVeVaGheNgoi.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            txtVeVaGheNgoi.setForeground(java.awt.Color.RED);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            txtVeVaGheNgoi.setForeground(java.awt.Color.WHITE);
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            VeVaGheNgoiForm form = new VeVaGheNgoiForm();
            form.setVisible(true);
            dispose();
        }
    });


    // ======= 3. Đồ ăn & Hóa đơn =======
    txtDoAnHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    txtDoAnHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            txtDoAnHoaDon.setForeground(java.awt.Color.RED);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            txtDoAnHoaDon.setForeground(java.awt.Color.WHITE);
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            DoAnHoaDonForm form = new DoAnHoaDonForm();
            form.setVisible(true);
            dispose();
        }
    });


    // ======= 4. Thống kê & Báo cáo =======
    txtThongKeBaoCao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    txtThongKeBaoCao.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            txtThongKeBaoCao.setForeground(java.awt.Color.RED);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            txtThongKeBaoCao.setForeground(java.awt.Color.WHITE);
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            ThongKeBaoCaoForm form = new ThongKeBaoCaoForm();
            form.setVisible(true);
            dispose();
        }
    });


    // ======= 5. Quản trị hệ thống =======
    txtQuanTriHeThong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    txtQuanTriHeThong.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            txtQuanTriHeThong.setForeground(java.awt.Color.RED);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            txtQuanTriHeThong.setForeground(java.awt.Color.WHITE);
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            QuanTriHeThonggForm form = new QuanTriHeThonggForm();
            form.setVisible(true);
            dispose();
        }
    });
    
    //Dashboard
    txtDashboard1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // hiện bàn tay

txtDashboard1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        txtDashboard1.setForeground(java.awt.Color.RED); // hover đỏ
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        txtDashboard1.setForeground(java.awt.Color.WHITE); // rời chuột trắng lại
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // Quay lại form TrangChuForm (dashboard)
        TrangChuForm home = new TrangChuForm();
        home.setLocationRelativeTo(null); // hiển thị giữa màn hình
        home.setVisible(true);
        dispose(); // đóng form hiện tại
    }
});
}
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtPhimVaSuatChieu = new javax.swing.JLabel();
        txtVeVaGheNgoi = new javax.swing.JLabel();
        txtDoAnHoaDon = new javax.swing.JLabel();
        txtThongKeBaoCao = new javax.swing.JLabel();
        txtQuanTriHeThong = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        txtDashboard1 = new javax.swing.JLabel();
        lbAdmin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        btnTimKiem = new javax.swing.JButton();
        txtNgaylap = new javax.swing.JTextField();
        txtTongtien = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        txtManguoidung = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboVe = new javax.swing.JComboBox<>();
        comboCombo = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnTaohoadon = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMahoadon = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHoadon = new javax.swing.JTable();
        jSeparator17 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel4.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel9.setFont(new java.awt.Font("UTM Amerika Sans", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("myGalaxyCinema");

        txtPhimVaSuatChieu.setFont(new java.awt.Font("SVN-Gilroy SemiBold", 1, 14)); // NOI18N
        txtPhimVaSuatChieu.setForeground(new java.awt.Color(255, 255, 255));
        txtPhimVaSuatChieu.setText("Phim và suất chiếu");

        txtVeVaGheNgoi.setFont(new java.awt.Font("SVN-Gilroy SemiBold", 1, 14)); // NOI18N
        txtVeVaGheNgoi.setForeground(new java.awt.Color(255, 255, 255));
        txtVeVaGheNgoi.setText("Vé và ghế ngồi");

        txtDoAnHoaDon.setFont(new java.awt.Font("SVN-Gilroy SemiBold", 1, 14)); // NOI18N
        txtDoAnHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        txtDoAnHoaDon.setText("Đồ ăn và hóa đơn");

        txtThongKeBaoCao.setFont(new java.awt.Font("SVN-Gilroy SemiBold", 1, 14)); // NOI18N
        txtThongKeBaoCao.setForeground(new java.awt.Color(255, 255, 255));
        txtThongKeBaoCao.setText("Thống kê & báo cáo");

        txtQuanTriHeThong.setFont(new java.awt.Font("SVN-Gilroy SemiBold", 1, 14)); // NOI18N
        txtQuanTriHeThong.setForeground(new java.awt.Color(255, 255, 255));
        txtQuanTriHeThong.setText("Quản trị hệ thống");

        txtDashboard1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtDashboard1.setForeground(new java.awt.Color(255, 255, 255));
        txtDashboard1.setText("Dashboard");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhimVaSuatChieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVeVaGheNgoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoAnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThongKeBaoCao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuanTriHeThong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel9)
                .addGap(57, 57, 57)
                .addComponent(txtDashboard1)
                .addGap(26, 26, 26)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPhimVaSuatChieu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVeVaGheNgoi)
                .addGap(12, 12, 12)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDoAnHoaDon)
                .addGap(14, 14, 14)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThongKeBaoCao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtQuanTriHeThong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);
        jPanel5.setBounds(-10, -10, 190, 510);
        jPanel4.add(jSeparator1);
        jSeparator1.setBounds(180, 130, 0, 3);
        jPanel4.add(jSeparator14);
        jSeparator14.setBounds(160, 120, 640, 10);

        btnTimKiem.setBackground(new java.awt.Color(153, 0, 51));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel4.add(btnTimKiem);
        btnTimKiem.setBounds(670, 80, 110, 30);

        txtNgaylap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgaylapActionPerformed(evt);
            }
        });
        jPanel4.add(txtNgaylap);
        txtNgaylap.setBounds(320, 270, 100, 30);

        txtTongtien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongtienActionPerformed(evt);
            }
        });
        jPanel4.add(txtTongtien);
        txtTongtien.setBounds(320, 310, 340, 30);

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        jPanel4.add(txtTimKiem);
        txtTimKiem.setBounds(340, 80, 310, 30);
        jPanel4.add(txtManguoidung);
        txtManguoidung.setBounds(550, 150, 110, 30);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 46, 81));
        jLabel1.setText("THÔNG TIN HOÁ ĐƠN");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(380, 120, 210, 25);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 46, 81));
        jLabel2.setText("DANH SÁCH HOÁ ĐƠN");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(190, 350, 250, 30);

        comboVe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(comboVe);
        comboVe.setBounds(320, 190, 340, 30);

        comboCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboComboActionPerformed(evt);
            }
        });
        jPanel4.add(comboCombo);
        comboCombo.setBounds(320, 230, 340, 30);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel4.add(jComboBox3);
        jComboBox3.setBounds(500, 270, 160, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 46, 81));
        jLabel3.setText("TÌM HOÁ ĐƠN");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(200, 80, 130, 30);

        btnTaohoadon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaohoadon.setText("Tạo hoá đơn");
        btnTaohoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaohoadonActionPerformed(evt);
            }
        });
        jPanel4.add(btnTaohoadon);
        btnTaohoadon.setBounds(670, 170, 110, 40);

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuy.setText("Huỷ");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel4.add(btnHuy);
        btnHuy.setBounds(670, 290, 110, 40);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoa);
        btnXoa.setBounds(670, 230, 110, 40);
        jPanel4.add(jSeparator15);
        jSeparator15.setBounds(170, 340, 640, 0);
        jPanel4.add(jSeparator16);
        jSeparator16.setBounds(170, 60, 640, 10);

        jLabel7.setBackground(new java.awt.Color(153, 0, 51));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Mã người dùng");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel7.setFocusTraversalPolicyProvider(true);
        jLabel7.setOpaque(true);
        jPanel4.add(jLabel7);
        jLabel7.setBounds(430, 150, 110, 30);

        jLabel8.setBackground(new java.awt.Color(153, 0, 51));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Chọn vé");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setFocusTraversalPolicyProvider(true);
        jLabel8.setOpaque(true);
        jPanel4.add(jLabel8);
        jLabel8.setBounds(190, 190, 110, 30);

        jLabel10.setBackground(new java.awt.Color(153, 0, 51));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Chọn combo");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setFocusTraversalPolicyProvider(true);
        jLabel10.setOpaque(true);
        jPanel4.add(jLabel10);
        jLabel10.setBounds(190, 230, 110, 30);

        jLabel11.setBackground(new java.awt.Color(153, 0, 51));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Ngày lập");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setFocusTraversalPolicyProvider(true);
        jLabel11.setOpaque(true);
        jPanel4.add(jLabel11);
        jLabel11.setBounds(190, 270, 110, 30);

        jLabel12.setBackground(new java.awt.Color(153, 0, 51));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PTTT");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setFocusTraversalPolicyProvider(true);
        jLabel12.setOpaque(true);
        jPanel4.add(jLabel12);
        jLabel12.setBounds(430, 270, 60, 30);

        jLabel13.setBackground(new java.awt.Color(153, 0, 51));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Mã hoá đơn");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.setFocusTraversalPolicyProvider(true);
        jLabel13.setOpaque(true);
        jPanel4.add(jLabel13);
        jLabel13.setBounds(190, 150, 110, 30);

        jLabel14.setBackground(new java.awt.Color(153, 0, 51));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("QUẢN LÝ HOÁ ĐƠN");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel14.setFocusTraversalPolicyProvider(true);
        jLabel14.setOpaque(true);
        jPanel4.add(jLabel14);
        jLabel14.setBounds(220, 10, 550, 40);

        jLabel15.setBackground(new java.awt.Color(153, 0, 51));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Tổng tiền");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel15.setFocusTraversalPolicyProvider(true);
        jLabel15.setOpaque(true);
        jPanel4.add(jLabel15);
        jLabel15.setBounds(190, 310, 110, 30);

        txtMahoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMahoadonActionPerformed(evt);
            }
        });
        jPanel4.add(txtMahoadon);
        txtMahoadon.setBounds(320, 150, 100, 30);

        tbHoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbHoadon.setShowGrid(true);
        jScrollPane2.setViewportView(tbHoadon);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(190, 380, 600, 110);
        jPanel4.add(jSeparator17);
        jSeparator17.setBounds(160, 347, 640, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
         timKiemHoaDon();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtNgaylapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgaylapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgaylapActionPerformed

    private void txtTongtienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongtienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongtienActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void txtMahoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMahoadonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMahoadonActionPerformed

    private void btnTaohoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaohoadonActionPerformed
        // TODO add your handling code here:
        taoHoaDon();
    }//GEN-LAST:event_btnTaohoadonActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
          xoaHoaDon();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
          huyHoaDon();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void comboComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboComboActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
 try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new QuanLyHoaDonForm().setVisible(true));
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnTaohoadon;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> comboCombo;
    private javax.swing.JComboBox<String> comboVe;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JTable tbHoadon;
    private javax.swing.JLabel txtDashboard1;
    private javax.swing.JLabel txtDoAnHoaDon;
    private javax.swing.JTextField txtMahoadon;
    private javax.swing.JTextField txtManguoidung;
    private javax.swing.JTextField txtNgaylap;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongtien;
    private javax.swing.JLabel txtVeVaGheNgoi;
    // End of variables declaration//GEN-END:variables
}
