/*
	Viktor Kirillov
 	vkiril2@uic.edu
	My own versions of the stack and queue data structures.
*/

import java.util.ArrayList;

public class GLProject {

	// Returns all values in an ArrayList
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello generic lists");

		GenericStack<Integer> q = new GenericStack<Integer>(1);
		q.add(2);
		q.add(4);
		q.add(7);

//		q.print();

		while(q.getHead() != null) {
			System.out.println(q.pop());
		}

		q.add(9);

//		q.print();
		System.out.println(q.dumpList());
	}

}
