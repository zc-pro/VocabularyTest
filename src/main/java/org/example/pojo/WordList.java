package org.example.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: WordList
 * Package: org.example.pojo
 *
 * @Author 吴圳城
 * @Create 2024/6/21 17:04
 * @Version 1.0
 * Description:
 */
@Component
@Data
public class WordList {
    private Word[] wordList;

    public void setWordList(Word[] wordList) {
        this.wordList = wordList;
    }
    public Word[] getWordList(){
        return this.wordList;
    }
    @Override
    public String toString() {
        return "wordList="+wordList;
    }

}
