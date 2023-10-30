package oit.is.z1928.kaizi.janken.service;

//import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1928.kaizi.janken.model.Match;
import oit.is.z1928.kaizi.janken.model.MatchInfo;
import oit.is.z1928.kaizi.janken.model.MatchInfoMapper;
import oit.is.z1928.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  public Match syncShowMatch() {
    return matchMapper.selectByActive();
  }

  @Async
  public void asyncShowKekka(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (dbUpdated) {// 無限ループ
        // DBが更新されたのでリストを取得
        Match match = matchMapper.selectByActive();

        // ブラウザに送信
        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(1000);
        // DBが更新されていないことを共有する
        dbUpdated = false;
        MatchInfo matchinfo = new MatchInfo();
        matchInfoMapper.updateMatchInfo(matchinfo);
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }

}
