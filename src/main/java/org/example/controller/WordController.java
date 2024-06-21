package org.example.controller;

import org.example.pojo.Word;
import org.example.pojo.WordList;
import org.example.service.WordbookService;
import org.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName: WordController
 * Package: org.example.controller
 *
 * @Author 吴圳城
 * @Create 2024/6/21 13:44
 * @Version 1.0
 * Description: 语料处理
 */
@RestController
@RequestMapping("basic-api/word")
@CrossOrigin
public class WordController {
    @Autowired
    private WordbookService wordbookService;

    @GetMapping("getList1")
    public Result getList1(@RequestHeader String authorization){
        Result result = wordbookService.getList1(authorization);
        return result;
    }

    @PostMapping("postList1")
    public Result postList1(@RequestHeader String authorization, @RequestBody Map<String, Object> chooseWord){
//        System.out.println("authorization = " + authorization + ", chooseWord = " + chooseWord);
        Result result = wordbookService.postList1(authorization, chooseWord);
        return  result;
    }
    @PostMapping("getVocabulary")
    public Result getVocabulary(@RequestHeader String authorization,@RequestBody Map<String, Object> chooseWord){
        Result result = wordbookService.getVocabulary(authorization,chooseWord);
        return result;
    }
}
