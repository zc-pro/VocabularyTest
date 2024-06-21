package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.pojo.BatchProcessRecord;
import org.example.service.BatchProcessRecordService;
import org.example.mapper.BatchProcessRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author a1380
* @description 针对表【batch_process_record】的数据库操作Service实现
* @createDate 2024-06-20 16:36:17
*/
@Service
public class BatchProcessRecordServiceImpl extends ServiceImpl<BatchProcessRecordMapper, BatchProcessRecord>
    implements BatchProcessRecordService{

}




