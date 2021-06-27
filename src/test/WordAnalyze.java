package test;

import bean.Node;

import java.util.ArrayList;

public class WordAnalyze {
    //词法分析，并分离操作符和操作数
    public ArrayList<Node> strExecute(String str) {
        //将表达式的每个字符用数组链表存储
        ArrayList<Node> result = new ArrayList<>();
        //去空格
        str = str.replaceAll(" ", "");
        //标记有没有出现非法字符
        int flag = 0;

        for (int i = 0; i < str.length(); i++) {
            String op = "";
            //若这个字符为数字，则继续遍历下一个元素，直到元素为非数字
            while (str.charAt(i) >= '0' && str.charAt(i) <= '9' || str.charAt(i) == '.') {
                //若为连续数字，则拼接起来
                op += str.charAt(i);
                i++;
                if (i >= str.length())
                    break;
            }
            //若字符串不等于空，则一定为数字字符串
            int l = op.length();
            if (!op.equals("") && l > 0) {
                int count = 0;
                for (int j = 0; j < l; j++) {
                    if (op.charAt(j) == '.') count++;
                }
                if (count > 1 || op.charAt(0) == '.') {
                    flag = 1;
                    System.out.println("位置" + (i - l) + "到" + (i - 1) + "为非法字符：" + "\"" + op + "\"");
                } else if (count == 1) {
                    //无符号浮点数
                    Node node = new Node("8", op, Double.parseDouble(op), "double");
                    System.out.println(node.toString());
                    result.add(node);
                } else {
                    //无符号整数
                    Node node = new Node("7", op, Integer.parseInt(op), "int");
                    System.out.println(node.toString());
                    result.add(node);
                }
            }

            if (i >= str.length())
                break;
            //不为数字（即操作符）
            char op2 = str.charAt(i);
            if (op2 == '+' || op2 == '-' || op2 == '*' || op2 == '/' || op2 == '(' || op2 == ')') {

                switch (op2) {
                    case '+':
                        Node detail1 = new Node("1", String.valueOf(op2), "NULL", "NULL");//判别为+号
                        result.add(detail1);
                        System.out.println(detail1.toString());
                        break;

                    case '-':
                        Node detail2 = new Node("2", String.valueOf(op2), "NULL", "NULL");//判别为-号
                        result.add(detail2);
                        System.out.println(detail2.toString());
                        break;

                    case '*':
                        Node detail3 = new Node("3", String.valueOf(op2), "NULL", "NULL");//判别为*号
                        result.add(detail3);
                        System.out.println(detail3.toString());
                        break;

                    case '/':
                        Node detail4 = new Node("4", String.valueOf(op2), "NULL", "NULL");//判别为/号
                        result.add(detail4);
                        System.out.println(detail4.toString());
                        break;

                    case '(':
                        Node detail5 = new Node("5", String.valueOf(op2), "NULL", "NULL");//判别为(号
                        result.add(detail5);
                        System.out.println(detail5.toString());
                        break;

                    case ')':
                        Node detail6 = new Node("6", String.valueOf(op2), "NULL", "NULL");//判别为)号
                        result.add(detail6);
                        System.out.println(detail6.toString());
                        break;
                    default:
                        break;
                }
            } else {
                flag = 1;
                System.out.println("位置" + i + "为不合法的字符：" + "\"" + op2 + "\"");
            }
        }
        if (flag == 1) {
            result = null;
        }
        return result;  //返回表达式链表
    }
}
