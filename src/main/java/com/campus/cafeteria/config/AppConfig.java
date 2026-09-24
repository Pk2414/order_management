package com.campus.cafeteria.config;

import com.campus.cafeteria.model.MenuItem;
import com.campus.cafeteria.repository.MenuRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean CommandLineRunner seedMenu(MenuRepository menu){return args->{if(menu.count()==0){
        menu.save(new MenuItem("Masala Dosa","Crispy dosa, potato masala & coconut chutney","Breakfast",65,"🥞",true));
        menu.save(new MenuItem("Veggie Sandwich","Grilled garden vegetables & mint spread","Snacks",55,"🥪",true));
        menu.save(new MenuItem("Paneer Rice Bowl","Spiced paneer, rice & fresh salad","Meals",110,"🍛",true));
        menu.save(new MenuItem("Classic Burger","Crispy chicken, lettuce & house sauce","Snacks",95,"🍔",false));
        menu.save(new MenuItem("Chole Kulche","Slow cooked chickpeas with soft kulche","Meals",85,"🫓",true));
        menu.save(new MenuItem("Cold Coffee","Chilled coffee blended with creamy milk","Drinks",50,"🥤",true));
        menu.save(new MenuItem("Samosa (2 pcs)","Golden pastry with a spiced potato filling","Snacks",30,"🥟",true));
        menu.save(new MenuItem("Fresh Lime Soda","Fresh lime, soda & a hint of mint","Drinks",35,"🍋",true));
    }};}
}
