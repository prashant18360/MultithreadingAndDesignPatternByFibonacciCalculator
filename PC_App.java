package ap_assign7;

import java.util.*;
import java.util.concurrent.TimeUnit;


interface pro {
	public void createthread_co();
	public void takesinput();
	public void adding();
	public void display();
	public void allocatethread(int num) throws InterruptedException;
}

class Producer implements Runnable, pro {
	private int total;
	private Scanner sc;
	private Queue<Thread> thread_q;
	private Queue<Integer> pend_num;
	private Consumer con;
	private Map<Integer,Long> res;
	private long Durationinmillis;
	
	public long getDurationinmillis() {
		return Durationinmillis;
	}
	
	
	public Producer() {
		res = new HashMap<Integer,Long>();	
		con = new Consumer();
		thread_q = new LinkedList<Thread>();
		pend_num = new LinkedList<Integer>();
	}
	
	public void createthread_co() {
		
		
		for(int i=0;i<total;i++) {
			thread_q.add(new Thread(con));
		}
		
		takesinput();
			
	}
	
	public void takesinput() {
		sc = new Scanner(System.in);
		
		int n=0,ch=0;
		do {
			 
			 System.out.println("1. Enter the number");
			 System.out.println("2. Print result and exit");
			 ch = sc.nextInt();
			 switch(ch) {
			 	case 1 : n = sc.nextInt();
			 			try {
			 				allocatethread(n);
			 			} catch (InterruptedException e) {
			 				e.printStackTrace();
			 			}
			 			adding();
			 			break;
			 			
			 	case 2 : display();
			 			break;
			 		      
			    default : System.out.println("Wrong input enter again");
			    			break;
			 			  
			 }
			 
				
		}while(ch!=2);
			
				
	}
	
	public void adding() {
		res.put(con.getRes(),getDurationinmillis());
	}
	
	
	
	public void display() {
		for(Map.Entry<Integer,Long> entry : res.entrySet())
			System.out.println("Number = " + entry.getKey() + " Execution Time " + entry.getValue());
	}
	
	
	public void allocatethread(int num) throws InterruptedException {
		 if(thread_q.size() > 0) {
			 
			 con.init(thread_q,num);
			 ArrayList<Thread> arr = new ArrayList<Thread>();
			 arr.add(thread_q.remove());
			 long startTime = System.nanoTime();
			 arr.get(0).start();
			 arr.get(0).join();
			 long endTime = System.nanoTime();
			 long Durationinnano = endTime - startTime;
			 Durationinmillis = TimeUnit.NANOSECONDS.toMillis(Durationinnano);
			 
			 
		 }
		 else {
			 pend_num.add(num);
		 }
	}
	
	public void run() {
		sc = new Scanner(System.in);
		total = sc.nextInt();
		
		this.createthread_co();
		
	}
}

interface cons_temp {
	public void init(Queue<Thread> temp,int num);
	public void calculation() throws InterruptedException;
}

class Consumer implements Runnable {
	
	
	//private Queue<Integer> pend_num;
	private Queue<Thread> thread_q;
	private int num;
	private int result;
	


	public int getRes() {
		return result;
	}
	
	public void init(Queue<Thread> temp,int num) {
		thread_q = temp;
		this.num= num;

	}
	
	public void calculation() throws InterruptedException{
		 Fibonacci left = new Fibonacci(num-1);
		 Fibonacci right = new Fibonacci(num-2);
		 ArrayList<Thread> arr = new ArrayList<Thread>();
		 arr.add(new Thread(left));
		 arr.add(new Thread(right));
		
		 arr.get(0).start(); arr.get(1).start();
		 arr.get(0).join(); arr.get(1).join();
		 
		 result = left.getResult() + right.getResult();
		 
		 
	}
	
	
	public void run() {
		try {
			calculation();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
}

class Fibonacci implements Runnable {
	
	private int result,n;
	
	public Fibonacci(int num) { this.n = num; }

	
	public static int fib(int n) {
		if(n<2)
			return n;
		else
			return fib(n-1) + fib(n-2);
	}
	
	public void run() {
		result = fib(n); 
	}
	
	public int getResult() { return result; }
	
}



public class PC_App {
	
	public void createthread_pr() throws InterruptedException {
		Producer prod = new Producer();
		Thread tobj = new Thread(prod);
		tobj.start();
		tobj.join();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PC_App ob = new PC_App();
		try {
			ob.createthread_pr();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
