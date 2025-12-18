/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Windows
 */
public class combo {
     private int ComboID;
    private String Name;
    private String Desc;
    private double Price;

    public combo(int ComboID, String Name, String Desc, double Price) {
        this.ComboID = ComboID;
        this.Name = Name;
        this.Desc = Desc;
        this.Price = Price;
    }

   

    public int getComboID() {
        return ComboID;
    }

    public String getName() {
        return Name;
    }
 public String getDesc() {
        return Desc;
    }

    public double getPrice() {
        return Price;
    }

   
    public void setComboID(int ComboID) {
        this.ComboID = ComboID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    @Override
    public String toString() {
        return "combo{" + "ComboID=" + ComboID + ", Name=" + Name + ", Desc=" + Desc + ", Price=" + Price + '}';
    }

    
}
