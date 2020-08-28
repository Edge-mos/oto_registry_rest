package ru.autoins.oto_registry_rest.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistryApiController {

    @RequestMapping("/admin")
    public String mainPage(Model model) {
        return "admin_page";
    }
}
