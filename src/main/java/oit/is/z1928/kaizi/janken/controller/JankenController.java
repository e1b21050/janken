package oit.is.z1928.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import oit.is.z1928.kaizi.janken.model.User;
import oit.is.z1928.kaizi.janken.model.UserMapper;
import oit.is.z1928.kaizi.janken.model.Janken;

@Controller
public class JankenController {
  @Autowired
  UserMapper userMapper;

  private Janken janken = new Janken();

  /**
   *
   * @param model Thymeleafにわたすデータを保持するオブジェクト
   * @param prin  ログインユーザ情報が保持されるオブジェクト
   * @return
   */
  @GetMapping("/janken")
  public String JankenPage(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    model.addAttribute("username", loginUser);
    ArrayList<User> users = userMapper.selectAllByUsersName();
    model.addAttribute("users", users);
    return "janken";
  }

  /*
   * @GetMapping("/jankengame")
   * public String jankengamePage(@RequestParam("choice") String choice,
   * ModelMap model) {
   * janken.setUserChoice(choice);
   * String cpuChoice = janken.randomHand();
   * janken.setCpuChoice(cpuChoice);
   * String result = janken.detResult(janken.getUserChoice(),
   * janken.getCpuChoice());
   * model.addAttribute("choice", janken.getUserChoice());
   * model.addAttribute("cpu_choice", janken.getCpuChoice());
   * model.addAttribute("result", result);
   * return "janken";
   * }
   */
}
