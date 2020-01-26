

public class HomeworkOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Statistics s = new Statistics("values2.txt", 6);
		s.printMean();
		s.printMedian();
		s.printSTD();
		
		Statistics s2 = new Statistics("values.txt", 5);
		s2.printMean();
		s2.printMedian();
		s2.printSTD();
		
	}
}
