package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    @TableId
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String account;

    private String username;

    private String password;

    private String fourGrade;

    private String sixGrade;

    private String basicVocabulary;

    private static final long serialVersionUID = 1L;
}