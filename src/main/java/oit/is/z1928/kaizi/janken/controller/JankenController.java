package oit.is.z1928.kaizi.janken.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import oit.is.z1928.kaizi.janken.model.Entry;

@Controller
public class JankenController {
  @Autowired
  private Entry entry;

  /**
   *
   * @param model Thymeleafにわたすデータを保持するオブジェクト
   * @param prin  ログインユーザ情報が保持されるオブジェクト
   * @return
   */
  @GetMapping("/janken")
  public String JankenPage(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("username", loginUser);
    return "janken";
  }

  /*
   * @PostMapping("/janken")
   * public String jankenPage(Principal prin, ModelMap model) {
   * String loginUser = prin.getName();
   * Entry newEntry = new Entry();
   * newEntry.addUser(loginUser);
   * model.addAttribute("new_entry", newEntry);
   * model.addAttribute("username", loginUser);
   * return "janken";
   * }
   */

  @GetMapping("/jankengame")
  public String jankengamePage(@RequestParam("choice") String choice,
      ModelMap model) {
    return "janken";
  }
}
