package com.campus.cafeteria.model;

import jakarta.persistence.*;

@Entity @Table(name = "order_lines")
public class OrderLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) private CafeOrder order;
    @ManyToOne(optional = false) private MenuItem menuItem;
    private int quantity; private double unitPrice;
    protected OrderLine() {}
    public OrderLine(CafeOrder order,MenuItem menuItem,int quantity,double unitPrice){this.order=order;this.menuItem=menuItem;this.quantity=quantity;this.unitPrice=unitPrice;}
    public MenuItem getMenuItem(){return menuItem;} public int getQuantity(){return quantity;} public double getUnitPrice(){return unitPrice;}
}
