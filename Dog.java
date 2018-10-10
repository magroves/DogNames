
public class Dog {
	private String dogName;
	private int count;
	
	public Dog() {
		dogName = null;
		count = 0;
	}
	
	public Dog(String name, int c) {
		this.dogName = name;
		this.count = c;
	}
	
	public String getName() {
		return this.dogName;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setName(String n) {
		this.dogName = n;
	}
	
	public void setCount(int c) {
		this.count = c;
	}

}
