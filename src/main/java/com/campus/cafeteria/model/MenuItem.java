package com.campus.cafeteria.model;

import jakarta.persistence.*;

@Entity @Table(name = "menu_items")
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name; private String description; private String category; private double price; private String emoji; private boolean vegetarian;
    protected MenuItem() {}
    public MenuItem(String name,String description,String category,double price,String emoji,boolean vegetarian){this.name=name;this.description=description;this.category=category;this.price=price;this.emoji=emoji;this.vegetarian=vegetarian;}
    public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;} public String getCategory(){return category;} public double getPrice(){return price;} public String getEmoji(){return emoji;} public boolean isVegetarian(){return vegetarian;}
}
