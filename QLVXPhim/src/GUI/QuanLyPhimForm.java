/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
import CRUD.PhimCRUD;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Phim;



/**
 *
 * @author ADMIN
 */
public class QuanLyPhimForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuanLyPhimForm.class.getName());
    DefaultTableModel tableModel;
    PhimCRUD phim;
    ArrayList<Phim> dsPhim;
    public QuanLyPhimForm() {
       
        initComponents();
        txtMaPhim.setEditable(false);   // Không cho gõ
txtMaPhim.setEnabled(false);    // Không cho click // Không cho gõ

        layDuLieu();
            gancn();
            tblPhim.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tblPhim.getSelectedRow();
        if(selectedRow != -1) {
            txtMaPhim.setText(tblPhim.getValueAt(selectedRow, 0).toString());
            txtTenPhim.setText(tblPhim.getValueAt(selectedRow, 1).toString());
            txtThoiLuong.setText(tblPhim.getValueAt(selectedRow, 2).toString());
            txtDanhGia.setText(tblPhim.getValueAt(selectedRow, 3).toString());
            txtTheLoai.setText(tblPhim.getValueAt(selectedRow, 4).toString());
            txtMoTa.setText(tblPhim.getValueAt(selectedRow, 5).toString());
            txtNgayPhatHanh.setText(tblPhim.getValueAt(selectedRow, 6).toString());
        }
    }
});

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
        tableModel.addColumn("Mã phim");
        tableModel.addColumn("Tên phim");
        tableModel.addColumn("Thời lượng");
        tableModel.addColumn("Đánh giá");
        tableModel.addColumn("Thể loại");
        tableModel.addColumn("Mô  tả");
        tableModel.addColumn("Ngày phát hành");
        phim = new PhimCRUD();
        dsPhim = phim.getAllPhim();
        for(Phim p: dsPhim){
            System.out.println(p.toString());
            tableModel.addRow(new Object[]{
                p.getMaPhim(),
                p.getTenPhim(),
                p.getThoiLuong(),
                p.getDanhGia(),
                p.getTheLoai(),
                p.getMoTa(),
                p.getNgayPhatHanh()
            });
        }
        tblPhim.setModel(tableModel);
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
        txtMaPhim = new javax.swing.JTextField();
        txtNgayPhatHanh = new javax.swing.JTextField();
        txtTenPhim = new javax.swing.JTextField();
        txtDanhGia = new javax.swing.JTextField();
        txtTheLoai = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        btnXoa1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhim = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        txtmaphim = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
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
                        .addGap(72, 72, 72)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
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
        btnThem.setBounds(700, 150, 72, 30);

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel4.add(btnSua);
        btnSua.setBounds(700, 190, 72, 30);

        txtMaPhim.setText("Auto");
        txtMaPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhimActionPerformed(evt);
            }
        });
        jPanel4.add(txtMaPhim);
        txtMaPhim.setBounds(320, 120, 100, 30);

        txtNgayPhatHanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayPhatHanhActionPerformed(evt);
            }
        });
        jPanel4.add(txtNgayPhatHanh);
        txtNgayPhatHanh.setBounds(570, 240, 100, 30);

        txtTenPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenPhimActionPerformed(evt);
            }
        });
        jPanel4.add(txtTenPhim);
        txtTenPhim.setBounds(320, 160, 350, 30);

        txtDanhGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDanhGiaActionPerformed(evt);
            }
        });
        jPanel4.add(txtDanhGia);
        txtDanhGia.setBounds(570, 200, 100, 30);

        txtTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTheLoaiActionPerformed(evt);
            }
        });
        jPanel4.add(txtTheLoai);
        txtTheLoai.setBounds(320, 240, 110, 30);

        txtMoTa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMoTaActionPerformed(evt);
            }
        });
        jPanel4.add(txtMoTa);
        txtMoTa.setBounds(320, 280, 350, 30);

        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoa1);
        btnXoa1.setBounds(700, 230, 72, 30);

        jLabel8.setBackground(new java.awt.Color(153, 0, 51));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ngày phát hành");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setFocusTraversalPolicyProvider(true);
        jLabel8.setOpaque(true);
        jPanel4.add(jLabel8);
        jLabel8.setBounds(440, 240, 120, 30);

        jLabel10.setBackground(new java.awt.Color(153, 0, 51));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Mã phim");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setFocusTraversalPolicyProvider(true);
        jLabel10.setOpaque(true);
        jPanel4.add(jLabel10);
        jLabel10.setBounds(220, 120, 90, 30);

        jLabel11.setBackground(new java.awt.Color(153, 0, 51));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Tên phim");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setFocusTraversalPolicyProvider(true);
        jLabel11.setOpaque(true);
        jPanel4.add(jLabel11);
        jLabel11.setBounds(220, 160, 90, 30);

        jLabel12.setBackground(new java.awt.Color(153, 0, 51));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Thời lượng");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setFocusTraversalPolicyProvider(true);
        jLabel12.setOpaque(true);
        jPanel4.add(jLabel12);
        jLabel12.setBounds(220, 200, 90, 30);

        jLabel13.setBackground(new java.awt.Color(153, 0, 51));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Thể loại");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.setFocusTraversalPolicyProvider(true);
        jLabel13.setOpaque(true);
        jPanel4.add(jLabel13);
        jLabel13.setBounds(220, 240, 90, 30);

        jLabel14.setBackground(new java.awt.Color(153, 0, 51));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Mô tả");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel14.setFocusTraversalPolicyProvider(true);
        jLabel14.setOpaque(true);
        jPanel4.add(jLabel14);
        jLabel14.setBounds(220, 280, 90, 30);

        jLabel15.setBackground(new java.awt.Color(153, 0, 51));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("QUẢN LÝ PHIM");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel15.setFocusTraversalPolicyProvider(true);
        jLabel15.setOpaque(true);
        jPanel4.add(jLabel15);
        jLabel15.setBounds(220, 10, 550, 40);

        jLabel16.setBackground(new java.awt.Color(153, 0, 51));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Đánh giá");
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel16.setFocusTraversalPolicyProvider(true);
        jLabel16.setOpaque(true);
        jPanel4.add(jLabel16);
        jLabel16.setBounds(440, 200, 120, 30);

        txtThoiLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThoiLuongActionPerformed(evt);
            }
        });
        jPanel4.add(txtThoiLuong);
        txtThoiLuong.setBounds(320, 200, 110, 30);

        tblPhim.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblPhim);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(220, 360, 540, 130);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 46, 81));
        jLabel1.setText("TÌM PHIM");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(220, 70, 100, 30);

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
        btnTimKiem.setBounds(680, 70, 100, 30);

        txtmaphim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaphimActionPerformed(evt);
            }
        });
        jPanel4.add(txtmaphim);
        txtmaphim.setBounds(340, 72, 330, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 46, 81));
        jLabel3.setText("DANH SÁCH PHIM");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(220, 330, 190, 30);
        jPanel4.add(jSeparator2);
        jSeparator2.setBounds(170, 110, 660, 10);
        jPanel4.add(jSeparator3);
        jSeparator3.setBounds(160, 320, 660, 10);

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel4.add(btnHuy);
        btnHuy.setBounds(700, 270, 72, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 17, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPhimActionPerformed
        // TODO add your handling code here:
     txtMaPhim.setEditable(false);


    }//GEN-LAST:event_txtMaPhimActionPerformed

    private void txtTenPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenPhimActionPerformed

    private void txtDanhGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDanhGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDanhGiaActionPerformed

    private void txtTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTheLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTheLoaiActionPerformed

    private void txtMoTaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMoTaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMoTaActionPerformed

    private void txtNgayPhatHanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayPhatHanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayPhatHanhActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
