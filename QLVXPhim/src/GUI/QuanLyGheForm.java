/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import CRUD.SeatCRUD;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Seat;

/**
 *
 * @author ADMIN
 */
public class QuanLyGheForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuanLyGheForm.class.getName());
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private SeatCRUD seatCRUD;

    /**
     * Creates new form QuanLyGheForm
     */
    public QuanLyGheForm() {
        initComponents();
        gancn();
        initTableModel(); // KHỞI TẠO BẢNG TRƯỚC
        addEventListeners();
        
        // Khởi tạo SeatCRUD
        seatCRUD = new SeatCRUD();
        layDuLieu(); // SAU ĐÓ MỚI LOAD DỮ LIỆU
        
        // Ẩn ô nhập mã ghế vì nó tự động tăng
        txtMaghe.setEnabled(false);
        txtMaghe.setText("Auto");
    }
    
    private void initTableModel() {
        // TẠO TABLE MODEL VỚI CÁC CỘT NGAY TỪ ĐẦU
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã ghế", "Hàng ghế", "Số ghế", "Số hàng", "Loại ghế", "Trạng thái"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        
        tbVe.setModel(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        tbVe.setRowSorter(rowSorter);
    }
    
    private void layDuLieu(){
         setIconImage(new ImageIcon(getClass().getResource("/image/ava-01.png")).getImage());
        FlatSVGIcon iconAdmin = new FlatSVGIcon("image/admin.svg", 50, 50);
        lbAdmin.setIcon(iconAdmin);
        try {
            // XÓA DỮ LIỆU CŨ TRONG BẢNG (CHỈ DỮ LIỆU, KHÔNG XÓA CỘT)
            tableModel.setRowCount(0);
            
            // Lấy dữ liệu từ database
            ArrayList<Seat> dsSeat = seatCRUD.getDsSeat();
            
            if (dsSeat != null && !dsSeat.isEmpty()) {
                for (Seat s : dsSeat) {
                    // Xử lý trạng thái từ cột TrangThai
                    String trangThai = s.getTrangThai();

                    if (trangThai != null) {
                        switch (trangThai.toLowerCase()) {
                            case "1":
                            case "true":
                            case "active":
                            case "còn trống":
                                trangThai = "Còn trống";
                                break;
                            case "0":
                            case "false":
                            case "inactive":
                            case "đã bán":
                                trangThai = "Đã bán";
                                break;
                            case "đang chọn":
                                trangThai = "Đang chọn";
                                break;
                            case "tạm giữ":
                                trangThai = "Tạm giữ";
                                break;
                            case "đặt trước":
                                trangThai = "Đặt trước";
                                break;
                            default:
                                // giữ nguyên giá trị
                                break;
                        }
                    } else {
                        trangThai = "Không xác định";
                    }

                    // Thêm dữ liệu vào bảng
                    tableModel.addRow(new Object[]{
                        s.getMaGhe(),    // MaGhe
                        s.getHangGhe(),  // HangGhe
                        s.getSoGhe(),    // SoGhe
                        s.getSoHang(),   // SoHang
                        s.getLoaiGhe(),  // LoaiGhe
                        trangThai        // TrangThai
                    });
                }
                System.out.println("✅ Đã tải " + dsSeat.size() + " ghế lên bảng");
            } else {
                // Thêm hàng thông báo nếu không có dữ liệu
                tableModel.addRow(new Object[]{"", "", "", "", "", "Không có dữ liệu"});
                System.out.println("⚠️ Không có dữ liệu ghế");
            }
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Lỗi khi tải dữ liệu ghế", e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEventListeners() {
        // Thêm sự kiện cho nút Thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themGhe();
            }
        });

        // Thêm sự kiện cho nút Sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suaGhe();
            }
        });

        // Thêm sự kiện cho nút Xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaGhe();
            }
        });

        // Thêm sự kiện cho nút Tìm kiếm
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemGhe();
            }
        });

        // Thêm sự kiện cho nút Hủy
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // Thêm sự kiện cho bảng để chọn hàng
        tbVe.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbVe.getSelectedRow() != -1) {
                selectedRow = tbVe.getSelectedRow();
                hienThiDuLieuLenForm();
            }
        });
    }

    // === THÊM GHẾ ===
    private void themGhe() {
        try {
            // Lấy dữ liệu từ form - KHÔNG lấy mã ghế
            String hangGhe = comboHangghe.getSelectedItem().toString();
            String soGheStr = comboSoghe.getSelectedItem().toString();
            String soHangStr = comboSoghe.getSelectedItem().toString(); // Số hàng = Số ghế
            String loaiGhe = comboLoaighe.getSelectedItem().toString();
            String trangThai = comboTrangthai.getSelectedItem().toString();

            // VALIDATION
            if (hangGhe.isEmpty() || soGheStr.isEmpty() || loaiGhe.isEmpty() || trangThai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse dữ liệu
            int soGhe, soHang;
            try {
                soGhe = Integer.parseInt(soGheStr);
                soHang = Integer.parseInt(soHangStr);
                
                if (soGhe <= 0 || soHang <= 0) {
                    JOptionPane.showMessageDialog(this, "Số ghế và số hàng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số ghế và số hàng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng Seat - KHÔNG set mã ghế
            Seat seat = new Seat();
            seat.setHangGhe(hangGhe);
            seat.setSoGhe(soGhe);
            seat.setSoHang(soHang);
            seat.setLoaiGhe(loaiGhe);
            seat.setTrangThai(trangThai);

            // Gọi CRUD để thêm ghế
            boolean success = seatCRUD.themGhe(seat);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm ghế thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                layDuLieu(); // Load lại dữ liệu từ database
            } else {
                JOptionPane.showMessageDialog(this, "Thêm ghế thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Lỗi khi thêm ghế", e);
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm ghế: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === SỬA GHẾ ===
    private void suaGhe() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Lấy mã ghế từ dòng được chọn
            int modelRow = tbVe.convertRowIndexToModel(selectedRow);
            int maGhe = (int) tableModel.getValueAt(modelRow, 0);
            
            // Lấy dữ liệu từ form
            String hangGhe = comboHangghe.getSelectedItem().toString();
            String soGheStr = comboSoghe.getSelectedItem().toString();
            String soHangStr = comboSoghe.getSelectedItem().toString();
            String loaiGhe = comboLoaighe.getSelectedItem().toString();
            String trangThai = comboTrangthai.getSelectedItem().toString();

            // VALIDATION
            if (hangGhe.isEmpty() || soGheStr.isEmpty() || loaiGhe.isEmpty() || trangThai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse dữ liệu
            int soGhe, soHang;
            try {
                soGhe = Integer.parseInt(soGheStr);
                soHang = Integer.parseInt(soHangStr);
                
                if (soGhe <= 0 || soHang <= 0) {
                    JOptionPane.showMessageDialog(this, "Số ghế và số hàng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số ghế và số hàng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng Seat
            Seat seat = new Seat();
            seat.setMaGhe(maGhe);
            seat.setHangGhe(hangGhe);
            seat.setSoGhe(soGhe);
            seat.setSoHang(soHang);
            seat.setLoaiGhe(loaiGhe);
            seat.setTrangThai(trangThai);

            // Gọi CRUD để sửa ghế
            boolean success = seatCRUD.suaGhe(seat);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Sửa ghế thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                selectedRow = -1;
                layDuLieu(); // Load lại dữ liệu từ database
            } else {
                JOptionPane.showMessageDialog(this, "Sửa ghế thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Lỗi khi sửa ghế", e);
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa ghế: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === XÓA GHẾ ===
    private void xoaGhe() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Lấy mã ghế từ dòng được chọn
            int modelRow = tbVe.convertRowIndexToModel(selectedRow);
            int maGhe = (int) tableModel.getValueAt(modelRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa ghế mã " + maGhe + "?", "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Gọi CRUD để xóa ghế
                boolean success = seatCRUD.xoaGhe(maGhe);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa ghế thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    selectedRow = -1;
                    layDuLieu(); // Load lại dữ liệu từ database
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa ghế thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Lỗi khi xóa ghế", e);
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa ghế: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === TÌM KIẾM GHẾ ===
    private void timKiemGhe() {
        String tuKhoa = txtTimKiem.getText().trim();

        if (tuKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ArrayList<Seat> ketQua = seatCRUD.timKiemGhe(tuKhoa);

            // Xóa dữ liệu cũ trong table
            tableModel.setRowCount(0);

            if (ketQua != null && !ketQua.isEmpty()) {
                for (Seat s : ketQua) {
                    // Xử lý trạng thái giống như trong layDuLieu()
                    String trangThai = s.getTrangThai();
                    if (trangThai != null) {
                        switch (trangThai.toLowerCase()) {
                            case "1":
                            case "true":
                            case "active":
                            case "còn trống":
                                trangThai = "Còn trống";
                                break;
                            case "0":
                            case "false":
                            case "inactive":
                            case "đã bán":
                                trangThai = "Đã bán";
                                break;
                            case "đang chọn":
                                trangThai = "Đang chọn";
                                break;
                            case "tạm giữ":
                                trangThai = "Tạm giữ";
                                break;
                            case "đặt trước":
                                trangThai = "Đặt trước";
                                break;
                            default:
                                // giữ nguyên giá trị
                                break;
                        }
                    } else {
                        trangThai = "Không xác định";
                    }

                    tableModel.addRow(new Object[]{
                        s.getMaGhe(),
                        s.getHangGhe(),
                        s.getSoGhe(),
                        s.getSoHang(),
                        s.getLoaiGhe(),
                        trangThai
                    });
                }
                JOptionPane.showMessageDialog(this, "Tìm thấy " + ketQua.size() + " kết quả!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào cho từ khóa: " + tuKhoa, "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Lỗi khi tìm kiếm ghế", e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === RESET FORM ===
    private void resetForm() {
        clearForm();
        selectedRow = -1;
        
        // Reset bộ lọc tìm kiếm
        tbVe.setRowSorter(null);
        
        // Reset ô tìm kiếm
        txtTimKiem.setText("");
        
        // Hiển thị lại toàn bộ danh sách ghế
        layDuLieu();
        
        JOptionPane.showMessageDialog(this, "Đã reset form và hiển thị toàn bộ danh sách ghế!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // === HIỂN THỊ DỮ LIỆU LÊN FORM ===
    private void hienThiDuLieuLenForm() {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            try {
                // Lấy dữ liệu từ bảng
                int modelRow = tbVe.convertRowIndexToModel(selectedRow);
                int maGhe = (int) tableModel.getValueAt(modelRow, 0);
                String hangGhe = tableModel.getValueAt(modelRow, 1).toString();
                String soGhe = tableModel.getValueAt(modelRow, 2).toString();
                String soHang = tableModel.getValueAt(modelRow, 3).toString();
                String loaiGhe = tableModel.getValueAt(modelRow, 4).toString();
                String trangThai = tableModel.getValueAt(modelRow, 5).toString();

                // Hiển thị lên form
                txtMaghe.setText(String.valueOf(maGhe));
                comboHangghe.setSelectedItem(hangGhe);
                comboSoghe.setSelectedItem(soGhe);
                comboLoaighe.setSelectedItem(loaiGhe);
                comboTrangthai.setSelectedItem(trangThai);
                
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Lỗi khi hiển thị dữ liệu", e);
                JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === CLEAR FORM ===
    private void clearForm() {
        txtMaghe.setText("Auto");
        comboHangghe.setSelectedIndex(0);
        comboSoghe.setSelectedIndex(0);
        comboLoaighe.setSelectedIndex(0);
        comboTrangthai.setSelectedIndex(0);
        tbVe.clearSelection();
    }
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
        mm = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        comboHangghe = new javax.swing.JComboBox<>();
        txtMaghe = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        comboSoghe = new javax.swing.JComboBox<>();
        comboTrangthai = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        comboLoaighe = new javax.swing.JComboBox<>();
        btnHuy = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbVe = new javax.swing.JTable();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();

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
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(txtDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(156, 156, 156))
                    .addComponent(txtPhimVaSuatChieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVeVaGheNgoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoAnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThongKeBaoCao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuanTriHeThong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel9)
                .addGap(58, 58, 58)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtQuanTriHeThong)
                .addGap(12, 12, 12)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);
        jPanel5.setBounds(-10, -10, 190, 510);
        jPanel4.add(jSeparator1);
        jSeparator1.setBounds(180, 130, 0, 3);
        jPanel4.add(jSeparator14);
        jSeparator14.setBounds(180, 330, 640, 10);

        mm.setBackground(new java.awt.Color(153, 0, 51));
        mm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mm.setForeground(new java.awt.Color(255, 255, 255));
        mm.setText("Trạng thái");
        mm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmActionPerformed(evt);
            }
        });
        jPanel4.add(mm);
        mm.setBounds(470, 290, 110, 30);

        jButton3.setBackground(new java.awt.Color(153, 0, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("QUẢN LÝ GHẾ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);
        jButton3.setBounds(270, 0, 420, 40);

        jButton5.setBackground(new java.awt.Color(153, 0, 51));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Nhập ghế");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);
        jButton5.setBounds(230, 70, 110, 30);

        jButton7.setBackground(new java.awt.Color(153, 0, 51));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Loại ghế");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(230, 230, 110, 30);

        btnSua.setText("Sửa");
        jPanel4.add(btnSua);
        btnSua.setBounds(720, 180, 72, 30);

        btnXoa.setText("Xóa");
        jPanel4.add(btnXoa);
        btnXoa.setBounds(720, 230, 72, 30);

        comboHangghe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O" }));
        jPanel4.add(comboHangghe);
        comboHangghe.setBounds(360, 170, 330, 30);
        jPanel4.add(txtMaghe);
        txtMaghe.setBounds(360, 120, 100, 30);

        jButton8.setBackground(new java.awt.Color(153, 0, 51));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Hàng ghế");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8);
        jButton8.setBounds(230, 170, 110, 30);

        comboSoghe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        jPanel4.add(comboSoghe);
        comboSoghe.setBounds(360, 290, 72, 30);

        comboTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ghế trống", "Đang chọn", "Tạm giữ", "Đã bán", "Đặt trước" }));
        jPanel4.add(comboTrangthai);
        comboTrangthai.setBounds(610, 290, 92, 30);

        jButton9.setBackground(new java.awt.Color(153, 0, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Số ghế");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9);
        jButton9.setBounds(230, 290, 110, 30);

        comboLoaighe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thường", "Vip", "Đôi", " " }));
        jPanel4.add(comboLoaighe);
        comboLoaighe.setBounds(360, 230, 330, 30);

        btnHuy.setText("Hủy");
        jPanel4.add(btnHuy);
        btnHuy.setBounds(720, 280, 72, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 46, 81));
        jLabel3.setText("DANH SÁCH GHẾ");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(190, 330, 190, 30);
        jPanel4.add(txtTimKiem);
        txtTimKiem.setBounds(360, 70, 220, 30);

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel4.add(btnTimKiem);
        btnTimKiem.setBounds(610, 70, 100, 30);

        jButton11.setBackground(new java.awt.Color(153, 0, 51));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Mã ghế");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton11);
        jButton11.setBounds(230, 120, 110, 30);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel4.add(btnThem);
        btnThem.setBounds(720, 130, 72, 30);

        tbVe.setModel(new javax.swing.table.DefaultTableModel(
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
        tbVe.setShowGrid(true);
        jScrollPane2.setViewportView(tbVe);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(190, 360, 600, 130);
        jPanel4.add(jSeparator15);
        jSeparator15.setBounds(180, 110, 640, 10);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 46, 81));
        jLabel4.setText("TRA CỨU GHẾ");
        jPanel4.add(jLabel4);
        jLabel4.setBounds(190, 40, 190, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mmActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_btnThemActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new QuanLyGheForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> comboHangghe;
    private javax.swing.JComboBox<String> comboLoaighe;
    private javax.swing.JComboBox<String> comboSoghe;
    private javax.swing.JComboBox<String> comboTrangthai;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JButton mm;
    private javax.swing.JTable tbVe;
    private javax.swing.JLabel txtDashboard1;
    private javax.swing.JLabel txtDoAnHoaDon;
    private javax.swing.JTextField txtMaghe;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JLabel txtVeVaGheNgoi;
    // End of variables declaration//GEN-END:variables
}
