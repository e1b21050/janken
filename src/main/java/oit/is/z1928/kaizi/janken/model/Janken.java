package oit.is.z1928.kaizi.janken.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class Janken {

  @GetMapping("/my-page")
  public String myPage(Model model) {

    // ビューの名前を返す
    return "my-page";
  }
}