try {
    // ===== LẤY DỮ LIỆU =====
    String ten = txtTenPhim.getText().trim();
    String thoiLuongStr = txtThoiLuong.getText().trim();
    String danhGia = txtDanhGia.getText().trim();
    String theLoai = txtTheLoai.getText().trim();
    String moTa = txtMoTa.getText().trim();
    String ngayPhatHanh = txtNgayPhatHanh.getText().trim();

    // ===== VALIDATION =====
    if (ten.isEmpty() || thoiLuongStr.isEmpty() || theLoai.isEmpty() || ngayPhatHanh.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Không được để trống các trường bắt buộc!");
        return;
    }

    // Kiểm tra thời lượng (phải là số > 0)
    int thoiLuong;
    try {
        thoiLuong = Integer.parseInt(thoiLuongStr);
        if (thoiLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0!");
            return;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Thời lượng phải là số!");
        return;
    }

    // Kiểm tra ngày phát hành
    String ngay = txtNgayPhatHanh.getText().trim();

// REGEX kiểm tra yyyy-MM-dd
String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

if (!ngay.matches(regex)) {
    JOptionPane.showMessageDialog(this,
            "Ngày phải đúng định dạng yyyy-MM-dd (ví dụ 2023-03-09)",
            "Lỗi", JOptionPane.ERROR_MESSAGE);
    return;
}


    // ===== TẠO ĐỐI TƯỢNG PHIM =====
    Phim p = new Phim();
    p.setTenPhim(ten);
    p.setThoiLuong(thoiLuong);
    p.setDanhGia(danhGia);
    p.setTheLoai(theLoai);
    p.setMoTa(moTa);
    p.setNgayPhatHanh(ngayPhatHanh);

    // ===== INSERT DB =====
    PhimCRUD crud = new PhimCRUD();
    if (crud.insertPhim(p)) {
        JOptionPane.showMessageDialog(this, "Thêm phim thành công!");
        layDuLieu();
    } else {
        JOptionPane.showMessageDialog(this, "Thêm thất bại!");
    }

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
}

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
                                      
    try {
        // ===== LẤY DỮ LIỆU =====
        String maPhimStr = txtMaPhim.getText().trim();
        String ten = txtTenPhim.getText().trim();
        String thoiLuongStr = txtThoiLuong.getText().trim();
        String danhGia = txtDanhGia.getText().trim();
        String theLoai = txtTheLoai.getText().trim();
        String moTa = txtMoTa.getText().trim();
        String ngayPhatHanh = txtNgayPhatHanh.getText().trim();

        // ===== CHECK RỖNG =====
        if (maPhimStr.isEmpty() || ten.isEmpty() || thoiLuongStr.isEmpty() 
                || theLoai.isEmpty() || ngayPhatHanh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống các trường bắt buộc!");
            return;
        }

        // ===== CHECK MÃ PHIM =====
        int maPhim;
        try {
            maPhim = Integer.parseInt(maPhimStr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Mã phim không hợp lệ!");
            return;
        }

        // ===== CHECK THỜI LƯỢNG =====
        int thoiLuong;
        try {
            thoiLuong = Integer.parseInt(thoiLuongStr);
            if (thoiLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số!");
            return;
        }

        // ===== CHECK NGÀY =====
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

        if (!ngayPhatHanh.matches(regex)) {
            JOptionPane.showMessageDialog(this,
                "Ngày phải đúng định dạng yyyy-MM-dd (ví dụ 2023-03-09)",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        

        // ===== TẠO ĐỐI TƯỢNG =====
        Phim p = new Phim();
        p.setMaPhim(maPhim);
        p.setTenPhim(ten);
        p.setThoiLuong(thoiLuong);
        p.setDanhGia(danhGia);
        p.setTheLoai(theLoai);
        p.setMoTa(moTa);
        p.setNgayPhatHanh(ngayPhatHanh); // để nguyên chuỗi

        // ===== UPDATE DB =====
        PhimCRUD crud = new PhimCRUD();
        if (crud.updatePhim(p)) {
            JOptionPane.showMessageDialog(this, "Cập nhật phim thành công!");
            layDuLieu();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
                                     
    try {
        int maPhim = Integer.parseInt(txtMaPhim.getText());
        PhimCRUD crud = new PhimCRUD();
        if (crud.deletePhim(maPhim)) {
            JOptionPane.showMessageDialog(this, "Xóa phim thành công!");
            layDuLieu();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }

    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void txtThoiLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThoiLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThoiLuongActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
                                         
    String input = txtmaphim.getText().trim();

    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phim cần tìm!");
        return;
    }

    try {
        int maPhim = Integer.parseInt(input);

        PhimCRUD phimCRUD = new PhimCRUD();
        Phim p = phimCRUD.getPhimById(maPhim);

        if (p != null) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{
                p.getMaPhim(),
                p.getTenPhim(),
                p.getThoiLuong(),
                p.getDanhGia(),
                p.getTheLoai(),
                p.getMoTa(),
                p.getNgayPhatHanh()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phim!");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Mã phim phải là số!");
    }


    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtmaphimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaphimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmaphimActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
    txtMaPhim.setText("");
    txtTenPhim.setText("");
    txtThoiLuong.setText("");
    txtDanhGia.setText("");
    txtTheLoai.setText("");
    txtMoTa.setText("");
    txtNgayPhatHanh.setText("");

    // Bỏ chọn dòng trong JTable nếu có
    tblPhim.clearSelection();
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
        java.awt.EventQueue.invokeLater(() -> new QuanLyPhimForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JTable tblPhim;
    private javax.swing.JTextField txtDanhGia;
    private javax.swing.JLabel txtDashboard1;
    private javax.swing.JLabel txtDoAnHoaDon;
    private javax.swing.JTextField txtMaPhim;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtNgayPhatHanh;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JTextField txtTenPhim;
    private javax.swing.JTextField txtTheLoai;
    private javax.swing.JTextField txtThoiLuong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JLabel txtVeVaGheNgoi;
    private javax.swing.JTextField txtmaphim;
    // End of variables declaration//GEN-END:variables
}
