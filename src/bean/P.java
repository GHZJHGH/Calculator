package bean;

import java.util.Arrays;
import java.util.Objects;

//文法的产生式
public class P {
	private int num;
	private V leftV;
	private String rigthExp[];

	//文法序号、箭头左边、箭头右边
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		P p = (P) o;
		return num == p.num && leftV.getSingle().equals(p.leftV.getSingle()) && Arrays.equals(rigthExp, p.rigthExp);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(num, leftV);
		result = 31 * result + Arrays.hashCode(rigthExp);
		return result;
	}
}
