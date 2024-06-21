package org.example.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * ClassName: Word
 * Package: org.example.pojo
 *
 * @Author 吴圳城
 * @Create 2024/6/21 15:56
 * @Version 1.0
 * Description: 单词类
 */
@Component
@Data
public class Word {
    private int id;
    private String word;
    private int known;
    @Override
    public String toString() {
        return "WordDTO{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", known=" + known +
                '}';
    }
}
