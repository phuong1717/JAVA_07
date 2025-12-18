/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import CRUD.ThongKeDoanhThu;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import CRUD.PhimCRUD;       
import java.util.ArrayList; 
import model.Phim;        
import javax.swing.JComboBox;
import CRUD.HoaDonCRUD;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
// Thêm import này ở đầu file
import org.jfree.data.category.DefaultCategoryDataset;



/**
 *
 * @author ADMIN
 */
public class ThongKeBaoCaoForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ThongKeBaoCaoForm.class.getName());

    /**
     * Creates new form ThongKeBaoCaoForm
     */
    public ThongKeBaoCaoForm() {
        initComponents();
        gancn();
        this.pack();
        this.setLocationRelativeTo(null);
        loadPhim();
    }
    // Tạo hiệu ứng hover cho 1 label menu bên trái
public void gancn() {
         setIconImage(new ImageIcon(getClass().getResource("/image/ava-01.png")).getImage());
    FlatSVGIcon iconChart = new FlatSVGIcon("image/statistical.svg", 150, 150);
                lbChart.setIcon(iconChart);
    FlatSVGIcon iconAdmin = new FlatSVGIcon("image/admin.svg", 50, 50);
        lbAdmin.setIcon(iconAdmin);
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

        jDatePickerUtil1 = new org.jdatepicker.util.JDatePickerUtil();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtPhimVaSuatChieu = new javax.swing.JLabel();
        txtVeVaGheNgoi = new javax.swing.JLabel();
        txtDoAnHoaDon = new javax.swing.JLabel();
        txtThongKeBaoCao = new javax.swing.JLabel();
        txtQuanTriHeThong = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        txtDashboard1 = new javax.swing.JLabel();
        lbAdmin = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        ThongKe = new javax.swing.JTable();
        XemBaoCao = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tong = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        ChonPhim = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dateTuNgay = new com.toedter.calendar.JDateChooser();
        dateDenNgay = new com.toedter.calendar.JDateChooser();
        lbChart = new javax.swing.JLabel();
        tblBaoCao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel6.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(153, 0, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel10.setFont(new java.awt.Font("UTM Amerika Sans", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("myGalaxyCinema");

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDoAnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVeVaGheNgoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhimVaSuatChieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuanTriHeThong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThongKeBaoCao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel10)
                .addGap(56, 56, 56)
                .addComponent(txtDashboard1)
                .addGap(26, 26, 26)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPhimVaSuatChieu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVeVaGheNgoi)
                .addGap(12, 12, 12)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDoAnHoaDon)
                .addGap(14, 14, 14)
                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThongKeBaoCao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtQuanTriHeThong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jPanel6.add(jPanel7);
        jPanel7.setBounds(-10, -10, 190, 510);
        jPanel6.add(jSeparator2);
        jSeparator2.setBounds(180, 130, 0, 3);
        jPanel6.add(jSeparator22);
        jSeparator22.setBounds(180, 210, 640, 10);
        jPanel6.add(jSeparator23);
        jSeparator23.setBounds(180, 90, 640, 30);

        ThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Phim", "Số vé bán", "Doanh thu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        ThongKe.setShowGrid(true);
        jScrollPane3.setViewportView(ThongKe);

        jPanel6.add(jScrollPane3);
        jScrollPane3.setBounds(220, 240, 310, 150);

        XemBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        XemBaoCao.setText("Xem báo cáo");
        XemBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XemBaoCaoActionPerformed(evt);
            }
        });
        jPanel6.add(XemBaoCao);
        XemBaoCao.setBounds(570, 160, 110, 23);

        jLabel12.setBackground(new java.awt.Color(153, 0, 51));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Đến Ngày");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setFocusTraversalPolicyProvider(true);
        jLabel12.setOpaque(true);
        jPanel6.add(jLabel12);
        jLabel12.setBounds(230, 160, 100, 25);

        jLabel14.setBackground(new java.awt.Color(153, 0, 51));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Từ Ngày");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel14.setFocusTraversalPolicyProvider(true);
        jLabel14.setOpaque(true);
        jPanel6.add(jLabel14);
        jLabel14.setBounds(230, 120, 100, 25);

        Tong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Tổng vé", "Tổng doanh thu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Tong.setShowGrid(true);
        jScrollPane1.setViewportView(Tong);

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(220, 410, 310, 70);

        jLabel15.setBackground(new java.awt.Color(153, 0, 51));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Phim");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel15.setFocusTraversalPolicyProvider(true);
        jLabel15.setOpaque(true);
        jPanel6.add(jLabel15);
        jLabel15.setBounds(510, 120, 100, 25);

        ChonPhim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        ChonPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChonPhimActionPerformed(evt);
            }
        });
        jPanel6.add(ChonPhim);
        ChonPhim.setBounds(630, 120, 140, 25);

        jLabel6.setBackground(new java.awt.Color(153, 0, 51));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("THỐNG KÊ BÁO CÁO");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel6.setFocusTraversalPolicyProvider(true);
        jLabel6.setOpaque(true);
        jPanel6.add(jLabel6);
        jLabel6.setBounds(220, 30, 550, 40);

        dateTuNgay.setDateFormatString("dd/MM/yyyy");
        jPanel6.add(dateTuNgay);
        dateTuNgay.setBounds(350, 120, 130, 30);

        dateDenNgay.setDateFormatString("dd/MM/yyyy");
        jPanel6.add(dateDenNgay);
        dateDenNgay.setBounds(350, 160, 130, 30);
        jPanel6.add(lbChart);
        lbChart.setBounds(590, 250, 180, 160);

        tblBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblBaoCao.setText("Hiện thị biểu đồ");
        tblBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblBaoCaoActionPerformed(evt);
            }
        });
        jPanel6.add(tblBaoCao);
        tblBaoCao.setBounds(600, 430, 140, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void XemBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XemBaoCaoActionPerformed
        // TODO add your handling code here:
        
         try {
        ThongKeDoanhThu tk = new ThongKeDoanhThu();
//        System.out.println(dateTuNgay.getDate());
//        System.out.println(dateDenNgay.getDate());
        java.util.Date tuNgay = dateTuNgay.getDate();
        java.util.Date denNgay = dateDenNgay.getDate();

        // Lấy ngày từ ô nhập và validate
//        LocalDate tuNgay = tk.validateTuNgay(dateTuNgay.getDateFormatString());
//        LocalDate denNgay = tk.validateTuNgay(dateDenNgay.getDateFormatString());
             
        if (tuNgay.after(denNgay)) {
            JOptionPane.showMessageDialog(this, "Đến ngày phải >= Từ ngày!");
            return;
        }

        // Lấy tên phim trong ComboBox (null hoặc "Tất Cả" -> thống kê tất cả phim)
        String tenPhim = ChonPhim.getSelectedItem().toString();
        if (tenPhim.equalsIgnoreCase("Tất Cả")) {
            tenPhim = null;
        }

        // Gọi CRUD lấy dữ liệu báo cáo chi tiết
        ArrayList<Object[]> ds = tk.thongKeDoanhThu(tenPhim, tuNgay, denNgay);

        // Hiển thị dữ liệu chi tiết lên bảng ThongKe
        DefaultTableModel model = (DefaultTableModel) ThongKe.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (Object[] row : ds) {
            model.addRow(row);
        }

        // Lấy tổng vé và tổng doanh thu theo cùng điều kiện
        Object[] tong = tk.layTongVeVaDoanhThu(tenPhim, tuNgay, denNgay); 
        // Lưu ý: layTongVeVaDoanhThu nên sửa để nhận tham số lọc ngày và phim

        // Hiển thị tổng lên bảng Tong
        DefaultTableModel modelTong = (DefaultTableModel) Tong.getModel();
        modelTong.setRowCount(0);
        modelTong.addRow(new Object[] {tong[0], tong[1]});

        if (ds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có vé nào trong khoảng ngày này!");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
         
    }//GEN-LAST:event_XemBaoCaoActionPerformed

    private ArrayList<Phim> List; // danh sách phim từ DB
    private Phim phimChon; // phim đang chọn
    private void loadPhim() {
    try {
        PhimCRUD phimCRUD = new PhimCRUD();
        List = phimCRUD.getAllPhim();

        ChonPhim.removeAllItems();
        ChonPhim.addItem("Tất Cả");

        for (Phim phim : List) {
            ChonPhim.addItem(phim.getTenPhim());
            System.out.println(phim.toString());
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phim:");
        e.printStackTrace();
    }
}

    
    private void ChonPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChonPhimActionPerformed
        // TODO add your handling code here:
   
        int index = ChonPhim.getSelectedIndex();
    if (index > 0 && List != null && index <= List.size()) { 
        phimChon = List.get(index - 1); // vì "Tất Cả" là index 0
    } else {
        phimChon = null; // chọn Tất Cả
    }
    }//GEN-LAST:event_ChonPhimActionPerformed

    private void tblBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblBaoCaoActionPerformed
     try {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Lấy dữ liệu từ JTable ThongKe - cả số vé và doanh thu
        for (int i = 0; i < ThongKe.getRowCount(); i++) {
            Object tenPhimObj = ThongKe.getValueAt(i, 0);
            Object soVeObj = ThongKe.getValueAt(i, 1);
            Object doanhThuObj = ThongKe.getValueAt(i, 2);

            if (tenPhimObj != null && soVeObj != null) {
                String tenPhim = tenPhimObj.toString();
                int soVe = Integer.parseInt(soVeObj.toString());
                dataset.addValue(soVe, "Số vé bán", tenPhim);
            }
            
            if (tenPhimObj != null && doanhThuObj != null) {
                String tenPhim = tenPhimObj.toString();
                try {
                    double doanhThu = Double.parseDouble(doanhThuObj.toString());
                    dataset.addValue(doanhThu/1000000, "Doanh thu (triệu VND)", tenPhim);
                } catch (NumberFormatException e) {}
            }
        }

        if (dataset.getColumnCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Không có dữ liệu để vẽ biểu đồ!\nVui lòng xem báo cáo trước.", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Mở form biểu đồ
        BieuDoThongKeForm formBD = new BieuDoThongKeForm(dataset);
        formBD.setVisible(true);
        formBD.setLocationRelativeTo(null);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi vẽ biểu đồ: " + e.getMessage());
    }

    }//GEN-LAST:event_tblBaoCaoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ThongKeBaoCaoForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ChonPhim;
    private javax.swing.JTable ThongKe;
    private javax.swing.JTable Tong;
    private javax.swing.JButton XemBaoCao;
    private com.toedter.calendar.JDateChooser dateDenNgay;
    private com.toedter.calendar.JDateChooser dateTuNgay;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JLabel lbChart;
    private javax.swing.JButton tblBaoCao;
    private javax.swing.JLabel txtDashboard1;
    private javax.swing.JLabel txtDoAnHoaDon;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JLabel txtVeVaGheNgoi;
    // End of variables declaration//GEN-END:variables
}
