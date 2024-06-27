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

    public Word(Integer id, String word, Integer known) {
        this.id = id;
        this.word = word;
        this.known = known;
    }

    public Word() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getKnown() {
        return known;
    }

    public void setKnown(int known) {
        this.known = known;
    }

    public int getId() {
        return id;
    }
}
