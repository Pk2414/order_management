package com.campus.cafeteria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "cafe_orders")
public class CafeOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) private CafeUser user;
    @Column(nullable = false) private LocalDateTime createdAt;
    @Column(nullable = false) private String status;
    @Column(nullable = false) private double total;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) private List<OrderLine> items = new ArrayList<>();
    protected CafeOrder() {}
    public CafeOrder(CafeUser user){this.user=user;this.createdAt=LocalDateTime.now();this.status="Received";}
    public void addItem(MenuItem item,int quantity){items.add(new OrderLine(this,item,quantity,item.getPrice()));total+=item.getPrice()*quantity;}
    public Long getId(){return id;} public LocalDateTime getCreatedAt(){return createdAt;} public String getStatus(){return status;} public double getTotal(){return total;} public List<OrderLine> getItems(){return items;}
}
