package test;

import bean.Node;

import java.util.ArrayList;

public class WordAnalyze {
    //�ʷ�������������������Ͳ�����
    public ArrayList<Node> strExecute(String str) {
        //�����ʽ��ÿ���ַ�����������洢
        ArrayList<Node> result = new ArrayList<>();
        //ȥ�ո�
        str = str.replaceAll(" ", "");
        //�����û�г��ַǷ��ַ�
        int flag = 0;

        for (int i = 0; i < str.length(); i++) {
            String op = "";
            //������ַ�Ϊ���֣������������һ��Ԫ�أ�ֱ��Ԫ��Ϊ������
            while (str.charAt(i) >= '0' && str.charAt(i) <= '9' || str.charAt(i) == '.') {
                //��Ϊ�������֣���ƴ������
                op += str.charAt(i);
                i++;
                if (i >= str.length())
                    break;
            }
            //���ַ��������ڿգ���һ��Ϊ�����ַ���
            int l = op.length();
            if (!op.equals("") && l > 0) {
                int count = 0;
                for (int j = 0; j < l; j++) {
                    if (op.charAt(j) == '.') count++;
                }
                if (count > 1 || op.charAt(0) == '.') {
                    flag = 1;
                    System.out.println("λ��" + (i - l) + "��" + (i - 1) + "Ϊ�Ƿ��ַ���" + "\"" + op + "\"");
                } else if (count == 1) {
                    //�޷��Ÿ�����
                    Node node = new Node("8", op, Double.parseDouble(op), "double");
                    System.out.println(node.toString());
                    result.add(node);
                } else {
                    //�޷�������
                    Node node = new Node("7", op, Integer.parseInt(op), "int");
                    System.out.println(node.toString());
                    result.add(node);
                }
            }

            if (i >= str.length())
                break;
            //��Ϊ���֣�����������
            char op2 = str.charAt(i);
            if (op2 == '+' || op2 == '-' || op2 == '*' || op2 == '/' || op2 == '(' || op2 == ')') {

                switch (op2) {
                    case '+':
                        Node detail1 = new Node("1", String.valueOf(op2), "NULL", "NULL");//�б�Ϊ+��
                        result.add(detail1);
                        System.out.println(detail1.toString());
                        break;

                    case '-':
                        Node detail2 = new Node("2", String.valueOf(op2), "NULL", "NULL");//�б�Ϊ-��
                        result.add(detail2);
                        System.out.println(detail2.toString());
                        break;

                    case '*':
                        Node detail3 = new Node("3", String.valueOf(op2), "NULL", "NULL");//�б�Ϊ*��
                        result.add(detail3);
                        System.out.println(detail3.toString());
                        break;

                    case '/':
                        Node detail4 = new Node("4", String.valueOf(op2), "NULL", "NULL");//�б�Ϊ/��
                        result.add(detail4);
                        System.out.println(detail4.toString());
                        break;

                    case '(':
                        Node detail5 = new Node("5", String.valueOf(op2), "NULL", "NULL");//�б�Ϊ(��
                        result.add(detail5);
                        System.out.println(detail5.toString());
                        break;

                    case ')':
                        Node detail6 = new Node("6", String.valueOf(op2), "NULL", "NULL");//�б�Ϊ)��
                        result.add(detail6);
                        System.out.println(detail6.toString());
                        break;
                    default:
                        break;
                }
            } else {
                flag = 1;
                System.out.println("λ��" + i + "Ϊ���Ϸ����ַ���" + "\"" + op2 + "\"");
            }
        }
        if (flag == 1) {
            result = null;
        }
        return result;  //���ر��ʽ����
    }
}
