package com.campus.cafeteria.model;

import jakarta.persistence.*;

@Entity @Table(name = "cafe_users")
public class CafeUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String password;
    protected CafeUser() {}
    public CafeUser(String name, String email, String password) { this.name=name; this.email=email; this.password=password; }
    public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPassword(){return password;}
}
