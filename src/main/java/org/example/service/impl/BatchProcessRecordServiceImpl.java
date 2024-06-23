package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.pojo.BatchProcessRecord;
import org.example.service.BatchProcessRecordService;
import org.example.mapper.BatchProcessRecordMapper;
import org.example.utils.Result;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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

    /**
     * 验证算法的准确性。
     * 通过读取指定Excel文件中的数据，该方法执行算法验证过程。Excel文件中包含了多组测试数据，
     * 每组数据包括测试词汇和预期结果。方法会计算实际算法处理的词汇量，并与预期结果进行比较，
     * 最终汇总每组数据的验证结果。
     *
     * @return Result 对象，包含算法验证的结果。
     */
    @Override
    public Result verifyAlgorithm() {
        // 定义Excel文件路径
        // 用于存储Excel中的所有数据
        String filePath = "excel/JAVA自动化抓取官网词汇.xlsx"; // Excel文件的路径，相对于类路径
        List<List<String>> data = new ArrayList<>();
        // 用于存储每组数据的验证结果
        List<HashMap> resultMapList = new ArrayList<>();
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            // 获取Excel的第一个Sheet
            Sheet sheet = workbook.getSheetAt(0); // 假设我们只读取第一个sheet
            // 遍历Sheet中的每一行
            Iterator<Row> rowIterator = sheet.iterator();
            // 用于记录当前处理的是第几组数据
            int round = 0;   // 第几组数据
            // 用于记录当前组数据的测试词汇量
            int test_vocabulary =0;  //项目算法测试的词汇量  （需要修改，调用自己的算法）
            // 用于记录参考官方网站的测试词汇量
            String preply_vocabulary = null; //参考官方网站测试的词汇量
            // 用于记录实际在自己的语料库中查询到的词汇量
            int calculated_words = 0; //实际在自己的语料库查询到的并计入算法单词数
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // 用于存储当前行的数据
                List<String> rowData = new ArrayList<>();
                // 遍历当前行的每个单元格
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    // 提取单元格的值
                    String cellValue = getCellValue(cell);
                    // 每处理完3行数据，记录一次验证结果
                    if(round%3==2){
                        preply_vocabulary = cellValue.substring(0,cellValue.length()-2);
                        HashMap resultMap = new HashMap();
                        resultMap.put("round", round/3+1);
                        resultMap.put("test_vocabulary", test_vocabulary);
                        resultMap.put("preply_vocabulary",preply_vocabulary);
                        resultMap.put("calculated_words",calculated_words);
                        resultMapList.add(resultMap);
                    }
                    // 将单元格的值添加到当前行的数据列表中
                    rowData.add(cellValue);
                }
                // 将当前行的数据添加到Excel的全部数据列表中
                data.add(rowData);
                // 更新当前组数据的计数
                round++;
            }
        } catch (Exception e) {
            // 处理异常情况
            e.printStackTrace();
        }
        // 构建最终的返回结果
        Map result = new HashMap();
        result.put("result", resultMapList);
        // 返回包含验证结果的Result对象
        return Result.ok(result);
    }

        private String getCellValue(Cell cell) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return "";
            }
        }
}




