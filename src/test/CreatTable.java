package test;

import bean.P;
import bean.V;
import bean.X;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//S �� E
//E �� aA �O bB
//A �� cA �O d
//B �� cB �O d
public class CreatTable {

    //�����ķ�
    P[] f = {new P(1,new V("E"), new String[]{"a", "A"}),
            new P(2,new V("E"), new String[]{"b", "B"}),
            new P(3,new V("A"), new String[]{"c", "A"}),
            new P(4,new V("A"), new String[]{"d"}),
            new P(5,new V("B"), new String[]{"c", "B"}),
            new P(6,new V("B"), new String[]{"d"})};

    //�ս��
    String end = "abcd";
    //���ս��
    String not = "EAB";
    //��¼�����ɵ�����
    int number = 0;
    //����DFA�е���
    ArrayList<X> dfa = new ArrayList<>();

    int Action[][];
    int Goto[][];

    public void creatTable(){
        //���忪ʼ����
        ArrayList<P> arrayList = new ArrayList<>();
        arrayList.add(new P(0,new V("S"), new String[]{".", "E"}));
        arrayList.add(new P(1,new V("E"), new String[]{".","a", "A"}));
        arrayList.add(new P(2,new V("E"), new String[]{".","b", "B"}));
        X start = new X(0,arrayList,new HashMap<String,Integer>());
        dfa.add(start);

        //����DFA
        int i = 0;
        while (i < dfa.size()){
            creatDFA(dfa.get(i));
            i++;
        }
        //��ӡDFA
        print(dfa);

        //���ɷ�����,������ʾ�ƽ���������ʾ��Լ��20��ʾacc
        Action = new int[dfa.size()][5];
        Goto = new int[dfa.size()][3];

        //�ķ���߼Ӹ�.����Ƚ�
        String [] strings = {"."};
        for (int k = 0; k < f.length; k++) {
            f[k].setRigthExp(concat(f[k].getRigthExp(),strings));
        }
        //����dfa
        for (int j = 0; j < dfa.size(); j++) {
            X x = dfa.get(j);
            HashMap<String, Integer> to = x.getTo();
            //�ж�hash
            if (to.isEmpty()){
                //�����ķ�
                if ("S".equals(x.getS().get(0).getLeftV().getSingle())){
                    Action[j][4] = 20;
                }
                //��Լ
                for (int k = 0; k < f.length; k++) {
                    if (x.getS().get(0).equals(f[k])){
                        for (int l = 0; l < 5; l++) {
                            Action[j][l] = -f[k].getNum();
                        }
                    }

                }
            }else {
                //�ƽ�
                for (Map.Entry<String, Integer> entry : to.entrySet()) {
                    if (end.contains(entry.getKey())){
                        Action[j][end.indexOf(entry.getKey())] = entry.getValue();
                    }else {
                        Goto[j][not.indexOf(entry.getKey())] = entry.getValue();
                    }
                }
            }
        }

        //��ӡ������
        System.out.println("������");
        for (int j = 0; j < dfa.size(); j++) {
            System.out.println(Arrays.toString(Action[j]) + "  " + Arrays.toString(Goto[j]));
        }
    }

    public void creatDFA(X start){
        //�������е�ÿһ��
        for (int i = 0; i < start.getS().size(); i++) {
            //����->�ұ�
            for (int j = 0; j < start.getS().get(i).getRigthExp().length; j++) {
                //�ҵ�.��λ��
                if (".".equals(start.getS().get(i).getRigthExp()[j])){
                    //�ж�.�Ƿ���ĩβ
                    if (j < start.getS().get(i).getRigthExp().length - 1){
                        //�ƶ����λ��
                        String mid;
                        String[] newString = start.getS().get(i).getRigthExp().clone();
                        mid = newString[j];
                        newString[j] = newString[j+1];
                        newString[j+1] = mid;

                        //System.out.println("new:"+Arrays.toString(newString));

                        //�ж��µĴ���ǰ����û�г��ֹ�
                        int flag = 0;
                        for (X value : dfa) {
                            //�ж��ķ�
                            if (value.getS().get(0).getLeftV().getSingle().equals(start.getS().get(i).getLeftV().getSingle())
                                    && Arrays.equals(newString, value.getS().get(0).getRigthExp())) {
                                //ָ�����е���
                                start.getTo().put(newString[j], value.getNumber());
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0){
                            //�����µ���
                            //.�����һλ���ж�.���滹��û��Ԫ��
                            if (j < start.getS().get(i).getRigthExp().length - 2){
                                //�仯�Ƿ��ѳ��ֹ�������T->.T*F��T->.T/F����T
                                if (start.getTo().containsKey(newString[j])){
                                    //���´������ȥ
                                    X x = dfa.get(start.getTo().get(start.getS().get(i).getLeftV().getSingle()));
                                    x.getS().add(new P(start.getS().get(i).getNum(),start.getS().get(i).getLeftV(),newString));
                                    //.��һλ�Ƿ�Ϊ�ս��
                                    if (!end.contains(newString[j+2])){
                                        for (int k = 2; k < f.length; k++) {
                                            //�ҵ����ս�����ķ�
                                            if (f[k].getLeftV().getSingle().equals(newString[j+2])){
                                                String [] strings = {"."};
                                                x.getS().add(new P(f[k].getNum(),f[k].getLeftV(), concat(strings,f[k].getRigthExp())));
                                            }
                                        }
                                    }
                                    //�µ���ȡ��dfa��ɵ���
                                    dfa.set(start.getTo().get(start.getS().get(i).getLeftV().getSingle()),x);
                                }else {//�±仯
                                    X node = new X(++number,
                                            new ArrayList<P>(Arrays.asList(new P(start.getS().get(i).getNum(),start.getS().get(i).getLeftV(),newString))),
                                            new HashMap<>());
                                    //.��һλ�Ƿ�Ϊ�ս��
                                    if (!end.contains(newString[j+2])){
                                        for (int k = 2; k < f.length; k++) {
                                            if (f[k].getLeftV().getSingle().equals(newString[j+2])){
                                                String [] strings = {"."};
                                                node.getS().add(new P(f[k].getNum(),f[k].getLeftV(), concat(strings,f[k].getRigthExp())));
                                            }
                                        }
                                    }
                                    dfa.add(node);
                                    start.getTo().put(newString[j],number);
                                }
                            }else {//������.�����
                                X node = new X(++number,
                                        new ArrayList<P>(Arrays.asList(new P(start.getS().get(i).getNum(),start.getS().get(i).getLeftV(),newString))),
                                        new HashMap<>());
                                dfa.add(node);
                                start.getTo().put(newString[j],number);
                            }
                        }
                    }
                }
            }
        }
    }

    //�ϲ���������
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    //��ӡdfa
    public void print(ArrayList<X> dfa){
        for (X x: dfa) {
            System.out.println(String.valueOf(x.getNumber())+x.getTo());
            for (P p : x.getS()) {
                System.out.println(" "+p.getNum()+" "+p.getLeftV().getSingle()+"->"+Arrays.toString(p.getRigthExp()));
            }
        }
    }

    public static void main(String[] args) {
        CreatTable creatTable = new CreatTable();
        creatTable.creatTable();
    }
}
