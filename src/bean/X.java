package bean;

import java.util.ArrayList;
import java.util.HashMap;

//DFA�е�ÿһ����
public class X {

    //��ǰ�ǵڼ���
    private int number;

    //��ǰ�������ķ�
    private ArrayList<P> S;

    //��¼��ε�����һ��
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
