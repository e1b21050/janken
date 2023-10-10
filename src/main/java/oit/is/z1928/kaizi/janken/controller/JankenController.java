package oit.is.z1928.kaizi.janken.controller;

import oit.is.z1928.kaizi.janken.model.Janken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {
  private Janken janken = new Janken();
  private int cnt_game = 0;
  private int cnt_win = 0;
  private int cnt_draw = 0;
  private int cnt_lose = 0;

  @GetMapping("/index")
  public String indexPage() {
    return "janken";
  }

  @PostMapping("/janken")
  public String JankenPage(@RequestParam("username") String username, ModelMap model) {
    janken.setUserName(username);
    model.addAttribute("username", janken.getUserName());
    return "janken";
  }

  @GetMapping("/janken")
  public String jankenPage() {
    return "janken";
  }

  @GetMapping("/jankengame")
  public String jankengamePage(@RequestParam("choice") String choice,
      ModelMap model) {
    janken.setUserChoice(choice);
    String cpuChoice = janken.randomHand();
    janken.setCpuChoice(cpuChoice);
    String result = janken.detResult(janken.getUserChoice(), janken.getCpuChoice());
    cnt_game++;
    if (result.equals("You Win!")) {
      cnt_win++;
    } else if (result.equals("You Lose!")) {
      cnt_lose++;
    } else if (result.equals("Draw!")) {
      cnt_draw++;
    }
    model.addAttribute("choice", janken.getUserChoice());
    model.addAttribute("cpu_choice", janken.getCpuChoice());
    model.addAttribute("result", result);
    model.addAttribute("cnt_game", cnt_game);
    model.addAttribute("cnt_win", cnt_win);
    model.addAttribute("cnt_lose", cnt_lose);
    model.addAttribute("cnt_draw", cnt_draw);

    return "janken";
  }
}
