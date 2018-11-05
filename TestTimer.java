import java.util.*;

public class TestTimer{
	public static void time(){
		Scanner sc = new Scanner(System.in);
		double input = sc.nextDouble();
		input = input * 1000;
		double startTime = System.currentTimeMillis();
		double currentTime = System.currentTimeMillis();

		while (currentTime < (startTime + input)){
			currentTime = System.currentTimeMillis();
			System.out.println(currentTime);

		}
		System.out.println("Out of loop");
	}
	public static void iteration(){
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		int i = 0;

		while (i <= input){
			System.out.println("in loop");
			i++;
		}
	}

	public static void main(String [] args){
		time();
	}
}