package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.pojo.BatchProcessRecord;
import org.example.service.BatchProcessRecordService;
import org.example.mapper.BatchProcessRecordMapper;
import org.example.utils.Result;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author a1380
 * @description 针对表【batch_process_record】的数据库操作Service实现
 * @createDate 2024-06-20 16:36:17
 */
@Service
public class BatchProcessRecordServiceImpl extends ServiceImpl<BatchProcessRecordMapper, BatchProcessRecord>
        implements BatchProcessRecordService {

    @Override
    public Result getProcessFourGrade() {
        double correlation = 0.7314775450331452;
        Map data = new HashMap();
        Integer[] four_grade_array = {450, 450, 520, 475, 495, 520, 513};
        Integer[] vocabularies_array = {6099, 3370, 5946, 6586, 3575, 7218, 4053, 7689, 9928, 9476};
        List<Integer> four_grades = new ArrayList<>(Arrays.asList(four_grade_array));
        List<Integer> vocabularies = new ArrayList<>(Arrays.asList(vocabularies_array));
        data.put("correlation", correlation);
        data.put("four_grades", four_grades);
        data.put("vocabularies", vocabularies);
        return Result.ok(data);
    }


    @Override
    public Result getProcessSixGrade() {
        double correlation = 0.7314775450331452;
        Map data = new HashMap();
        Integer[] six_grade_array = {450, 450, 520, 475, 495, 520, 513};
        Integer[] vocabularies_array = {6099, 3370, 5946, 6586, 3575, 7218, 4053, 7689, 9928, 9476};
        List<Integer> six_grades = new ArrayList<>(Arrays.asList(six_grade_array));
        List<Integer> vocabularies = new ArrayList<>(Arrays.asList(vocabularies_array));
        data.put("correlation", correlation);
        data.put("six_grades", six_grades);
        data.put("vocabularies", vocabularies);
        return Result.ok(data);
    }
}




