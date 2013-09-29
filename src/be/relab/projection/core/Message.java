package be.relab.projection.core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class Message {

    ArrayList<Word> words;

    void Message(){
        words = new ArrayList<Word>();
    }
    void addWord(Word w){
        words.add(w);
    }

    void display(){
        Iterator<Word> it = words.iterator();
        while(it.hasNext()){
            Word w = (Word)it.next();
            w.display();
        }
    }
}
