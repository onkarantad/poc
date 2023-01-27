package denorm.test;

import java.util.ArrayList;

public class startsWith {
    public static void main(String[] args) {

    }

    public ArrayList<String> wordsThatStartWith(ArrayList<String> words, String letter)
    {
        ArrayList<String> newWords = new ArrayList<String>();
        for(int i = 0; i < words.size(); i++){
            if((words.get(i)).substring(i, i + 1) == letter){
                newWords.add(words.get(i));
            }
        }
        return newWords;
    }
}
