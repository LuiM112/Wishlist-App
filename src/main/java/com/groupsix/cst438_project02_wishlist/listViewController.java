package com.groupsix.cst438_project02_wishlist;

import com.groupsix.cst438_project02_wishlist.entities.Item;
import com.groupsix.cst438_project02_wishlist.entities.User;
import com.groupsix.cst438_project02_wishlist.entities.Wishlist;
import com.groupsix.cst438_project02_wishlist.repositories.ItemRepository;
import com.groupsix.cst438_project02_wishlist.repositories.UserRepository;
import com.groupsix.cst438_project02_wishlist.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class listViewController {

    public static String BASE_URI = "http://localhost:8080/api/";

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(value = "/edit_wishlist")
    String view_item_list(HttpServletResponse response, HttpServletRequest request,HttpSession session, Model model) throws IOException {

        return "edit_wishlist";
    }

    @RequestMapping(value = "/addItem")
    String getAddItem(HttpServletResponse response, HttpServletRequest request,HttpSession session, Model model) throws IOException {

        return "add_item";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    String addItem (HttpServletResponse response,
                           HttpServletRequest request,
                           Model model,
                           @RequestParam String itemName,
                           @RequestParam String itemDetails) throws IOException{
        User user = ((User) request.getSession().getAttribute("User_Session"));
        Item item = new Item();

        if(!itemName.isEmpty()){
            item.setItemName(itemName);
            item.setWishlistId(user.getUserId());
            item.setItemDetails(itemDetails);
            itemRepository.save(item);
            response.sendRedirect("/edit_wishlist");
        }
        else{
            model.addAttribute("Error_Msg", "Can't leave the Item Name blank");
        }

        return "add_item";

    }
}
