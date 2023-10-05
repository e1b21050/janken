package oit.is.z1928.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String jankenPage(@RequestParam("username") String username, Model model) {
    model.addAttribute("username", username);
    return "janken";
  }
}
