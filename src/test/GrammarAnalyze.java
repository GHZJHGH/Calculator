package test;


import bean.Node;
import bean.P;
import bean.V;

import java.util.ArrayList;
import java.util.Stack;

public class GrammarAnalyze {
	
	private Node particularNode=new Node(null, "#", null, null);
	private ArrayList<Node> input;//���봮
	private ArrayList<Node> inputQueue=new ArrayList<Node>();//����ջ
	private ArrayList<Node> singleQueue=new ArrayList<Node>();//����ջ
	private ArrayList<Integer> analyzeQueue=new ArrayList<Integer>();//����ջ(״̬ջ)

	
	 GrammarAnalyze(ArrayList<Node> input){
		this.inputQueue.add(particularNode);//���봮�ڽ����﷨����ǰҪ�Ƚ�������ѹ��ջ��
		this.singleQueue.add(particularNode);//����ջ��ʼ״̬��Ϊ#
		this.analyzeQueue.add(0);
		this.input = input;

	}

	//�﷨���������
	public void program(){
		Stack<Integer> statusStack = new Stack<>();//״̬ջ
		Stack<Node> symbolStack = new Stack<>();//����ջ
		Node reduceNode = null;//��Լ��ѹ�����ջ�ĵ��ʽ��
		int ti=1;//�м�Ԫ��
		symbolStack.push(particularNode);
		input.add(particularNode);
		statusStack.push(0);

		int status = 0;
		int action = 0;
		for (int j=0;j<input.size();j++){
			Node n = input.get(j);
			if (n.getNumType() == null){
				action = ParserType.action[status][7];
			}else if (Integer.parseInt(n.getNumType())==7||Integer.parseInt(n.getNumType())==8){
				action = ParserType.action[status][0];
			}else {
				action = ParserType.action[status][Integer.parseInt(n.getNumType())];
			}

			if (action==0){
				System.out.println("�﷨����");
				break;
			}else if(action == ParserType.ACC){
				System.out.println("�����봮�����﷨Ҫ���ѱ�����");
				System.out.println("��������"+symbolStack.get(1).getValue());

			}else if (action>0){
				statusStack.push(action);
				symbolStack.push(n);
				System.out.println(n+"�������ջ");
				System.out.println(action+"����״̬ջ");
				System.out.println("״̬ջ" + statusStack);
				System.out.println("����ջ" + symbolStack+"\n");
				status = action;
				continue;
			}else {
				for(P p:ParserType.pset){//Ѱ�Ҳ���ʽ
					if(p.getNum()== Math.abs(action)){
						int noZeroNum=0;
						//��Լ�м����洢�б�
						ArrayList<Node>  reduceNodeList=new ArrayList<Node>();

						//����Ҫ����ԼԪ�صĸ���
						for(int i=0;i<p.getRigthExp().length;i++){
							if(!p.getRigthExp()[i].equals("0")){
								noZeroNum++;
							}
						}
						//������Լ�Ĵʳ�ջ����ͬ��״̬ջ
						while(noZeroNum>0){
							System.out.println(symbolStack.peek()+"����Լ");
							reduceNodeList.add(symbolStack.pop());
							statusStack.pop();
							noZeroNum--;
						}
						//��ȡ��Լ��ĵ���
						V ch=p.getLeftV();
						int index=0;
						//�ҵ���goto���Ӧ��ֵ
						for(V v:ParserType.vn){
							if(!v.equals(ch)){
								index++;
							}else
								break;
						}

						StringBuilder result=new StringBuilder();
						for(String str:p.getRigthExp()){
							result.append(str);
						}
						String rigthExp=result.toString();
						System.out.println("rigthExp��"+rigthExp);
						if(rigthExp.contains("+")||rigthExp.contains("-")||rigthExp.contains("*")||rigthExp.contains("/")){
							//��Լ��ĵ�����Ԫʽ
							Node saveNode=new Node(p.getRigthExp()[1],reduceNodeList.get(2).getValue().toString(),
									reduceNodeList.get(0).getValue().toString(),"t"+(ti++));

							//����м���Ԫʽ
							System.out.println(saveNode.toString());

							//�����ʴ�ת������ʵ���ַ����㲢��reduceNode�洢�м���
							switch(p.getRigthExp()[1]){
								case "+":
									reduceNode=ReduceTools.reduceByAdd(reduceNodeList, p);break;
								case "-":
									reduceNode=ReduceTools.reduceByJian(reduceNodeList, p); break;
								case "*":
									reduceNode=ReduceTools.reduceByCheng(reduceNodeList, p);break;
								case "/":
									reduceNode=ReduceTools.reduceByChu(reduceNodeList, p); break;
								default:
									break;
							}
						}else if(rigthExp.contains("i")){
							reduceNode=ReduceTools.reduceFromi(reduceNodeList, p);
						}else if(rigthExp.contains("(")&&rigthExp.contains(")")){
							reduceNode=ReduceTools.reduceByKuoHao(reduceNodeList, p);
						}/*else if(rigthExp.contains("-F")){//F->-F
													 reduceNode=ReduceTools.reduceByFu(reduceNodeList, p);
												 }*/else{//T->F//E->T//S->E
							reduceNode=ReduceTools.reduceFromOther(reduceNodeList, p);
						}
						//��Լ֮���м���ѹ�����ջ
						symbolStack.push(reduceNode);

						status = ParserType.go[statusStack.peek()][index];
						//��״̬ջ��ӹ�Լ���״̬
						statusStack.push(status);

						System.out.println(symbolStack.peek()+"�������ջ");
						System.out.println(statusStack.peek()+"����״̬ջ");
						System.out.println("״̬ջ" + statusStack);
						System.out.println("����ջ" + symbolStack+"\n");
						j--;
						break;
					}
				}
			}
		}
	}
}
