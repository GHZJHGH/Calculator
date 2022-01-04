package test;

import bean.P;
import bean.V;
import bean.X;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//S → E
//E → aA ∣ bB
//A → cA ∣ d
//B → cB ∣ d
public class CreatTable {

    //定义文法
    P[] f = {new P(1,new V("E"), new String[]{"a", "A"}),
            new P(2,new V("E"), new String[]{"b", "B"}),
            new P(3,new V("A"), new String[]{"c", "A"}),
            new P(4,new V("A"), new String[]{"d"}),
            new P(5,new V("B"), new String[]{"c", "B"}),
            new P(6,new V("B"), new String[]{"d"})};

    //终结符
    String end = "abcd";
    //非终结符
    String not = "EAB";
    //记录已生成的项数
    int number = 0;
    //保存DFA中的项
    ArrayList<X> dfa = new ArrayList<>();

    int Action[][];
    int Goto[][];

    public void creatTable(){
        //定义开始的项
        ArrayList<P> arrayList = new ArrayList<>();
        arrayList.add(new P(0,new V("S"), new String[]{".", "E"}));
        arrayList.add(new P(1,new V("E"), new String[]{".","a", "A"}));
        arrayList.add(new P(2,new V("E"), new String[]{".","b", "B"}));
        X start = new X(0,arrayList,new HashMap<String,Integer>());
        dfa.add(start);

        //生成DFA
        int i = 0;
        while (i < dfa.size()){
            creatDFA(dfa.get(i));
            i++;
        }
        //打印DFA
        print(dfa);

        //生成分析表,正数表示移进，负数表示规约，20表示acc
        Action = new int[dfa.size()][5];
        Goto = new int[dfa.size()][3];

        //文法后边加个.方便比较
        String [] strings = {"."};
        for (int k = 0; k < f.length; k++) {
            f[k].setRigthExp(concat(f[k].getRigthExp(),strings));
        }
        //遍历dfa
        for (int j = 0; j < dfa.size(); j++) {
            X x = dfa.get(j);
            HashMap<String, Integer> to = x.getTo();
            //判断hash
            if (to.isEmpty()){
                //符合文法
                if ("S".equals(x.getS().get(0).getLeftV().getSingle())){
                    Action[j][4] = 20;
                }
                //规约
                for (int k = 0; k < f.length; k++) {
                    if (x.getS().get(0).equals(f[k])){
                        for (int l = 0; l < 5; l++) {
                            Action[j][l] = -f[k].getNum();
                        }
                    }

                }
            }else {
                //移进
                for (Map.Entry<String, Integer> entry : to.entrySet()) {
                    if (end.contains(entry.getKey())){
                        Action[j][end.indexOf(entry.getKey())] = entry.getValue();
                    }else {
                        Goto[j][not.indexOf(entry.getKey())] = entry.getValue();
                    }
                }
            }
        }

        //打印分析表
        System.out.println("分析表：");
        for (int j = 0; j < dfa.size(); j++) {
            System.out.println(Arrays.toString(Action[j]) + "  " + Arrays.toString(Goto[j]));
        }
    }

    public void creatDFA(X start){
        //遍历项中的每一条
        for (int i = 0; i < start.getS().size(); i++) {
            //遍历->右边
            for (int j = 0; j < start.getS().get(i).getRigthExp().length; j++) {
                //找到.的位置
                if (".".equals(start.getS().get(i).getRigthExp()[j])){
                    //判断.是否在末尾
                    if (j < start.getS().get(i).getRigthExp().length - 1){
                        //移动点的位置
                        String mid;
                        String[] newString = start.getS().get(i).getRigthExp().clone();
                        mid = newString[j];
                        newString[j] = newString[j+1];
                        newString[j+1] = mid;

                        //System.out.println("new:"+Arrays.toString(newString));

                        //判断新的串在前面有没有出现过
                        int flag = 0;
                        for (X value : dfa) {
                            //判断文法
                            if (value.getS().get(0).getLeftV().getSingle().equals(start.getS().get(i).getLeftV().getSingle())
                                    && Arrays.equals(newString, value.getS().get(0).getRigthExp())) {
                                //指向已有的项
                                start.getTo().put(newString[j], value.getNumber());
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0){
                            //生成新的项
                            //.向后移一位后，判断.后面还有没有元素
                            if (j < start.getS().get(i).getRigthExp().length - 2){
                                //变化是否已出现过，例如T->.T*F和T->.T/F都是T
                                if (start.getTo().containsKey(newString[j])){
                                    //将新串加入进去
                                    X x = dfa.get(start.getTo().get(start.getS().get(i).getLeftV().getSingle()));
                                    x.getS().add(new P(start.getS().get(i).getNum(),start.getS().get(i).getLeftV(),newString));
                                    //.后一位是否为终结符
                                    if (!end.contains(newString[j+2])){
                                        for (int k = 2; k < f.length; k++) {
                                            //找到非终结符的文法
                                            if (f[k].getLeftV().getSingle().equals(newString[j+2])){
                                                String [] strings = {"."};
                                                x.getS().add(new P(f[k].getNum(),f[k].getLeftV(), concat(strings,f[k].getRigthExp())));
                                            }
                                        }
                                    }
                                    //新的项取代dfa里旧的项
                                    dfa.set(start.getTo().get(start.getS().get(i).getLeftV().getSingle()),x);
                                }else {//新变化
                                    X node = new X(++number,
                                            new ArrayList<P>(Arrays.asList(new P(start.getS().get(i).getNum(),start.getS().get(i).getLeftV(),newString))),
                                            new HashMap<>());
                                    //.后一位是否为终结符
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
                            }else {//交换后.在最后
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

    //合并两个数组
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    //打印dfa
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
