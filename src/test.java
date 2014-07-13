import java.util.ArrayList;


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList a=new ArrayList();
		a.add("a");
		a.add("b");
		a.add("c");
		a.add("d");
		a.add("e");
		while(a.size()!=1){
			int i=0;
			System.out.print(a.get(i));
			System.out.println(a.get(i+1));
			a.remove(i+1);
		}
	}

}
