/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import CRUD.TicketCRUD;
import CRUD.PhimCRUD;
import CRUD.SuatChieuCRUD; // THÊM DÒNG NÀY
import DAO.TicketDAO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableRowSorter;
import model.Ticket;
import model.Phim;
import model.SuatChieu; // THÊM DÒNG NÀY

/**
 *
 * @author ADMIN
 */
public class QuanLyVeForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuanLyVeForm.class.getName());
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private ArrayList<Ticket> dsTicket;
    private TicketCRUD ticketCRUD;
    private PhimCRUD phimCRUD;
    private SuatChieuCRUD suatChieuCRUD; // THÊM DÒNG NÀY

    public QuanLyVeForm() {
        initComponents();
        gancn();
        initTableModel();
        addEventListeners();
        
        // Khởi tạo CRUD
        ticketCRUD = new TicketCRUD();
        phimCRUD = new PhimCRUD();
        suatChieuCRUD = new SuatChieuCRUD(); // THÊM DÒNG NÀY
        
        // Load danh sách phim và suất chiếu từ database
        loadDanhSachPhim();
        loadDanhSachSuatChieu(); // THÊM DÒNG NÀY
        
        layDuLieu();
        
        // Ẩn ô nhập mã vé vì nó tự động tăng
        txtMave.setEnabled(false);
        txtMave.setText("Auto");
    }
    
    // PHƯƠNG THỨC LOAD DANH SÁCH PHIM
    private void loadDanhSachPhim() {
        try {
            // Lấy danh sách phim từ database
            ArrayList<Phim> dsPhim = phimCRUD.getAllPhim();
            
            // Xóa tất cả items cũ trong combobox
            comboPhim.removeAllItems();
            
            // Thêm từng phim vào combobox
            for (Phim phim : dsPhim) {
                comboPhim.addItem(phim.getTenPhim());
            }
            
            // Nếu có ít nhất 1 phim, chọn phim đầu tiên
            if (comboPhim.getItemCount() > 0) {
                comboPhim.setSelectedIndex(0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            
            // Nếu có lỗi, thêm các phim mặc định
            comboPhim.addItem("Nhà Bà Nữ");
            comboPhim.addItem("Mai");
            comboPhim.addItem("Bố Già");
        }
    }
    
    // PHƯƠNG THỨC LOAD DANH SÁCH SUẤT CHIẾU - THÊM PHƯƠNG THỨC NÀY
    private void loadDanhSachSuatChieu() {
        try {
            // Lấy danh sách suất chiếu từ database
            ArrayList<SuatChieu> dsSuatChieu = suatChieuCRUD.getAllSuatChieu();
            
            // Xóa tất cả items cũ trong combobox
            comboSuatchieu.removeAllItems();
            
            // Thêm từng suất chiếu vào combobox với định dạng đẹp
            for (SuatChieu suatChieu : dsSuatChieu) {
                String displayText = String.format("%s - %s", 
                    suatChieu.getBatDau().toLocalTime().toString(),
                    suatChieu.getKetThuc().toLocalTime().toString());
                comboSuatchieu.addItem(displayText);
            }
            
            // Nếu có ít nhất 1 suất chiếu, chọn suất chiếu đầu tiên
            if (comboSuatchieu.getItemCount() > 0) {
                comboSuatchieu.setSelectedIndex(0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách suất chiếu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            
            // Nếu có lỗi, thêm các suất chiếu mặc định
            comboSuatchieu.addItem("8:00-9:30");
            comboSuatchieu.addItem("9:45-11:00");
            comboSuatchieu.addItem("12:30-13:45");
        }
    }
    
    private void layDuLieu() {
        try {
            dsTicket = ticketCRUD.getAllTickets();
            tableModel.setRowCount(0);
            
            // Clear và thêm cột
            tableModel.setColumnCount(0);
            tableModel.addColumn("Mã vé");
            tableModel.addColumn("Tên phim");
            tableModel.addColumn("Suất chiếu");
            tableModel.addColumn("Ghế");
            tableModel.addColumn("Giá vé");
            tableModel.addColumn("Mô tả");
            
            for (Ticket t : dsTicket) {
                tableModel.addRow(new Object[]{
                    t.getMaVe(),
                    t.getTenPhim(),
                    t.getSuatChieu(),
                    t.getGhe(),
                    t.getGiaVe(),
                    t.getMoTa()
                });
            }

            tbVe.setModel(tableModel);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
             setIconImage(new ImageIcon(getClass().getResource("/image/ava-01.png")).getImage());
         FlatSVGIcon iconAdmin = new FlatSVGIcon("image/admin.svg", 50, 50);
        lbAdmin.setIcon(iconAdmin);
                
                
    }

    private void initTableModel() {
        tableModel = (DefaultTableModel) tbVe.getModel();
        rowSorter = new TableRowSorter<>(tableModel);
        tbVe.setRowSorter(rowSorter);
    }

    private void addEventListeners() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themVe();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suaVe();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaVe();
            }
        });

        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemVe();
            }
        });

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

    private void themVe() {
    System.out.println("=== BẮT ĐẦU THÊM VÉ ===");
    
    try {
        // Lấy dữ liệu từ form - KHÔNG lấy mã vé
        String phim = comboPhim.getSelectedItem().toString();
        String suatChieu = comboSuatchieu.getSelectedItem().toString();
        String ghe = txtGhe.getText().trim();
        String giaVeStr = txtGiave.getText().trim();
        // KHÔNG lấy mô tả từ form nữa, sẽ tự động tạo
        
        System.out.println("Dữ liệu form - Phim: " + phim + ", Ghế: " + ghe + ", Giá: " + giaVeStr);

        // Validation cơ bản - KHÔNG validate mã vé
        if (ghe.isEmpty() || giaVeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin ghế và giá vé!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse dữ liệu
        double giaVe = Double.parseDouble(giaVeStr);
        
        if (giaVe <= 0) {
            JOptionPane.showMessageDialog(this, "Giá vé phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng Ticket - KHÔNG set mã vé và mô tả
        Ticket newTicket = new Ticket();
        newTicket.setTenPhim(phim);
        newTicket.setSuatChieu(suatChieu);
        newTicket.setGhe(ghe);
        newTicket.setGiaVe(giaVe);
        newTicket.setMoTa(""); // Để trống, sẽ được điền tự động

        System.out.println("Đối tượng Ticket đã tạo");

        // Gọi CRUD để thêm vé VỚI MÔ TẢ TỰ ĐỘNG
        System.out.println("Bắt đầu gọi CRUD addTicketWithAutoDescription...");
        int maVeMoi = ticketCRUD.addTicketWithAutoDescription(newTicket);
        System.out.println("Mã vé mới: " + maVeMoi);
        
        if (maVeMoi > 0) {
            JOptionPane.showMessageDialog(this, 
                "Thêm vé thành công! Mã vé: " + maVeMoi + "\nMô tả: Vé " + maVeMoi, 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            layDuLieu(); // Load lại dữ liệu
        } else {
            JOptionPane.showMessageDialog(this, "Thêm vé thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (IllegalArgumentException e) {
        System.out.println("Lỗi validation: " + e.getMessage());
        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        System.out.println("Lỗi exception: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm vé: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    System.out.println("=== KẾT THÚC THÊM VÉ ===");
}

    private void suaVe() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String maVeStr = txtMave.getText().trim();
            String phim = comboPhim.getSelectedItem().toString();
            String suatChieu = comboSuatchieu.getSelectedItem().toString();
            String ghe = txtGhe.getText().trim();
            String giaVeStr = txtGiave.getText().trim();
            String moTa = "";
            
            // Validation
            if (ghe.isEmpty() || giaVeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse dữ liệu
            int maVe = Integer.parseInt(maVeStr);
            double giaVe = Double.parseDouble(giaVeStr);

            if (giaVe <= 0) {
                JOptionPane.showMessageDialog(this, "Giá vé phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng Ticket
            Ticket updatedTicket = new Ticket();
            updatedTicket.setMaVe(maVe);
            updatedTicket.setTenPhim(phim);
            updatedTicket.setSuatChieu(suatChieu);
            updatedTicket.setGhe(ghe);
            updatedTicket.setGiaVe(giaVe);
            updatedTicket.setMoTa(moTa);

            // Gọi CRUD để cập nhật
            boolean success = ticketCRUD.updateTicket(updatedTicket);
            if (success) {
                JOptionPane.showMessageDialog(this, "Sửa vé thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                selectedRow = -1;
                layDuLieu(); // Load lại dữ liệu
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa vé: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaVe() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Lấy mã vé từ dòng được chọn
            int modelRow = tbVe.convertRowIndexToModel(selectedRow);
            int maVe = (int) tableModel.getValueAt(modelRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa vé mã " + maVe + "?", "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Gọi CRUD để xóa
                boolean success = ticketCRUD.deleteTicket(maVe);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa vé thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    selectedRow = -1;
                    layDuLieu(); // Load lại dữ liệu
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa vé: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemVe() {
        String tuKhoa = txtTimKiem.getText().trim();
        
        if (tuKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ArrayList<Ticket> ketQua = ticketCRUD.searchTickets(tuKhoa);
            
            // Hiển thị kết quả
            tableModel.setRowCount(0);
            for (Ticket t : ketQua) {
                tableModel.addRow(new Object[]{
                    t.getMaVe(),
                    t.getTenPhim(),
                    t.getSuatChieu(),
                    t.getGhe(),
                    t.getGiaVe(),
                    t.getMoTa()
                });
            }

            // Đếm số kết quả tìm thấy
            int rowCount = tableModel.getRowCount();
            if (rowCount > 0) {
                JOptionPane.showMessageDialog(this, "Tìm thấy " + rowCount + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                if (rowCount > 0) {
                    tbVe.setRowSelectionInterval(0, 0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        clearForm();
        selectedRow = -1;
        txtTimKiem.setText("");
        layDuLieu(); // Load lại toàn bộ dữ liệu
        JOptionPane.showMessageDialog(this, "Đã reset form thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void hienThiDuLieuLenForm() {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            try {
                // Lấy dữ liệu từ bảng
                int modelRow = tbVe.convertRowIndexToModel(selectedRow);
                int maVe = (int) tableModel.getValueAt(modelRow, 0);
                String phim = tableModel.getValueAt(modelRow, 1).toString();
                String suatChieu = tableModel.getValueAt(modelRow, 2).toString();
                String ghe = tableModel.getValueAt(modelRow, 3).toString();
                double giaVe = (double) tableModel.getValueAt(modelRow, 4);

                // Hiển thị lên form
                txtMave.setText(String.valueOf(maVe));
                
                // Chọn đúng phim trong combobox
                boolean foundPhim = false;
                for (int i = 0; i < comboPhim.getItemCount(); i++) {
                    if (comboPhim.getItemAt(i).equals(phim)) {
                        comboPhim.setSelectedIndex(i);
                        foundPhim = true;
                        break;
                    }
                }
                
                // Nếu không tìm thấy phim trong combobox, thêm vào
                if (!foundPhim && !phim.isEmpty()) {
                    comboPhim.addItem(phim);
                    comboPhim.setSelectedItem(phim);
                }
                
                // Chọn đúng suất chiếu trong combobox
                boolean foundSuatChieu = false;
                for (int i = 0; i < comboSuatchieu.getItemCount(); i++) {
                    if (comboSuatchieu.getItemAt(i).equals(suatChieu)) {
                        comboSuatchieu.setSelectedIndex(i);
                        foundSuatChieu = true;
                        break;
                    }
                }
                
                // Nếu không tìm thấy suất chiếu trong combobox, thêm vào
                if (!foundSuatChieu && !suatChieu.isEmpty()) {
                    comboSuatchieu.addItem(suatChieu);
                    comboSuatchieu.setSelectedItem(suatChieu);
                }
                
                txtGhe.setText(ghe);
                txtGiave.setText(String.valueOf(giaVe));
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtMave.setText("Auto");
        if (comboPhim.getItemCount() > 0) comboPhim.setSelectedIndex(0);
        if (comboSuatchieu.getItemCount() > 0) comboSuatchieu.setSelectedIndex(0);
        txtGhe.setText("");
        txtGiave.setText("");
        tbVe.clearSelection();
    }

    // Validation dữ liệu - KHÔNG validate mã vé
    private boolean validateInput(String ghe, String giaVe) {
        if (ghe.isEmpty() || giaVe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin ghế và giá vé!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double giaVeDouble = Double.parseDouble(giaVe);
            
            if (giaVeDouble <= 0) {
                JOptionPane.showMessageDialog(this, "Giá vé phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá vé phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
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

        jPanel1 = new javax.swing.JPanel();
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
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        comboPhim = new javax.swing.JComboBox<>();
        comboSuatchieu = new javax.swing.JComboBox<>();
        txtMave = new javax.swing.JTextField();
        txtGhe = new javax.swing.JTextField();
        txtGiave = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVe = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhimVaSuatChieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVeVaGheNgoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoAnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThongKeBaoCao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuanTriHeThong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGap(37, 37, 37)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel9)
                .addGap(62, 62, 62)
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
                .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);
        jPanel5.setBounds(-10, -10, 190, 510);
        jPanel4.add(jSeparator1);
        jSeparator1.setBounds(180, 130, 0, 3);
        jPanel4.add(jSeparator14);
        jSeparator14.setBounds(170, 330, 640, 3);

        mm.setBackground(new java.awt.Color(153, 0, 51));
        mm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mm.setForeground(new java.awt.Color(255, 255, 255));
        mm.setText("Giá vé");
        mm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmActionPerformed(evt);
            }
        });
        jPanel4.add(mm);
        mm.setBounds(450, 290, 110, 30);

        jButton3.setBackground(new java.awt.Color(153, 0, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("QUẢN LÝ VÉ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);
        jButton3.setBounds(290, 0, 390, 40);

        jButton5.setBackground(new java.awt.Color(153, 0, 51));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Nhập vé");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);
        jButton5.setBounds(220, 70, 110, 30);

        jButton6.setBackground(new java.awt.Color(153, 0, 51));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Suất chiếu");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6);
        jButton6.setBounds(220, 240, 110, 30);

        jButton7.setBackground(new java.awt.Color(153, 0, 51));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Ghế");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(220, 290, 110, 30);

        btnThem.setText("Thêm");
        jPanel4.add(btnThem);
        btnThem.setBounds(700, 150, 70, 30);

        btnSua.setText("Sửa");
        jPanel4.add(btnSua);
        btnSua.setBounds(700, 193, 72, 30);

        btnXoa.setText("Xóa");
        jPanel4.add(btnXoa);
        btnXoa.setBounds(700, 233, 72, 30);

        comboPhim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhà Bà Nữ", "Mai", "Bố Già", "Kimetsu no Yaiba", "Mang mẹ đi bỏ", "Doremon chú mèo máy đến từ tương lai", "Mưa đỏ" }));
        comboPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPhimActionPerformed(evt);
            }
        });
        jPanel4.add(comboPhim);
        comboPhim.setBounds(350, 190, 330, 30);

        comboSuatchieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:00-9:30", "9:45-11:00", "12:30-13:45", "14:00-15:30", "15:45-17:00", "17:30-19:00", "19:15-20:45", "21:00-22:30", "22:45-24:00" }));
        comboSuatchieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSuatchieuActionPerformed(evt);
            }
        });
        jPanel4.add(comboSuatchieu);
        comboSuatchieu.setBounds(350, 240, 330, 30);

        txtMave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaveActionPerformed(evt);
            }
        });
        jPanel4.add(txtMave);
        txtMave.setBounds(350, 140, 330, 30);
        jPanel4.add(txtGhe);
        txtGhe.setBounds(350, 290, 90, 30);
        jPanel4.add(txtGiave);
        txtGiave.setBounds(580, 290, 100, 30);

        jButton8.setBackground(new java.awt.Color(153, 0, 51));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Phim");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8);
        jButton8.setBounds(220, 190, 110, 30);

        btnHuy.setText("Hủy");
        jPanel4.add(btnHuy);
        btnHuy.setBounds(700, 273, 72, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 46, 81));
        jLabel3.setText("TRA CỨU VÉ");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(190, 40, 190, 30);

        jButton9.setBackground(new java.awt.Color(153, 0, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Mã vé");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9);
        jButton9.setBounds(220, 140, 110, 30);
        jPanel4.add(txtTimKiem);
        txtTimKiem.setBounds(350, 70, 240, 30);

        btnTimKiem.setBackground(new java.awt.Color(153, 0, 0));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        jPanel4.add(btnTimKiem);
        btnTimKiem.setBounds(630, 70, 100, 30);

        tbVe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbVe);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(210, 360, 560, 120);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 46, 81));
        jLabel1.setText("THÔNG TIN VÉ");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(440, 110, 210, 25);
        jPanel4.add(jSeparator15);
        jSeparator15.setBounds(180, 110, 640, 3);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 46, 81));
        jLabel2.setText("DANH SÁCH VÉ");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(200, 330, 250, 40);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void comboSuatchieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSuatchieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSuatchieuActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void txtMaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaveActionPerformed

    private void comboPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPhimActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new QuanLyVeForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> comboPhim;
    private javax.swing.JComboBox<String> comboSuatchieu;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTextField txtGhe;
    private javax.swing.JTextField txtGiave;
    private javax.swing.JTextField txtMave;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JLabel txtVeVaGheNgoi;
    // End of variables declaration//GEN-END:variables
}
