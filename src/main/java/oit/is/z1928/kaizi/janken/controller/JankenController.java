package oit.is.z1928.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import oit.is.z1928.kaizi.janken.model.User;
import oit.is.z1928.kaizi.janken.model.UserMapper;
import oit.is.z1928.kaizi.janken.model.Match;
import oit.is.z1928.kaizi.janken.model.MatchInfo;
import oit.is.z1928.kaizi.janken.model.MatchInfoMapper;
import oit.is.z1928.kaizi.janken.model.MatchMapper;
//import oit.is.z1928.kaizi.janken.model.Janken;
import oit.is.z1928.kaizi.janken.service.AsyncKekka;

@Controller
public class JankenController {
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka asyncKekka;

  // private Janken janken = new Janken();

  @GetMapping("/janken")
  @Transactional
  public String JankenPage(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllByUsersName();
    model.addAttribute("users", users);
    ArrayList<MatchInfo> matchinfos = matchInfoMapper.selectByMatchInfo();
    model.addAttribute("matchinfos", matchinfos);
    ArrayList<Match> matches = matchMapper.selectAllByMatchs();
    model.addAttribute("matches", matches);
    return "janken";
  }

  @GetMapping("/match")
  public String MatchPage(@RequestParam Integer id, ModelMap model) {
    User users = userMapper.selectById(id);
    model.addAttribute("users", users);
    return "match";
  }

  @GetMapping("/fight")
  @Transactional
  public String FightPage(@RequestParam Integer id, @RequestParam String hand, ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);

    MatchInfo matchinfo = new MatchInfo();
    matchinfo = matchInfoMapper.selectById(user.getId());
    if (matchinfo != null) {
      Match match = new Match();
      match = asyncKekka.setMatches(user.getId(), id, matchinfo.getUser1Hand(), hand);
      matchInfoMapper.updateMatchInfo(matchinfo);
    } else {
      MatchInfo matchinfo2 = new MatchInfo();
      matchinfo2.setUser1(user.getId());
      matchinfo2.setUser2(id);
      matchinfo2.setUser1Hand(hand);
      matchInfoMapper.insertMatchInfo(matchinfo2);
    }

    return "wait";
  }

  @GetMapping("/result")
  public SseEmitter fight() {
    SseEmitter emitter = new SseEmitter();
    asyncKekka.asyncShowKekka(emitter);
    return emitter;
  }

}
