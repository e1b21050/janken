package oit.is.z1928.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches")
  ArrayList<Match> selectAllByMatchs();

  @Select("SELECT * FROM matches WHERE isActive = true")
  Match selectByActive();

  @Insert("INSERT INTO matches( user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},true);")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Update("UPDATE matches SET isActive = false")
  void updateMatch(Match match);

  @Update("UPDATE matches SET isActive = false WHERE user1=#{user1} and user2=#{user2} and isActive = true")
  void updateMatchByusers(int user1, int user2);
}
