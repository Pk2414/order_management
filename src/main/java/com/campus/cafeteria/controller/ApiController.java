package com.campus.cafeteria.controller;

import com.campus.cafeteria.model.*;
import com.campus.cafeteria.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController @RequestMapping("/api")
public class ApiController {
    private final UserRepository users; private final MenuRepository menu; private final OrderRepository orders; private final PasswordEncoder encoder;
    public ApiController(UserRepository users,MenuRepository menu,OrderRepository orders,PasswordEncoder encoder){this.users=users;this.menu=menu;this.orders=orders;this.encoder=encoder;}
    public record AuthRequest(@NotBlank @Size(max=80) String name,@NotBlank @Email @Size(max=160) String email,@NotBlank @Size(min=8,max=72) String password){}
    public record LoginRequest(@NotBlank @Email String email,@NotBlank String password){}
    public record OrderRequest(List<OrderItemRequest> items){}
    public record OrderItemRequest(Long itemId,int quantity){}
    private Map<String,Object> userView(CafeUser u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail());}
    @PostMapping("/auth/signup") public Map<String,Object> signup(@Valid @RequestBody AuthRequest req,HttpSession session){
        String email=req.email().trim().toLowerCase(Locale.ROOT); if(users.findByEmailIgnoreCase(email).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"An account with this email already exists.");
        CafeUser u=users.save(new CafeUser(req.name().trim(),email,encoder.encode(req.password())));session.setAttribute("userId",u.getId());return userView(u);
    }
    @PostMapping("/auth/login") public Map<String,Object> login(@Valid @RequestBody LoginRequest req,HttpSession session){
        CafeUser u=users.findByEmailIgnoreCase(req.email().trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password is incorrect."));
        if(!encoder.matches(req.password(),u.getPassword()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password is incorrect.");session.setAttribute("userId",u.getId());return userView(u);
    }
    @GetMapping("/auth/me") public Map<String,Object> me(HttpSession session){return userView(currentUser(session));}
    @PostMapping("/auth/logout") public Map<String,String> logout(HttpSession session){session.invalidate();return Map.of("message","Signed out");}
    @GetMapping("/menu") public List<MenuItem> getMenu(){return menu.findAll();}
    @PostMapping("/orders") public Map<String,Object> createOrder(@RequestBody OrderRequest request,HttpSession session){
        CafeUser user=currentUser(session);if(request==null||request.items()==null||request.items().isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Your cart is empty.");
        CafeOrder order=new CafeOrder(user);for(OrderItemRequest line:request.items()){if(line.quantity()<1||line.quantity()>20)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Each item quantity must be between 1 and 20.");MenuItem item=menu.findById(line.itemId()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"A menu item could not be found."));order.addItem(item,line.quantity());}
        return orderView(orders.save(order));
    }
    @GetMapping("/orders") public List<Map<String,Object>> getOrders(HttpSession session){return orders.findByUserIdOrderByCreatedAtDesc(currentUser(session).getId()).stream().map(this::orderView).toList();}
    private Map<String,Object> orderView(CafeOrder o){return Map.of("id",o.getId(),"createdAt",o.getCreatedAt(),"status",o.getStatus(),"total",o.getTotal(),"items",o.getItems().stream().map(i->Map.of("name",i.getMenuItem().getName(),"quantity",i.getQuantity(),"unitPrice",i.getUnitPrice())).toList());}
    private CafeUser currentUser(HttpSession session){Object id=session.getAttribute("userId");if(id==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in to continue.");return users.findById((Long)id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in to continue."));}
    @ExceptionHandler(ResponseStatusException.class) public ResponseEntity<Map<String,String>> handle(ResponseStatusException ex){return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message",ex.getReason()==null?"Something went wrong.":ex.getReason()));}
}
