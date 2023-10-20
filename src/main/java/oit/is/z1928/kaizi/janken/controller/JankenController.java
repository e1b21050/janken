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
import oit.is.z1928.kaizi.janken.model.Match;
import oit.is.z1928.kaizi.janken.model.MatchMapper;
import oit.is.z1928.kaizi.janken.model.Janken;

@Controller
public class JankenController {
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

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
    ArrayList<Match> matches = matchMapper.selectAllByMatchs();
    model.addAttribute("matches", matches);
    return "janken";
  }

  @GetMapping("/match")
  public String MatchPage(@RequestParam("id") int user_id, ModelMap model) {
    User users = userMapper.selectById(user_id);
    model.addAttribute("users", users);
    return "match";
  }
}
