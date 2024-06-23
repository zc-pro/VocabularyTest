package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.UserInfoMapper;
import org.example.pojo.UserInfo;
import org.example.pojo.Word;
import org.example.pojo.WordList;
import org.example.pojo.Wordbook;
import org.example.service.WordbookService;
import org.example.mapper.WordbookMapper;
import org.example.utils.JwtHelper;
import org.example.utils.Result;
import org.example.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author a1380
* @description 针对表【wordbook】的数据库操作Service实现
* @createDate 2024-06-20 16:36:17
*/
@Service
public class WordbookServiceImpl extends ServiceImpl<WordbookMapper, Wordbook>
    implements WordbookService{

    @Autowired
    private WordbookMapper wordbookMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result getList1(String authorization) {
        if(jwtHelper.isExpiration(authorization)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(authorization).intValue();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if(userInfo!=null){
            List<Integer> randomList = new ArrayList<>();
            Random random = new Random();
            //从前8000个单词中随机读取40个作为第一次测试,此处需要更改算法
            for(int i =0;i<40;i++){
                randomList.add(0+random.nextInt(8000-0+1));
            }
            List<Wordbook> resultList = wordbookMapper.selectBatchIds(randomList);
//            System.out.println("resultList = " + resultList);
            List<HashMap> result = new ArrayList();
            for(Wordbook word: resultList){
                HashMap map = new HashMap<>();
                map.put("id",Integer.valueOf(word.getId().toString()));
                map.put("word",word.getWord());
                result.add(map);
            }

            return Result.ok(result);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    /***
     *  为了界面效果第二次单词列表与第一次单词列表相同
     *  修改算法时要修改这部分代码
     */
    @Override
    public Result postList1(String authorization, Map<String,Object> chooseWord) {
        if(jwtHelper.isExpiration(authorization)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(authorization).intValue();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if(userInfo!=null) {

              List<HashMap> result = new ArrayList();
            ArrayList resultList = (ArrayList)(chooseWord.get("WordList"));

            for (Object r : resultList) {
                HashMap temp = (HashMap) r;
                temp.remove("known");
                result.add(temp);
            }

            return Result.ok(result);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    @Override
    public Result getVocabulary(String authorization, Map<String, Object> chooseWord) {
        if(jwtHelper.isExpiration(authorization)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(authorization).intValue();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if(userInfo!=null) {

            //算法部分：
            //..........
            int result = 7000;
            System.out.println(userInfo.getBasicVocabulary());
            String vocabularyString = userInfo.getBasicVocabulary();
            String[] vocabularyArray = new String[]{};
            if(!vocabularyString.isEmpty()) {
                vocabularyArray = userInfo.getBasicVocabulary().substring(1, vocabularyString.length() - 1).split(",");
            }
            for (String temp:vocabularyArray){
                System.out.println(temp);
            }
            List<String> vocabularyList = new ArrayList<>(Arrays.asList(vocabularyArray));
            vocabularyList.add(String.valueOf(result));
            userInfo.setBasicVocabulary(vocabularyList.toString());
            userInfo.setUpdatedAt(new Date());
            userInfoMapper.updateById(userInfo);
            return Result.ok(result);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);

    }
}




