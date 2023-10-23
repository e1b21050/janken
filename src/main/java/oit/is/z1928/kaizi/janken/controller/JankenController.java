package oit.is.z1928.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
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
  public String MatchPage(@RequestParam Integer id, ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    model.addAttribute("username", loginUser);
    User users = userMapper.selectById(id);
    model.addAttribute("users", users);
    return "match";
  }

  @GetMapping("/fight")
  public String FightPage(@RequestParam Integer id, @RequestParam String hand, ModelMap model, Principal prin) {
    User users = userMapper.selectById(id);
    model.addAttribute("users", users);
    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);
    model.addAttribute("username", loginUser);
    String cpuHand = janken.randomHand();
    String result = janken.detResult(hand, cpuHand);
    Match match = new Match();
    match.setUser1(user.getId());
    match.setUser2(id);
    match.setUser1Hand(hand);
    match.setUser2Hand(cpuHand);
    matchMapper.insertMatchInfo(match);
    model.addAttribute("match", match);
    model.addAttribute("user1Hand", hand);
    model.addAttribute("user2Hand", cpuHand);
    model.addAttribute("result", result);
    return "match";
  }
}
