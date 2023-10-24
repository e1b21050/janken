package oit.is.z1928.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * from matcheinfo")
  ArrayList<Match> selectAllByMatchInfo();

}
