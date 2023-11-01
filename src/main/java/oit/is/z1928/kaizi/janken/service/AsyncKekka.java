package oit.is.z1928.kaizi.janken.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1928.kaizi.janken.model.Match;
//import oit.is.z1928.kaizi.janken.model.MatchInfo;
import oit.is.z1928.kaizi.janken.model.MatchInfoMapper;
import oit.is.z1928.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;
  int count = 0;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  public Match syncShowMatch() {
    return matchMapper.selectByActive();
  }

  @Transactional
  public Match setMatches(int user1, int user2, String user1Hand, String user2Hand) {
    Match match = new Match();
    match.setUser1(user1);
    match.setUser2(user2);
    match.setUser1Hand(user1Hand);
    match.setUser2Hand(user2Hand);
    match.setIsActive(true);
    this.dbUpdated = true;
    matchMapper.insertMatch(match);
    match = matchMapper.selectByActive();
    return match;
  }

  @Async
  public void asyncShowKekka(SseEmitter emitter) {
    try {
      while (true) {
        if (dbUpdated == false) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        Match match = this.syncShowMatch();
        emitter.send(match);
        count++;
        if (count == 2) {
          dbUpdated = false;
          matchMapper.updateMatchByusers(match.getUser1(), match.getUser2());
          count = 0;
        }
        TimeUnit.MILLISECONDS.sleep(10000);
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }

}
