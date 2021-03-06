package com.groupsix.cst438_project02_wishlist;

import com.groupsix.cst438_project02_wishlist.entities.User;
import com.groupsix.cst438_project02_wishlist.entities.Wishlist;
import com.groupsix.cst438_project02_wishlist.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@SpringBootApplication
public class Cst438Project02WishlistApplication {

    @Autowired
    WishlistRepository wishlistRepository;

    @RequestMapping("/")
    String home(HttpSession session, HttpServletResponse response, Model model) throws IOException {
        User user = (User) session.getAttribute("User_Session");
        if(user == null) {
            response.sendRedirect("/landing");
        } else if (user != null) {
            List<Wishlist> wishlist = wishlistRepository.findByUserId(user.getUserId());
            if (wishlist != null) {
                model.addAttribute("wishlists", wishlist);
            }
        }
        model.addAttribute("user", user);
        return "home";
    }


    @RequestMapping(value = "/name")
    @ResponseBody
    String name() {
        return "name";
    }

    @RequestMapping(value = "/landing")
    String landing_page() {
        return "landing_page";
    }


    public static void main(String[] args) {
        SpringApplication.run(Cst438Project02WishlistApplication.class, args);
    }

}
