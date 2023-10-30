package oit.is.z1928.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * FROM matchinfo WHERE isActive = true;")
  ArrayList<MatchInfo> selectByMatchInfo();

  @Select("SELECT * FROM matchinfo WHERE isActive=true and id = #{id}")
  MatchInfo selectById(int id);

  @Select("SELECT * FROM matchinfo WHERE isActive=true and user2 = #{id}")
  MatchInfo selectByUser2(int id);

  @Insert("INSERT INTO matchinfo(user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},true);")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("UPDATE matchinfo SET isActive = false")
  void updateMatchInfo(MatchInfo matchInfo);

}
