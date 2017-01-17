package temp;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class DRoller {
	
	public static class result{
		
		protected int amount;
		protected int size;
		protected int bonus;
		protected int sum;
		
		public int getAmount(){
			return amount;
		}
		
		public int getSize(){
			return size;
		}
		
		public int getBonus(){
			return bonus;
		}
		
		public int hasBonus(){
			if(bonus == 0) return bonus;
			else return 1;
		}
		public result(int amount, int size, int sum){
			this.amount = amount;
			this.size = size;
			this.sum = sum;
		}
		
		public result(int amount, int size, int bonus, int sum){
			this.amount = amount;
			this.size = size;
			this.bonus = bonus;
			this.sum = sum;
		}
		
		public String toString(){
			String s = "";
			s += amount + "D" + size;
			if(bonus!=0) s+= " + " + bonus;
			s+= " : " + sum;
			return s;
		}
	}
	
	public static class critResult extends result{

		//public int amount;
		public critResult(int amount, int size, int bonus) {
			super(amount, size, bonus);		
		}
		
		public critResult(int amount, int size, int bonus, int sum) {
			super(amount, size, bonus, sum);		
		}
		
		public void setAmount(int a){ //OBJ08-J ---------------------------------------------------------------
			amount = a;
		}
		
		public String toString(){
			String s = "";
			s += amount + "D" + size + " + " + bonus + " : " + sum + " was crit";
			return s;
		}
		
	}
		
	public static class resultStack{ //MSC07-J-----------------------------------------------------------------
		Stack<result> s;
		private static resultStack instance;
		public resultStack(){
			s = new Stack<result>();
		}
		
		public static synchronized  resultStack getInstance(){
			return instance;
		}
	}
	
	public static void main(String[] args) throws Exception{
				
		int amount,size,bonus,total;	
		String line = "";
		String[] parts;
		
		resultStack rs = new resultStack();
		
		boolean isCrit = false; //MSC04-J limited use of definition -------------------------------------------
		int counter = 1; //same as above 
	
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Input a die roll.");
		
		line = input.nextLine();
		
			while(!line.equals("q")){
				try{		
					parts = line.split("d|\\+");
								
					amount = Integer.parseInt(parts[0]);
					size = Integer.parseInt(parts[1]);
					
					if(parts.length>=4) throw new Exception("too many parts"); //ERR07-J ----------------------
					if(parts.length==3)bonus = Integer.parseInt(parts[2]);
					else
						bonus = 0;
					
					total = 0;
					
					for(int i = 0; i<amount; i++){
						
						int each = new Random().nextInt(size)+1;				
						total+= each;
						if(isCrit || each == size) isCrit = true;
						System.out.print(each + " + ");				
					}
					
					System.out.print("Bonus(" + bonus + ") = ");
					total+= bonus; //NUM00-J  -----------------------------------------------------------------
					System.out.println(total);
					
					if(isCrit) rs.s.push(new critResult(amount, size, bonus, total));
					else rs.s.push(new result(amount, size, bonus, total));
					
					System.out.println("Input a die roll. To quit type q");
					line = input.nextLine();
				}
				catch(Exception e){ //don't catch generic exceptions
					System.out.println("Incorrect Format!\nInput a die roll. To quit type q");
					line = input.nextLine();
					bonus = 0;
					total = 0;
				}
			}
			
			
			
		while(!rs.s.empty()){
			System.out.println(counter + ": " + rs.s.pop().toString());
			counter++;
		}
		
	}
}