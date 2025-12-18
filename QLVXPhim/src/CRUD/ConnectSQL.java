package CRUD;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectSQL {

    public static Connection getConnection() {
     
        String server = "DESKTOP-M778R8J";
        String user = "sa";
        String pass = "123456789";
        String db = "QLRapPhim";  
        int port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource(); 
        ds.setServerName(server);
        ds.setUser(user);
        ds.setPassword(pass);
        ds.setDatabaseName(db);
        ds.setPortNumber(port);
        ds.setTrustServerCertificate(true);
        ds.setEncrypt("false"); 

       
        try {
            Connection conn = ds.getConnection();
            System.out.println("✅ Kết nối thành công SQL Server!");
            return conn;
        } catch (SQLServerException ex) {
            System.err.println("❌ Lỗi kết nối SQL Server: " + ex.getMessage());
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void main(String[] args) {
        Connection c = getConnection();
        if (c != null) {
            try {
                c.close();
                System.out.println("🔒 Đã đóng kết nối!");
            } catch (SQLException e) {
                System.err.println("⚠️ Lỗi khi đóng kết nối: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Kết nối trả về null – xem lại server/user/pass/db/port.");
        }
    }
}
    