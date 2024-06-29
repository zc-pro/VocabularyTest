package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* @author a1380
* @description 针对表【wordbook】的数据库操作Service实现
* @createDate 2024-06-20 16:36:17
*/
@Service
@Slf4j
public class WordbookServiceImpl extends ServiceImpl<WordbookMapper, Wordbook>
    implements WordbookService{

    @Autowired
    private WordbookMapper wordbookMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JwtHelper jwtHelper;
//    @Override
//    public Result getList1(String authorization) {
//        if(jwtHelper.isExpiration(authorization)){
//            return Result.build(null, ResultCodeEnum.NOTLOGIN);
//        }
//        int userId = jwtHelper.getUserId(authorization).intValue();
//        UserInfo userInfo = userInfoMapper.selectById(userId);
//        if(userInfo!=null){
//            List<Integer> randomList = new ArrayList<>();
//            Random random = new Random();
//            //从前8000个单词中随机读取40个作为第一次测试,此处需要更改算法
//            for(int i =0;i<40;i++){
//                randomList.add(0+random.nextInt(8000-0+1));
//            }
//            List<Wordbook> resultList = wordbookMapper.selectBatchIds(randomList);
////            System.out.println("resultList = " + resultList);
//            List<HashMap> result = new ArrayList();
//            for(Wordbook word: resultList){
//                HashMap map = new HashMap<>();
//                map.put("id",Integer.valueOf(word.getId().toString()));
//                map.put("word",word.getWord());
//                result.add(map);
//            }
//
//            return Result.ok(result);
//        }
//        return Result.build(null, ResultCodeEnum.NOTLOGIN);
//    }

    /***
     *  为了界面效果第二次单词列表与第一次单词列表相同
     *  修改算法时要修改这部分代码
     */
//    @Override
//    public Result postList1(String authorization, Map<String,Object> chooseWord) {
//        if(jwtHelper.isExpiration(authorization)){
//            return Result.build(null, ResultCodeEnum.NOTLOGIN);
//        }
//        int userId = jwtHelper.getUserId(authorization).intValue();
//        UserInfo userInfo = userInfoMapper.selectById(userId);
//        if(userInfo!=null) {
//
//              List<HashMap> result = new ArrayList();
//            ArrayList resultList = (ArrayList)(chooseWord.get("WordList"));
//
//            for (Object r : resultList) {
//                HashMap temp = (HashMap) r;
//                temp.remove("known");
//                result.add(temp);
//            }
//
//            return Result.ok(result);
//        }
//        return Result.build(null, ResultCodeEnum.NOTLOGIN);
//    }


//    @Override
//    public Result getVocabulary(String authorization, Map<String, Object> chooseWord) {
//        if(jwtHelper.isExpiration(authorization)){
//            return Result.build(null, ResultCodeEnum.NOTLOGIN);
//        }
//        int userId = jwtHelper.getUserId(authorization).intValue();
//        UserInfo userInfo = userInfoMapper.selectById(userId);
//        if(userInfo!=null) {
//
//            //算法部分：
//            //..........
//            int result = 7000;
//            System.out.println(userInfo.getBasicVocabulary());
//            String vocabularyString = userInfo.getBasicVocabulary();
//            String[] vocabularyArray = new String[]{};
//            if(!vocabularyString.isEmpty()) {
//                vocabularyArray = userInfo.getBasicVocabulary().substring(1, vocabularyString.length() - 1).split(",");
//            }
//            for (String temp:vocabularyArray){
//                System.out.println(temp);
//            }
//            List<String> vocabularyList = new ArrayList<>(Arrays.asList(vocabularyArray));
//            vocabularyList.add(String.valueOf(result));
//            userInfo.setBasicVocabulary(vocabularyList.toString());
//            userInfo.setUpdatedAt(new Date());
//            userInfoMapper.updateById(userInfo);
//            return Result.ok(result);
//        }
//        return Result.build(null, ResultCodeEnum.NOTLOGIN);
//
//    }

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

            //总单词数
            int totalWords=8000;
            //抽取个数
            int sampleSize=40;
            //分层
            int numLayers =10;
            //定义各层权重
            //!!!float->double
            double weights[]={1.0,0.8,0.6,0.4,0.2,0.1,0.05,0.03,0.02,0.01};

            int[] layerSizes = new int[numLayers];
            double totalWeight = 0.0;
            for (int i = 0; i < numLayers; i++) {
                totalWeight += weights[i];
            }

            // 确保权重总和为1（或接近1），否则计算会出错
            if (totalWeight <= 0) {
                throw new IllegalArgumentException("Total weight must be greater than 0");
            }

            for (int i = 0; i < numLayers; i++) {
                // 计算当前层的单词数量（考虑精度）
                double ratio = weights[i] / totalWeight;
                double layerSizeDouble = totalWords * ratio;
                // 使用 Math.round 进行四舍五入
                layerSizes[i] = (int) Math.round(layerSizeDouble);

                // 如果需要确保所有层的单词数量加起来等于 totalWords，
                // 可以在这里添加逻辑来重新分配剩余的单词
            }

            // 打印每层的单词数量
            for (int i = 0; i < numLayers; i++) {
                System.out.println("Layer " + (i + 1) + ": " + layerSizes[i] + " words");
            }

            // 每个层级应该取多少个单词
            int randCount = 0;
            int shouldRand[]=new int[10];
            for(int i = 0; i < numLayers; i++) {
                // 计算百分比(权重/总权重)
                double tmpPer = weights[i] / totalWeight;
                //权重*采样单词个数,权值越少(),采样范围越小
                int tmpSize = (int) Math.round(tmpPer * sampleSize);;
                shouldRand[i] = tmpSize;
                randCount += shouldRand[i];
            }

            // 如果总随机数量小于采样数量，则将剩余数量加到第一层（或根据需要调整）
            if (randCount < sampleSize) {
                int diff = sampleSize - randCount;
                if (diff > 0 && shouldRand.length > 0) {
                    shouldRand[0] += diff; // 只增加第一层，或者你可以根据需求调整逻辑
                }
            }else if(randCount > sampleSize){  //总随机数大于采样数量,减少第一层
                shouldRand[0]-=randCount-sampleSize;
            }

            for(int i=0;i<shouldRand.length;i++){
                System.out.println("shouldRand[" +i+"]="+ shouldRand[i]);
            }


            Random rand = new Random(System.currentTimeMillis());

            List<Integer> randomNumbers2 = new ArrayList<>();
            int curCount = 0;
            for (int i = 0; i < numLayers; i++) {
                // 该层结束索引
                curCount += layerSizes[i];
                // 从 {curCount-layerSizes[i], curCount-1} 随机取 shouldRand[i] 个数
                List<Integer> randomNumbers = generateRandomNumbers(rand, shouldRand[i], curCount - layerSizes[i], curCount - 1);
                System.out.print("............................");
                System.out.print(i);
                System.out.println("Random numbers: " + randomNumbers);

                // 将每层的 randomNumbers 列表添加进 randomNumbers2 中
                randomNumbers2.addAll(randomNumbers);
            }
            System.out.println("Combined random numbers: " + randomNumbers2);

            // 对 randomNumbers2 进行排序
            Collections.sort(randomNumbers2);

            // 接下来是数据库查询和响应逻辑
            // 假设你有一个方法来执行数据库查询，类似于 GetWordListById
            List<Wordbook> resultList = wordbookMapper.selectBatchIds(randomNumbers2);
            List<HashMap> result = new ArrayList();
            for(Wordbook word: resultList){
                HashMap map = new HashMap<>();
                map.put("id",Integer.valueOf(word.getId().toString()));
                map.put("word",word.getWord());
                result.add(map);
            }


            ////	生成对应数量的随机数
            //rand.Seed(time.Now().UnixNano())
            //
            //randomNumbers2 := make([]int, 0)
            //curCount := 0
            //for i := 0; i < numLayers; i++ {
            //    //该层结束索引
            //    curCount += layerSizes[i]
            //    //从{curCount-layerSizes[i]+1, curCount}随机取shouldRand[i]个数
            //    randomNumbers := generateRandomNumbers(shouldRand[i], curCount-layerSizes[i]+1, curCount)
            //

//            List<Wordbook> resultList = wordbookMapper.selectBatchIds(randomList);
////            System.out.println("resultList = " + resultList);
//            List<HashMap> result = new ArrayList();
//            for(Wordbook word: resultList){
//                HashMap map = new HashMap<>();
//                map.put("id",Integer.valueOf(word.getId().toString()));
//                map.put("word",word.getWord());
//                result.add(map);
//            }

            return Result.ok(result);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    //点击确认后,调用judge和getList2获得第二个单词列表
    public Result JudgeUserWordLevel(List<Word> wordList) {
        //var (
        //        reqForm reqWordList
        //        msg     string
        //        err     error
        //)
        //if err = c.ShouldBindJSON(&reqForm); err != nil {
        //    msg = "请求不合法"
        //    z.Error(fmt.Sprintf("%s:%s,请求的参数:%+v", msg, err, reqForm))
        //    response.Err(c, http.StatusOK, msg, nil)
        //    return
        //}
        //if len(reqForm.WordList) < 3 {
        //    msg = "参数太少，请求不合法"
        //    z.Error(fmt.Sprintf("%s:%s,请求的参数太少啦:%+v", msg, err, reqForm))
        //    response.Err(c, http.StatusOK, msg, nil)
        //    return
        //}

        // 设置抽样参数
        int numLayers = 6;
        //int totalWords = wordList[len(wordList)-1].Id;
        //System.out.println(wordList.get(wordList.size()-1));
        //Word lastword= wordList.get(wordList.size()-1);

        int totalWords= wordList.get(wordList.size() - 1).getId();
        //Word[] wordListArray = (Word[]) wordList.toArray();
        Word[] wordListArray = new Word[wordList.size()];
        for (int i = 0; i < wordList.size(); i++) {
            wordListArray[i] = (Word) wordList.get(i); // 注意这里仍然可能抛出ClassCastException
        }
        //!!!
        //Word[] wordListArray = (Word[]) wordList.toArray();
        //int totalWords = wordListArray[wordListArray.length - 1].getId();

        //int totalWords = wordList.get(wordList.size()-1);


        // 定义层次权重，采用指数衰减的方式
        double weights[] = {1.0, 0.8, 0.6, 0.4, 0.2, 0.1};
        double totalWeight = 0.0;
        for(int i = 0; i < numLayers; i++) {
            totalWeight += weights[i];
        }

        // 定义层次的得分权重
        double knownWeights[] = {1.0, 1.3, 1.6, 1.9, 2.2, 2.5};
        double totalKnownWeights = 0.0;
        for(int i = 0; i < numLayers; i++){
            totalKnownWeights += knownWeights[i];
        }

        // 定义层次系数
        //levelCoefficient := []float64{0.95, 0.8, 0.85, 0.8, 0.75, 0.7}

        // 计算每个层次的分界线 = 划分层级
        int boundaries[] = new int[7];
        boundaries[0] = 1;
        for (int i = 1; i <= numLayers; i++) {
            double boundary = Math.round(totalWords * (weights[i - 1] / totalWeight));
            boundaries[i] = boundaries[i - 1] + (int)boundary; // 将计算出的边界值累加到数组中
        }

        double knownPers[] = new double[6];
        double totalPer = 0.0;
        int curId = 0;
        //int length = wordList.getWordList().length;
        int length = wordList.size();
        //int length=wordList.length;

        for(int i = 0; i < numLayers; i++) {
            int tmp = curId;
            int knownCount = 0;

            int layerBoundaryStart = boundaries[i];
            int layerBoundaryEnd = boundaries[i+1];

            for( ; curId < length && wordListArray[curId].getId() < layerBoundaryEnd; curId++) {
                if( wordListArray[curId].getId() > layerBoundaryStart && wordListArray[curId].getKnown() == 1) {
                    knownCount++;
                }
            }

            // 计算该层级的认识率
            if (curId > 0) {
                //该层认识率=认识个数/该层单词数
                double knownPer = (double)(knownCount) / (double) (curId-tmp);
                knownPers[i] = knownPer;
                totalPer += knownPer;
            } else {
                knownPers[i] = 0.0;
            }
        }

        //平均认识率
        totalPer = totalPer / (numLayers);
        // 根据层级准确率计算下次抽样的数量
        int sampleSize = 0;
        // 从多少个单词抽
        int wordsRange = 5000;
        // weights: 定义层次权重，采用指数衰减的方式

        if( totalPer < 0 || totalPer > 1) {
            String msg = "计算的认识率不合法";
            // 这里假设您有一个日志系统或者错误处理机制
            log.error(msg);
            // 这里是HTTP响应的模拟，您需要根据实际情况处理
            // response.Err(c, http.StatusOK, msg, null);
            // 返回一个错误或者抛出异常
            throw new IllegalArgumentException(msg);
        }

        if (totalPer < 0.3) {
            sampleSize = 30;
            wordsRange = 5000;
            weights = new double[]{1.0, 0.8, 0.6, 0.4, 0.2, 0.1};
        } else if (totalPer < 0.5) {
            sampleSize = 60;
            wordsRange = 15000;
            weights = new double[]{1.0, 0.8, 0.7, 0.6, 0.5, 0.4};
        } else if (totalPer < 0.8) {
            sampleSize = 80;
            wordsRange = 25000;
            weights = new double[]{1.0, 0.9, 0.8, 0.7, 0.6, 0.5};
        } else {
            sampleSize = 100;
            wordsRange = 41000;
            weights = new double[]{1.0, 0.9, 0.9, 0.9, 0.8, 0.7};
        }

        //根据层级准确率计算下次抽样的单词列表
        // 计算每个层级有多少单词数量
        int[] layerSizes = new int[numLayers];
        double totalWeight2 = 0.0;
        for (int i = 0; i < numLayers; i++) {
            totalWeight2 += weights[i];
        }
        for (int i = 0; i < numLayers; i++) {
            // 计算该层的采样数量
            layerSizes[i] = (int) (wordsRange * weights[i] / totalWeight2);
        }

        // 总采样数量
        int randCount = 0;
        // 每个层级应该取多少个单词
        int shouldRand[] = new int[numLayers];
        for (int i = 0; i < numLayers; i++) {
            // 计算百分比
            double tmpPer = weights[i] / totalWeight2;
            double tmpSize = tmpPer * sampleSize;
            shouldRand[i] = (int) Math.round(tmpSize);
            randCount += shouldRand[i];
        }

        // 如果计算出的randCount小于sampleSize，则将剩余数量加到第一个层级
        if (randCount < sampleSize) {
            shouldRand[0] += sampleSize - randCount;
        }   else {
            shouldRand[0] -= randCount - sampleSize;
        }

        // 生成对应数量的随机数
        Random rand = new Random(System.currentTimeMillis());

        List<Integer> randomNumbers2 = new ArrayList<>();
        int curCount = 0;
        for (int i = 0; i < numLayers; i++) {
            // 计算该层结束索引（在Java中，索引是从0开始的，所以不需要+1）
            curCount += layerSizes[i];

            // 生成随机数（注意：这里假设generateRandomNumbersJava是自定义的Java方法）
            List<Integer> randomNumbers = generateRandomNumbersJava(shouldRand[i], curCount - layerSizes[i], curCount - 1);
            randomNumbers2.addAll(randomNumbers);
        }

        // 对 randomNumbers2 进行排序
        Collections.sort(randomNumbers2);

        // 假设您有一个方法来根据ID列表获取单词列表
        //List<String> list;
        try {
            //list = getWordListByIds(randomNumbers2); // 注意：方法名已更改为Java命名规范
            //list = wordbookMapper.selectBatchIds(randomNumbers2); // 注意：方法名已更改为Java命名规范
            List<Wordbook> resultList = wordbookMapper.selectBatchIds(randomNumbers2);
            List<HashMap> result = new ArrayList();
            for(Wordbook word: resultList){
                HashMap map = new HashMap<>();
                map.put("id",Integer.valueOf(word.getId().toString()));
                map.put("word",word.getWord());
                result.add(map);
            }
            return Result.ok(result);
        } catch (Exception e) {
            String msg = "数据库查询失败";
            // 这里假设您有一个日志系统
            log.error(msg, e);
            // 发送HTTP响应（这取决于您使用的框架）
            // sendErrorResponse(response, msg); // 这是一个模拟的发送错误响应的方法
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        //return
        //response.Success(c, list)
        //return

    }

    /***
     *  为了界面效果第二次单词列表与第一次单词列表相同
     *  修改算法时要修改这部分代码
     */
    @Override
    public Result postList1(String authorization, @RequestBody Map<String, Object> chooseWord) {
        //public Result postList1(String authorization, Map<String,Object> chooseWord) {
        if(jwtHelper.isExpiration(authorization)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(authorization).intValue();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if(userInfo!=null) {

            ArrayList<Word> wordList1111= (ArrayList<Word>) chooseWord.get("WordList");
            List<LinkedHashMap<String, Object>> wordMaps = (List<LinkedHashMap<String, Object>>) chooseWord.get("WordList");
            List<Word> wordlist = new ArrayList<>();
            for (LinkedHashMap<String, Object> wordMap : wordMaps) {
                // 提取id、word和known的值
                int id = (Integer) wordMap.get("id");
                String word = (String) wordMap.get("word");
                int known = (Integer) wordMap.get("known");

                // 创建Word对象并添加到列表中
                Word wordObj = new Word(id, word, known);
                wordlist.add(wordObj);
            }
            return JudgeUserWordLevel(wordlist);
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
            List<LinkedHashMap<String, Object>> wordMaps = (List<LinkedHashMap<String, Object>>) chooseWord.get("WordList");
            List<Word> list = new ArrayList<>();
            for (LinkedHashMap<String, Object> wordMap : wordMaps) {
                // 提取id、word和known的值
                int id = (Integer) wordMap.get("id");
                String word = (String) wordMap.get("word");
                int known = (Integer) wordMap.get("known");

                // 创建Word对象并添加到列表中
                Word wordObj = new Word(id, word, known);
                list.add(wordObj);
            }


            int result = Vocabulary(list);
            System.out.println(userInfo.getBasicVocabulary());
            String vocabularyString = userInfo.getBasicVocabulary();
            String[] vocabularyArray = new String[]{};
            if(vocabularyString.isEmpty()) {
                //vocabularyArray = new String[]{String.valueOf(result)};
            }
            else {
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


    public int Vocabulary(List<Word> list){
        Word[] wordListArray = new Word[list.size()];
        for (int i = 0; i < list.size(); i++) {
            wordListArray[i] = (Word) list.get(i); // 注意这里仍然可能抛出ClassCastException
        }
        // 设置抽样参数
        int numLayers = 10; // 词汇太少了，无法估算
        if (wordListArray.length < numLayers) {
            return 20;
        }

        // 假设列表中的最后一个元素的ID代表了总词汇量
        int totalWords=0;
        if(wordListArray.length-40<=20){
            totalWords=5000;
        } else if (wordListArray.length-40>20&&wordListArray.length-40<=40) {
            totalWords=15000;

        } else if (wordListArray.length-40<=60&&wordListArray.length-40>40) {
            totalWords=25000;
        } else if (wordListArray.length-40>=60) {
            totalWords=41000;
        }

//        if(wordListArray.length-40==30){
//            totalWords=5000;
//        } else if (wordListArray.length-40==60) {
//            totalWords=15000;
//
//        } else if (wordListArray.length-40==80) {
//            totalWords=25000;
//        } else if (wordListArray.length-40==100) {
//            totalWords=41000;
//
//        }


        // 定义层次权重，采用指数衰减的方式
        double[] weights = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.1, 0.1};
        double totalWeight = 0.0;
        for (double weight : weights) {
            totalWeight += weight;
        }

        // 定义层次的得分权重
        double[] knownWeights = {2.0, 2.1, 2.2, 2.3, 2.35, 2.4, 2.45, 2.5, 2.6, 2.7};

        // 计算每个层次的分界线
        int[] boundaries = new int[numLayers + 1];
        boundaries[0] = 1;
        for (int i = 1; i <= numLayers; i++) {
            double boundary = Math.round(totalWords * (weights[i - 1] / totalWeight));
            boundaries[i] = boundaries[i - 1] + (int) boundary;
        }

        // 计算每个层次有多少单词认识
        double[] knownPers = new double[numLayers];
        double totalPer = 0.0;
        int curId = 0;
        int length = list.size();

        for (int i = 0; i < numLayers; i++) {
            int tmp = curId;
            int knownCount = 0;
            int layerBoundaryStart = boundaries[i];
            int layerBoundaryEnd = boundaries[i + 1];

            while (curId < length && wordListArray[curId].getId() < layerBoundaryEnd) {
                if (wordListArray[curId].getId() > layerBoundaryStart && (wordListArray[curId].getKnown()==1)) {
                    knownCount++;
                }
                curId++;
            }

            // 计算该层级的认识率
            if (curId > tmp) {
                double knownPer = (double) knownCount / (curId - tmp);
                knownPers[i] = knownPer;
            } else {
                knownPers[i] = 0.0;
            }
        }

        // 避免某些中间层级没有单词，导致平均认识率加了NaN
        int skip = 0;
        for (double num : knownPers) {
            if (Double.isNaN(num)) {
                skip++;
                continue; // 跳过NaN值
            }
            totalPer += num;
        }

        // 如果所有层次都没有单词认识，返回默认词汇量
        if (skip == numLayers) {
            return 20;
        }

        totalPer /= (numLayers - skip);

        // 根据认识率的得分权重计算总体得分（但在这个例子中，我们不会实际使用它来计算词汇量）
        // ...

        // 假设：如果平均认识率是 totalPer，则大致认识 totalWords * totalPer 的单词数
        int estimatedVocabulary = (int) Math.round(totalWords * totalPer);

        // 设置合理的词汇量上下限
        int minVocabulary = 20;
        int maxVocabulary = totalWords;
        if (estimatedVocabulary < minVocabulary) {
            estimatedVocabulary = minVocabulary;
        } else if (estimatedVocabulary > maxVocabulary) {
            estimatedVocabulary = maxVocabulary;
        }
        return estimatedVocabulary;
    }

    private List<Integer> generateRandomNumbers(Random rand, int count, int min, int max) {
        List<Integer> numbers = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            numbers.add(rand.nextInt(max - min + 1) + min); // 生成 [min, max] 范围内的随机数
        }

        // 如果需要保证生成的随机数是唯一的，并且不重复，则需要进行去重和排序
        // 这里假设生成的随机数可以重复，因此不进行去重和排序

        return numbers;
    }

    /**
     * 该函数使用Java 8的Stream API生成指定数量（count）的随机整数，这些整数位于指定范围（from到to）内，并以List<Integer>形式返回。具体实现包括：
     * 使用Random类创建一个随机数生成器。
     * 通过IntStream.generate()方法生成一个无限的、独特的随机整数流。
     * 使用distinct()方法确保生成的随机数唯一，避免重复。
     * 使用limit()方法限制生成的随机数数量为指定的count。
     * 使用boxed()方法将整数流转换为Integer流。
     * 最后，使用collect()方法将流收集到一个List<Integer>对象中并返回。
     * @param count
     * @param from
     * @param to
     * @return
     */
    private List<Integer> generateRandomNumbersJava(int count, int from, int to) {
        Random rand = new Random();
        return IntStream.generate(() -> from + rand.nextInt(to - from + 1))
                .distinct()
                .limit(count)
                .boxed()
                .collect(Collectors.toList());
    }
}




