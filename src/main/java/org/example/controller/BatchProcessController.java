package org.example.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.service.BatchProcessRecordService;
import org.example.service.impl.BatchProcessRecordServiceImpl;
import org.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName: BatchProcessController
 * Package: org.example.controller
 *
 * @Author 吴圳城
 * @Create 2024/6/22 下午9:09
 * @Version 1.0
 * Description: 批处理类
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

    @PostMapping("word/VerifyAlgorithm")
    public Result verifyAlgorithm(){
//        System.out.println("1111");
        Result result = batchProcessRecordService.verifyAlgorithm();
        return result;
    }

}
