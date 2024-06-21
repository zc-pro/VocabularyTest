package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName wordbook
 */
@TableName(value ="wordbook")
@Data
public class Wordbook implements Serializable {
    private Long id;

    private String word;

    private static final long serialVersionUID = 1L;
}