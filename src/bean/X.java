package bean;

import java.util.ArrayList;
import java.util.HashMap;

//DFA中的每一个项
public class X {

    //当前是第几项
    private int number;

    //当前包含的文法
    private ArrayList<P> S;

    //记录如何到达下一项
    private HashMap<String,Integer> to;

    public X(int number, ArrayList<P> s,HashMap<String,Integer> t) {
        this.number = number;
        S = s;
        this.to = t;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<P> getS() {
        return S;
    }

    public void setS(ArrayList<P> s) {
        S = s;
    }


    public HashMap<String, Integer> getTo() {
        return to;
    }

    public void setTo(HashMap<String, Integer> to) {
        this.to = to;
    }

}
