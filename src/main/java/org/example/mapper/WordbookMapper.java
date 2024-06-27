package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Row;
import org.example.pojo.Word;
import org.example.pojo.Wordbook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author a1380
* @description 针对表【wordbook】的数据库操作Mapper
* @createDate 2024-06-20 16:36:17
* @Entity org.example.pojo.Wordbook
*/
public interface WordbookMapper extends BaseMapper<Wordbook> {

    List<Word> selectList(@Param("list") List<String> list);
}




