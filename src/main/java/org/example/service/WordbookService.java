package org.example.service;

import org.example.pojo.Word;
import org.example.pojo.WordList;
import org.example.pojo.Wordbook;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.utils.Result;

import java.util.List;
import java.util.Map;

/**
* @author a1380
* @description 针对表【wordbook】的数据库操作Service
* @createDate 2024-06-20 16:36:17
*/
public interface WordbookService extends IService<Wordbook> {

    Result getList1(String authorization);

    Result postList1(String authorization, Map<String,Object> chooseWord);

    Result getVocabulary(String authorization, Map<String, Object> chooseWord);
}
