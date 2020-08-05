package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Category;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.CategoryRepository;
import org.kitchen.booting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @GetMapping("")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("user/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/admin/user/list";
    }

    @GetMapping("user/get/{userId}")
    public String getUser(@PathVariable("userId") String userId,Model model)
    {
        User user = userService.findByUserId(userId);
        model.addAttribute("getUser", user);
        return "/admin/user/get";
    }
    @GetMapping("user/edit/{userId}")
    public String editUser(@PathVariable("userId") String userId, Model model)
    {
        User user = userService.findByUserId(userId);
        model.addAttribute("user", user);
        return "/admin/user/edit";
    }
    @GetMapping("category/list")
    public String categoryList(Model model)
    {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        logger.info("@@카테고리리스트@@"+categoryList.toString());
        return "/admin/category/list";
    }
    @GetMapping("category/create")
    public String category(Model model)
    {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        return "/admin/category/create";
    }
}
