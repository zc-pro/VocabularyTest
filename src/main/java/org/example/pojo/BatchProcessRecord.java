package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName batch_process_record
 */
@TableName(value ="batch_process_record")
@Data
public class BatchProcessRecord implements Serializable {
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String wordListIds;

    private Long myVocabulary;

    private Long vocabulary;

    private Long difference;

    private static final long serialVersionUID = 1L;
}