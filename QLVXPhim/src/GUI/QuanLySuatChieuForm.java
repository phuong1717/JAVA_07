/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
import CRUD.PhimCRUD;
import CRUD.SuatChieuCRUD;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Phim;
import model.SuatChieu;

/**
 *
 * @author ADMIN
 */
public class QuanLySuatChieuForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuanLySuatChieuForm.class.getName());
    DefaultTableModel tableModel;
    SuatChieuCRUD suatchieu;
    ArrayList<SuatChieu> dsSuatChieu;
    private ArrayList<Phim> dsPhim;

    private void loadComboBoxPhim() {
    dsPhim = new PhimCRUD().getAllPhim();
    cbPhim.removeAllItems();
        for (Phim p : dsPhim) {
        cbPhim.addItem(p.getMaPhim() + " - " + p.getTenPhim());
        }
    }

    public QuanLySuatChieuForm() {
        
        initComponents();
        txtMaSuatChieu.setEditable(false);   // Không cho gõ
        txtMaSuatChieu.setEnabled(false); 
        loadComboBoxPhim();
        layDuLieu();
            gancn();
            tblSuatChieu.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tblSuatChieu.getSelectedRow();
        if (selectedRow != -1) {
            txtMaSuatChieu.setText(tblSuatChieu.getValueAt(selectedRow, 0).toString());
            String maPhimSelected = tblSuatChieu.getValueAt(selectedRow, 1).toString();
for (int i = 0; i < cbPhim.getItemCount(); i++) {
    if (cbPhim.getItemAt(i).startsWith(maPhimSelected + " -")) {
        cbPhim.setSelectedIndex(i);
        break;
    }
}

            txtBatDau.setText(tblSuatChieu.getValueAt(selectedRow, 2).toString());
            txtKetThuc.setText(tblSuatChieu.getValueAt(selectedRow, 3).toString());
            txtTrangThai.setText(tblSuatChieu.getValueAt(selectedRow, 4).toString());
        }
    }
});


            gancn();
    }
    private void layDuLieu(){
        setIconImage(new ImageIcon(getClass().getResource("/image/ava-01.png")).getImage());
        FlatSVGIcon iconAdmin = new FlatSVGIcon("image/admin.svg", 50, 50);
        lbAdmin.setIcon(iconAdmin);
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tableModel.addColumn("Mã suất chiếu");
        tableModel.addColumn("Mã phim");
        tableModel.addColumn("Bắt đầu");
        tableModel.addColumn("Kết thúc");
        tableModel.addColumn("Trạng thái");
        suatchieu = new SuatChieuCRUD();
        dsSuatChieu = suatchieu.getAllSuatChieu();
        for(SuatChieu p: dsSuatChieu){
            System.out.println(p.toString());
            tableModel.addRow(new Object[]{
                p.getMaSuatChieu(),
                p.getMaPhim(),
                p.getBatDau(),
                p.getKetThuc(),
                p.getTrangThai(),
            });
        }
        tblSuatChieu.setModel(tableModel);
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
        jLabel17 = new javax.swing.JLabel();
        lbAdmin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSuatChieu = new javax.swing.JTable();
        txtMaSuatChieu = new javax.swing.JTextField();
        txtBatDau = new javax.swing.JTextField();
        txtTrangThai = new javax.swing.JTextField();
        btnXoa1 = new javax.swing.JButton();
        txtKetThuc = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        txtmasuatchieu = new javax.swing.JTextField();
        cbPhim = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnHuy = new javax.swing.JButton();

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

        jLabel17.setFont(new java.awt.Font("SVN-Gilroy SemiBold", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Admin");

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
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel9)
                .addGap(50, 50, 50)
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
                .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);
        jPanel5.setBounds(-10, -10, 190, 510);
        jPanel4.add(jSeparator1);
        jSeparator1.setBounds(180, 130, 0, 3);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel4.add(btnThem);
        btnThem.setBounds(680, 170, 72, 30);

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel4.add(btnSua);
        btnSua.setBounds(680, 210, 72, 30);

        tblSuatChieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã suất chiếu", "Mã phim", "Mã phòng", "Bắt đầu", "Kết thúc", "Trạng Thái"
            }
        ));
        jScrollPane1.setViewportView(tblSuatChieu);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(190, 380, 590, 100);

        txtMaSuatChieu.setText("Auto");
        txtMaSuatChieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSuatChieuActionPerformed(evt);
            }
        });
        jPanel4.add(txtMaSuatChieu);
        txtMaSuatChieu.setBounds(360, 140, 100, 30);

        txtBatDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBatDauActionPerformed(evt);
            }
        });
        jPanel4.add(txtBatDau);
        txtBatDau.setBounds(360, 220, 280, 30);

        txtTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrangThaiActionPerformed(evt);
            }
        });
        jPanel4.add(txtTrangThai);
        txtTrangThai.setBounds(360, 300, 280, 30);

        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoa1);
        btnXoa1.setBounds(680, 250, 72, 30);

        txtKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKetThucActionPerformed(evt);
            }
        });
        jPanel4.add(txtKetThuc);
        txtKetThuc.setBounds(360, 260, 280, 30);

        jLabel14.setBackground(new java.awt.Color(153, 0, 51));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("QUẢN LÝ SUẤT CHIẾU");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel14.setFocusTraversalPolicyProvider(true);
        jLabel14.setOpaque(true);
        jPanel4.add(jLabel14);
        jLabel14.setBounds(220, 10, 550, 40);

        jLabel11.setBackground(new java.awt.Color(153, 0, 51));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Mã phim");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setFocusTraversalPolicyProvider(true);
        jLabel11.setOpaque(true);
        jPanel4.add(jLabel11);
        jLabel11.setBounds(220, 180, 130, 30);

        jLabel12.setBackground(new java.awt.Color(153, 0, 51));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Bắt đầu");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setFocusTraversalPolicyProvider(true);
        jLabel12.setOpaque(true);
        jPanel4.add(jLabel12);
        jLabel12.setBounds(220, 220, 130, 30);

        jLabel13.setBackground(new java.awt.Color(153, 0, 51));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Kết thúc");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.setFocusTraversalPolicyProvider(true);
        jLabel13.setOpaque(true);
        jPanel4.add(jLabel13);
        jLabel13.setBounds(220, 260, 130, 30);

        jLabel15.setBackground(new java.awt.Color(153, 0, 51));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Trạng thái");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel15.setFocusTraversalPolicyProvider(true);
        jLabel15.setOpaque(true);
        jPanel4.add(jLabel15);
        jLabel15.setBounds(220, 300, 130, 30);

        jLabel16.setBackground(new java.awt.Color(153, 0, 51));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Mã suất chiếu ");
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel16.setFocusTraversalPolicyProvider(true);
        jLabel16.setOpaque(true);
        jPanel4.add(jLabel16);
        jLabel16.setBounds(220, 140, 130, 30);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 46, 81));
        jLabel1.setText("TÌM SUẤT CHIẾU");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(190, 80, 150, 30);

        btnTimkiem.setBackground(new java.awt.Color(153, 0, 51));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimkiem.setText("Tìm Kiếm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel4.add(btnTimkiem);
        btnTimkiem.setBounds(670, 80, 110, 30);

        txtmasuatchieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmasuatchieuActionPerformed(evt);
            }
        });
        jPanel4.add(txtmasuatchieu);
        txtmasuatchieu.setBounds(380, 80, 250, 30);

        cbPhim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPhimActionPerformed(evt);
            }
        });
        jPanel4.add(cbPhim);
        cbPhim.setBounds(360, 180, 280, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 46, 81));
        jLabel2.setText("DANH SÁCH SUẤT CHIẾU");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(190, 340, 250, 40);
        jPanel4.add(jSeparator2);
        jSeparator2.setBounds(170, 120, 660, 10);
        jPanel4.add(jSeparator3);
        jSeparator3.setBounds(160, 340, 660, 10);

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel4.add(btnHuy);
        btnHuy.setBounds(680, 290, 72, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
                                      
    try {
        String maPhim = cbPhim.getSelectedItem().toString().split(" - ")[0];
        String batDauStr = txtBatDau.getText().trim();
        String ketThucStr = txtKetThuc.getText().trim();
        String trangThai = txtTrangThai.getText().trim();

        if(maPhim.isEmpty() || batDauStr.isEmpty() || ketThucStr.isEmpty() || trangThai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        SuatChieu sc = new SuatChieu();
        sc.setMaPhim(Integer.parseInt(maPhim));
        sc.setBatDau(LocalDateTime.parse(batDauStr)); // parse từ String
        sc.setKetThuc(LocalDateTime.parse(ketThucStr));
        sc.setTrangThai(trangThai);

        if(suatchieu.insertSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Thêm suất chiếu thành công!");
            layDuLieu(); // load lại từ DB
            txtBatDau.setText("");
            txtKetThuc.setText("");
            txtTrangThai.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    } catch(Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtMaSuatChieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSuatChieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSuatChieuActionPerformed

    private void txtBatDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBatDauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBatDauActionPerformed

    private void txtKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKetThucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKetThucActionPerformed

    private void txtTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrangThaiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
                                  
    int selectedRow = tblSuatChieu.getSelectedRow();
    if(selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn suất chiếu để sửa!");
        return;
    }

    try {
        int maSuat = Integer.parseInt(txtMaSuatChieu.getText().trim());
        int maPhim = Integer.parseInt(cbPhim.getSelectedItem().toString().split(" - ")[0]);
        LocalDateTime batDau = LocalDateTime.parse(txtBatDau.getText().trim());
        LocalDateTime ketThuc = LocalDateTime.parse(txtKetThuc.getText().trim());
        String trangThai = txtTrangThai.getText().trim();

        SuatChieu sc = new SuatChieu();
        sc.setMaSuatChieu(maSuat);
        sc.setMaPhim(maPhim);
        sc.setBatDau(batDau);
        sc.setKetThuc(ketThuc);
        sc.setTrangThai(trangThai);

        if(suatchieu.updateSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            layDuLieu(); // load lại từ DB
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    } catch(Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
                                       
    int selectedRow = tblSuatChieu.getSelectedRow();
    if(selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn suất chiếu để xóa!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa suất chiếu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if(confirm == JOptionPane.YES_OPTION) {
        int maSuat = (int) tblSuatChieu.getValueAt(selectedRow, 0);
        if(suatchieu.delete(maSuat)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            layDuLieu(); // load lại từ DB
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }

    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:                                         
    String maSuatTim = txtmasuatchieu.getText().trim();
    
    if(maSuatTim.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã suất chiếu cần tìm!");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) tblSuatChieu.getModel();
    model.setRowCount(0); // xóa tất cả hàng hiện tại

    for(SuatChieu sc : dsSuatChieu) {
        if(String.valueOf(sc.getMaSuatChieu()).equals(maSuatTim)) {
            model.addRow(new Object[]{
                sc.getMaSuatChieu(),
                sc.getMaPhim(),
                sc.getBatDau(),
                sc.getKetThuc(),
                sc.getTrangThai()
            });
        }
    }

    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void cbPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPhimActionPerformed

    private void txtmasuatchieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmasuatchieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmasuatchieuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        txtMaSuatChieu.setText("");
    txtBatDau.setText("");
    txtKetThuc.setText("");
    txtTrangThai.setText("");
    

    // Bỏ chọn dòng trong JTable nếu có
    tblSuatChieu.clearSelection();
    }//GEN-LAST:event_btnHuyActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new QuanLySuatChieuForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JComboBox<String> cbPhim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JTable tblSuatChieu;
    private javax.swing.JTextField txtBatDau;
    private javax.swing.JLabel txtDashboard1;
    private javax.swing.JLabel txtDoAnHoaDon;
    private javax.swing.JTextField txtKetThuc;
    private javax.swing.JTextField txtMaSuatChieu;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JTextField txtTrangThai;
    private javax.swing.JLabel txtVeVaGheNgoi;
    private javax.swing.JTextField txtmasuatchieu;
    // End of variables declaration//GEN-END:variables
}
