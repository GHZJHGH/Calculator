package test;


import bean.Node;
import bean.P;
import bean.V;

import java.util.ArrayList;
import java.util.Stack;

public class GrammarAnalyze {
	
	private Node particularNode=new Node(null, "#", null, null);
	private ArrayList<Node> input;//输入串
	private ArrayList<Node> inputQueue=new ArrayList<Node>();//输入栈
	private ArrayList<Node> singleQueue=new ArrayList<Node>();//符号栈
	private ArrayList<Integer> analyzeQueue=new ArrayList<Integer>();//分析栈(状态栈)

	
	 GrammarAnalyze(ArrayList<Node> input){
		this.inputQueue.add(particularNode);//输入串在进行语法分析前要先将结束符压入栈底
		this.singleQueue.add(particularNode);//符号栈初始状态下为#
		this.analyzeQueue.add(0);
		this.input = input;

	}

	//语法、语义分析
	public void program(){
		Stack<Integer> statusStack = new Stack<>();//状态栈
		Stack<Node> symbolStack = new Stack<>();//符号栈
		Node reduceNode = null;//规约后压入符号栈的单词结点
		int ti=1;//中间元素
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
				System.out.println("语法错误");
				break;
			}else if(action == ParserType.ACC){
				System.out.println("该输入串符合语法要求并已被接收");
				System.out.println("计算结果："+symbolStack.get(1).getValue());

			}else if (action>0){
				statusStack.push(action);
				symbolStack.push(n);
				System.out.println(n+"加入符号栈");
				System.out.println(action+"加入状态栈");
				System.out.println("状态栈" + statusStack);
				System.out.println("符号栈" + symbolStack+"\n");
				status = action;
				continue;
			}else {
				for(P p:ParserType.pset){//寻找产生式
					if(p.getNum()== Math.abs(action)){
						int noZeroNum=0;
						//规约中间结果存储列表
						ArrayList<Node>  reduceNodeList=new ArrayList<Node>();

						//计算要被规约元素的个数
						for(int i=0;i<p.getRigthExp().length;i++){
							if(!p.getRigthExp()[i].equals("0")){
								noZeroNum++;
							}
						}
						//将被规约的词出栈，并同步状态栈
						while(noZeroNum>0){
							System.out.println(symbolStack.peek()+"被规约");
							reduceNodeList.add(symbolStack.pop());
							statusStack.pop();
							noZeroNum--;
						}
						//获取规约后的单词
						V ch=p.getLeftV();
						int index=0;
						//找到在goto表对应的值
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
						System.out.println("rigthExp："+rigthExp);
						if(rigthExp.contains("+")||rigthExp.contains("-")||rigthExp.contains("*")||rigthExp.contains("/")){
							//规约后的单词四元式
							Node saveNode=new Node(p.getRigthExp()[1],reduceNodeList.get(2).getValue().toString(),
									reduceNodeList.get(0).getValue().toString(),"t"+(ti++));

							//输出中间四元式
							System.out.println(saveNode.toString());

							//将单词串转换成真实的字符运算并用reduceNode存储中间结果
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
						//规约之后将中间结果压入符号栈
						symbolStack.push(reduceNode);

						status = ParserType.go[statusStack.peek()][index];
						//向状态栈添加规约后的状态
						statusStack.push(status);

						System.out.println(symbolStack.peek()+"加入符号栈");
						System.out.println(statusStack.peek()+"加入状态栈");
						System.out.println("状态栈" + statusStack);
						System.out.println("符号栈" + symbolStack+"\n");
						j--;
						break;
					}
				}
			}
		}
	}
}
