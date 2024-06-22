package org.example.controller;

import org.example.service.BatchProcessRecordService;
import org.example.service.impl.BatchProcessRecordServiceImpl;
import org.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: BatchProcessController
 * Package: org.example.controller
 *
 * @Author 吴圳城
 * @Create 2024/6/22 下午9:09
 * @Version 1.0
 * Description: 批处理
 */
@RestController
@RequestMapping("batch-api")
@CrossOrigin
public class BatchProcessController {
    @Autowired
    private BatchProcessRecordService batchProcessRecordService;

    @GetMapping("coefficient/fourGrade")
    public Result getProcessFourGrade(){
        Result result = batchProcessRecordService.getProcessFourGrade();
        return result;
    }

    @GetMapping("coefficient/sixGrade")
    public Result getProcessSixGrade(){
        Result result = batchProcessRecordService.getProcessSixGrade();
        return result;
    }
}
