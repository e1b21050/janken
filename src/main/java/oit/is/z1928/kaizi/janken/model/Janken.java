package oit.is.z1928.kaizi.janken.model;

import java.util.Random;

public class Janken {
  private String userName;
  private String userChoice;
  private String cpuChoice;

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserChoice() {
    return this.userChoice;
  }

  public void setUserChoice(String userChoice) {
    this.userChoice = userChoice;
  }

  public String getCpuChoice() {
    return this.cpuChoice;
  }

  public void setCpuChoice(String cpuChoice) {
    this.cpuChoice = cpuChoice;
  }

  public String randomHand() {
    Random random = new Random();
    int num = random.nextInt(3);
    if (num == 0) {
      return "Gu";
    } else if (num == 1) {
      return "Ch";
    } else {
      return "Pa";
    }
  }

  public String detResult(String userChoice, String cpuChoice) {
    if ((userChoice.equals("Pa") && cpuChoice.equals("Gu")) || (userChoice.equals("Gu") && cpuChoice.equals("Ch"))
        || (userChoice.equals("Ch") && cpuChoice.equals("Pa"))) {
      return "You Win!";
    } else if (userChoice.equals(cpuChoice)) {
      return "Draw!";
    }
    return "You Lose!";
  }

}
