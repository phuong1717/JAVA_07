package CRUD;

import DAO.TicketDAO;
import model.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketCRUD {

    private TicketDAO ticketDAO;

    public TicketCRUD() {
        this.ticketDAO = new TicketDAO();
    }

    // Lấy tất cả vé
    public ArrayList<Ticket> getAllTickets() {
        List<Ticket> list = ticketDAO.getAll();
        return new ArrayList<>(list);
    }

    // Thêm vé mới - KHÔNG kiểm tra trùng MaVe vì nó tự động tăng
    public boolean addTicket(Ticket ticket) {
        // Validation dữ liệu
        if (ticket.getTenPhim() == null || ticket.getTenPhim().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phim không được để trống!");
        }
        if (ticket.getGiaVe() <= 0) {
            throw new IllegalArgumentException("Giá vé phải lớn hơn 0!");
        }
        if (ticket.getGhe() == null || ticket.getGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Ghế không được để trống!");
        }
        
        return ticketDAO.insert(ticket);
    }

    // THÊM PHƯƠNG THỨC MỚI: Thêm vé và trả về mã vé với mô tả tự động
    public int addTicketWithAutoDescription(Ticket ticket) {
        // Validation dữ liệu
        if (ticket.getTenPhim() == null || ticket.getTenPhim().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phim không được để trống!");
        }
        if (ticket.getGiaVe() <= 0) {
            throw new IllegalArgumentException("Giá vé phải lớn hơn 0!");
        }
        if (ticket.getGhe() == null || ticket.getGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Ghế không được để trống!");
        }
        
        // Gọi DAO để thêm vé và lấy mã vé mới
        int maVeMoi = ticketDAO.insertAndGetId(ticket);
        
        // Nếu thêm thành công, cập nhật mô tả tự động
        if (maVeMoi > 0) {
            // Tạo mô tả tự động: "Vé + mã vé"
            String moTaAuto = "Vé " + maVeMoi;
            
            // Cập nhật lại vé với mô tả tự động
            Ticket ticketUpdated = new Ticket();
            ticketUpdated.setMaVe(maVeMoi);
            ticketUpdated.setTenPhim(ticket.getTenPhim());
            ticketUpdated.setSuatChieu(ticket.getSuatChieu());
            ticketUpdated.setGhe(ticket.getGhe());
            ticketUpdated.setGiaVe(ticket.getGiaVe());
            ticketUpdated.setMoTa(moTaAuto);
            
            ticketDAO.update(ticketUpdated);
        }
        
        return maVeMoi;
    }

    // Cập nhật vé
    public boolean updateTicket(Ticket ticket) {
        // Kiểm tra vé có tồn tại không
        Ticket existing = ticketDAO.getByMaVe(ticket.getMaVe());
        if (existing == null) {
            throw new IllegalArgumentException("Vé mã " + ticket.getMaVe() + " không tồn tại!");
        }
        
        // Validation dữ liệu
        if (ticket.getTenPhim() == null || ticket.getTenPhim().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phim không được để trống!");
        }
        if (ticket.getGiaVe() <= 0) {
            throw new IllegalArgumentException("Giá vé phải lớn hơn 0!");
        }
        if (ticket.getGhe() == null || ticket.getGhe().trim().isEmpty()) {
            throw new IllegalArgumentException("Ghế không được để trống!");
        }
        
        return ticketDAO.update(ticket);
    }

    // Xóa vé
    public boolean deleteTicket(int maVe) {
        // Kiểm tra vé có tồn tại không
        Ticket existing = ticketDAO.getByMaVe(maVe);
        if (existing == null) {
            throw new IllegalArgumentException("Vé mã " + maVe + " không tồn tại!");
        }
        
        return ticketDAO.delete(maVe);
    }

    // Tìm vé theo mã
    public Ticket getTicketByMaVe(int maVe) {
        return ticketDAO.getByMaVe(maVe);
    }

    // Tìm kiếm vé
    public ArrayList<Ticket> searchTickets(String keyword) {
        ArrayList<Ticket> allTickets = getAllTickets();
        ArrayList<Ticket> result = new ArrayList<>();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return allTickets;
        }
        
        String searchTerm = keyword.toLowerCase().trim();
        
        for (Ticket ticket : allTickets) {
            if (String.valueOf(ticket.getMaVe()).contains(searchTerm) ||
                ticket.getTenPhim().toLowerCase().contains(searchTerm) ||
                ticket.getSuatChieu().toLowerCase().contains(searchTerm) ||
                ticket.getGhe().toLowerCase().contains(searchTerm) ||
                String.valueOf(ticket.getGiaVe()).contains(searchTerm) ||
                (ticket.getMoTa() != null && ticket.getMoTa().toLowerCase().contains(searchTerm))) {
                result.add(ticket);
            }
        }
        return result;
    }

    // Kiểm tra mã vé có tồn tại không
    public boolean isMaVeExists(int maVe) {
        return ticketDAO.getByMaVe(maVe) != null;
    }

    // Kiểm tra kết nối
    public boolean testConnection() {
        return ticketDAO.testConnection();
    }
    
    // Lấy danh sách vé dạng Object[] cho combobox
    public List<Object[]> getAllVe() {
        List<Object[]> result = new ArrayList<>();
        ArrayList<Ticket> tickets = getAllTickets();
        
        for (Ticket ticket : tickets) {
            Object[] ve = new Object[3];
            ve[0] = ticket.getMaVe();
            ve[1] = ticket.getTenPhim() + " - " + ticket.getSuatChieu();
            ve[2] = ticket.getGiaVe();
            result.add(ve);
        }
        return result;
    }

    // Lấy giá vé theo mã vé
    public double getGiaVe(int maVe) {
        Ticket ticket = getTicketByMaVe(maVe);
        return ticket != null ? ticket.getGiaVe() : 0;
    }
}