/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import CRUD.ConnectSQL;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class TrangChuForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TrangChuForm.class.getName());

    private DefaultTableModel tableModel;
    public TrangChuForm() {
    initComponents();

    this.setSize(830, 540);    // chỉnh kích thước phù hợp giao diện
    this.setLocationRelativeTo(null);
    this.setResizable(false);   // tránh form tự thu/phóng gây lệch vị trí
    loadDuLieu();
    gancn();
    }
    public void loadDuLieu(){
        
        String sql = "SELECT COUNT(*) AS TongSoVeTrongNgay "
           + "FROM HoaDon "
           + "WHERE CAST(NgayDat AS DATE) = CAST(GETDATE() AS DATE)";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int tongVe = rs.getInt("TongSoVeTrongNgay");
                lbVeBan.setText(String.valueOf(tongVe)); // Gắn vào label
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        sql = """
              SELECT SUM(TongTien) AS DoanhThu_ThangHienTai
              FROM HoaDon
              WHERE MONTH(NgayDat) = MONTH(GETDATE())
                AND YEAR(NgayDat) = YEAR(GETDATE());""";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int doanhThu = rs.getInt("DoanhThu_ThangHienTai");
                lbDoanhThu.setText(String.valueOf(doanhThu)); // Gắn vào label
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        sql = """
              SELECT COUNT(*) AS SoSuatChieuDangMo
                FROM SuatChieu
                WHERE TrangThai = N'OPEN';""";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int soSuatChieu = rs.getInt("SoSuatChieuDangMo");
                lbSuatChieu.setText(String.valueOf(soSuatChieu)); // Gắn vào label
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = """
              SELECT COUNT(DISTINCT MaNguoiDung) AS KhachHangDaMuaVe
                FROM HoaDon;""";

        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int soKH = rs.getInt("KhachHangDaMuaVe");
                lbKhachHang.setText(String.valueOf(soKH)); // Gắn vào label
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tableModel.addColumn("TOP");
        tableModel.addColumn("Tên phim");
        tableModel.addColumn("Doanh thu");
        sql = """
              SELECT TOP 5
                    P.TenPhim,
                    SUM(HD.TongTien) AS TongDoanhThu
                FROM HoaDon HD
                JOIN Ticket T ON HD.MaVe = T.MaVe
                JOIN Phim P ON T.MaPhim = P.MaPhim
                GROUP BY P.TenPhim
                ORDER BY TongDoanhThu DESC;""";
        int i = 1;
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
//                SuatChieu sc = new SuatChieu();
//                sc.setMaSuatChieu(rs.getInt("MaSuatChieu"));
//                sc.setMaPhim(rs.getInt("MaPhim"));
//                sc.setBatDau(rs.getTimestamp("BatDau").toLocalDateTime());
//                sc.setKetThuc(rs.getTimestamp("KetThuc").toLocalDateTime());
//                sc.setTrangThai(rs.getString("TrangThai"));
                tableModel.addRow(new Object[]{
                    i,
                    rs.getString("TenPhim"),
                    rs.getInt("TongDoanhThu"),
                });
                i++;

            }
            tbPhimDoanhThu.setModel(tableModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
             setIconImage(new ImageIcon(getClass().getResource("/image/ava-01.png")).getImage());
        FlatSVGIcon iconTicket = new FlatSVGIcon("image/ticket.svg", 35, 35);
        FlatSVGIcon iconCoin = new FlatSVGIcon("image/coin.svg", 30, 30);
        FlatSVGIcon iconFilm = new FlatSVGIcon("image/film.svg", 30, 30);
        FlatSVGIcon iconCustomer = new FlatSVGIcon("image/customer.svg", 30, 30);
        FlatSVGIcon iconAdmin = new FlatSVGIcon("image/admin.svg", 50, 50);
        lbAdmin.setIcon(iconAdmin);
        lbVeBan.setIcon(iconTicket);
        lbDoanhThu.setIcon(iconCoin);
        lbSuatChieu.setIcon(iconFilm);
        lbKhachHang.setIcon(iconCustomer);
      
    }
    
    // Tạo hiệu ứng hover cho 1 label menu bên trái
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
    txtDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // hiện bàn tay

txtDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        txtDashboard.setForeground(java.awt.Color.RED); // hover đỏ
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        txtDashboard.setForeground(java.awt.Color.WHITE); // rời chuột trắng lại
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


    // gán chức năng cho label
  // gán chức năng cho label


    // ==========================
    // 2️⃣ Có thể thêm label khác ở đây sau này
    // ==========================
    // txtKhac.setCursor(new Cursor(Cursor.HAND_CURSOR));
    // txtKhac.addMouseListener(new MouseAdapter() { ... });


    
    
    //gán chức năng nút
    public void gancnbtn(){
        //..............
        
        // ....................
        
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
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtPhimVaSuatChieu = new javax.swing.JLabel();
        txtVeVaGheNgoi = new javax.swing.JLabel();
        txtDoAnHoaDon = new javax.swing.JLabel();
        txtThongKeBaoCao = new javax.swing.JLabel();
        txtQuanTriHeThong = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        txtDashboard = new javax.swing.JLabel();
        lbAdmin = new javax.swing.JLabel();
        btnSignOut = new javax.swing.JButton();
        lbDoanhThu = new javax.swing.JLabel();
        lbKhachHang = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbVeBan = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbSuatChieu = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhimDoanhThu = new javax.swing.JTable();
        btnExit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(810, 520));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel6.setFont(new java.awt.Font("UTM Amerika Sans", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("myGalaxyCinema");

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

        txtDashboard.setBackground(new java.awt.Color(0, 0, 0));
        txtDashboard.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtDashboard.setForeground(new java.awt.Color(255, 255, 255));
        txtDashboard.setText("Dashboard");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuanTriHeThong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThongKeBaoCao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhimVaSuatChieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVeVaGheNgoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoAnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel6)
                .addGap(52, 52, 52)
                .addComponent(txtDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPhimVaSuatChieu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVeVaGheNgoi)
                .addGap(12, 12, 12)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDoAnHoaDon)
                .addGap(14, 14, 14)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThongKeBaoCao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtQuanTriHeThong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(-10, -10, 190, 510);

        btnSignOut.setBackground(new java.awt.Color(153, 0, 51));
        btnSignOut.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSignOut.setForeground(new java.awt.Color(255, 255, 255));
        btnSignOut.setText("Sign out");
        btnSignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignOutActionPerformed(evt);
            }
        });
        jPanel1.add(btnSignOut);
        btnSignOut.setBounds(700, 10, 80, 30);

        lbDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbDoanhThu.setText("20.000.000 đ");
        jPanel1.add(lbDoanhThu);
        lbDoanhThu.setBounds(570, 90, 160, 30);

        lbKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbKhachHang.setText("1,500");
        jPanel1.add(lbKhachHang);
        lbKhachHang.setBounds(580, 200, 110, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Doanh thu tháng này");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(570, 120, 160, 20);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Khách hàng");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(580, 230, 190, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Vé đã bán hôm nay");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(270, 120, 130, 20);

        lbVeBan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbVeBan.setText("200");
        jPanel1.add(lbVeBan);
        lbVeBan.setBounds(310, 90, 90, 30);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Suất chiếu đang hoạt động");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(260, 230, 190, 40);

        lbSuatChieu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbSuatChieu.setText("10");
        jPanel1.add(lbSuatChieu);
        lbSuatChieu.setBounds(310, 210, 80, 30);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Top 5 phim theo doanh thu");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(240, 280, 190, 30);

        jTextField1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jTextField1MouseDragged(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(240, 70, 210, 90);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(480, 180, 290, 90);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(480, 70, 290, 90);
        jPanel1.add(jTextField5);
        jTextField5.setBounds(240, 180, 210, 90);

        jTextField6.setText("         Đã có 5 vé bị hủy hôm nay");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField6);
        jTextField6.setBounds(280, 460, 440, 30);

        tbPhimDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPhimDoanhThu.setShowGrid(true);
        jScrollPane1.setViewportView(tbPhimDoanhThu);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(240, 310, 530, 130);

        btnExit.setBackground(new java.awt.Color(153, 0, 51));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jPanel1.add(btnExit);
        btnExit.setBounds(600, 10, 80, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(835, 835, 835)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(648, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(538, 538, 538))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignOutActionPerformed
        // TODO add your handling code here:
         SignInnForm login = new SignInnForm();
    login.setLocationRelativeTo(null); // hiện giữa màn hình
    login.setVisible(true);

    // Đóng form Dashboard hiện tại
    this.dispose();
    }//GEN-LAST:event_btnSignOutActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseDragged


    }//GEN-LAST:event_jTextField1MouseDragged

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn thoát không?",
            "Xác nhận thoát",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0);
    }
    }//GEN-LAST:event_btnExitActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TrangChuForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JLabel lbDoanhThu;
    private javax.swing.JLabel lbKhachHang;
    private javax.swing.JLabel lbSuatChieu;
    private javax.swing.JLabel lbVeBan;
    private javax.swing.JTable tbPhimDoanhThu;
    private javax.swing.JLabel txtDashboard;
    private javax.swing.JLabel txtDoAnHoaDon;
    private javax.swing.JLabel txtPhimVaSuatChieu;
    private javax.swing.JLabel txtQuanTriHeThong;
    private javax.swing.JLabel txtThongKeBaoCao;
    private javax.swing.JLabel txtVeVaGheNgoi;
    // End of variables declaration//GEN-END:variables
}
