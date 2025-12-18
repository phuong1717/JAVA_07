package model;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String sdt;
    private String role;

    
    public User(String username, String password, String email, String sdt, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sdt = sdt;
        this.role = role;
    }

  
    public User(int id, String username, String password, String email, String sdt, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.sdt = sdt;
        this.role = role;
    }

    
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getSdt() { return sdt; }
    public String getRole() { return role; }

   
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username +
                ", password=" + password + ", email=" + email + 
                ", sdt=" + sdt + ", role=" + role + '}';
    }

    
}
