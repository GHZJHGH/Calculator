package bean;

//�ķ��Ĳ���ʽ
public class P {
	private int num;
	private V leftV;
	private String rigthExp[];

	//�ķ���š���ͷ��ߡ���ͷ�ұ�
	public P(int num,V leftV,String rigthExp[]){
		this.num=num;
		this.leftV=leftV;
		this.rigthExp=rigthExp;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public V getLeftV() {
		return leftV;
	}

	public void setLeftV(V leftV) {
		this.leftV = leftV;
	}

	public String[] getRigthExp() {
		return rigthExp;
	}

	public void setRigthExp(String[] rigthExp) {
		this.rigthExp = rigthExp;
	}
	
}
