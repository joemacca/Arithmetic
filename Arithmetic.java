import java.io.*;
import java.util.*;
import java.util.Collections.*;
import java.util.regex.Pattern;

/**
 * Arithmetic class - refactored into a recursive structure that takes advantage
 * of a depth first search.
 * @author Joseph McManamon - 6021556
 */
public class Arithmetic {

	//private static List <Character> ops;
	//private static Deque<Character> ops = new ArrayDeque<Character>();

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String nums = "";
		String nums2 = "";
		int target;
		String order;
		Arithmetic a = new Arithmetic();


		while(sc.hasNext()){
			ArrayList <Integer> numbers = new ArrayList<Integer>();
			nums = sc.nextLine();
			nums2 = sc.nextLine();

			//Useable numbers
			String[] s = nums.split("\\D+");
			for(int i=0;i<s.length;i++){
				numbers.add(Integer.parseInt(s[i]));
			}

			//Order and target number
			String[] s1 =nums2.split("\\W+");
			target = Integer.parseInt(s1[0]);
			order = s1[1];
			String outcome = a.Step(numbers, target, order);
			System.out.println(outcome);

		}
	}

	/**
	* Sets some variables then passes them onto search
	* @param a number list
	* @param target
	* @param order (L or N)
	* @return String outcome of search
	*/
	private String Step(ArrayList<Integer> a, int target, String order){
		int height = 0;
		int numCount = 0;
		int incomplete = 0;
		ArrayList<Character> ops = new ArrayList<Character>();				
        if (searchAndBuild(a, ops, target, order, height, numCount, incomplete)){
            return opToString(a, ops, order);

        }

        return order + " impossible";
    }

	/**
	* Number of checks then recursively passes on each possible operator to the
	* calculator.
	* @param a number list
	* @param ops array.
	* @param target
	* @param order (L or N)
	* @param height reference
	* @param numCount a.k.a value
	* @param incomplete for Normal ordering.
	* @return String outcome of search
	*/
	private boolean searchAndBuild(ArrayList<Integer> a, ArrayList<Character> ops, int target, String order, int height, int numCount, int incomplete){
        int maxHeight = a.size()-1;
		//ops = new ArrayDeque<Character>();
		char[] opArray = {'*', '+'};

        String string = calculate(a, ops, target, order, height, numCount, incomplete);
        if(string.length() > 0){
			String[] s = string.split("\\D+");
			numCount = Integer.parseInt(s[0]);
			incomplete = Integer.parseInt(s[1]);
        }
        int result = numCount + incomplete;
        if (height == maxHeight){
        	return result == target;
        } else if (result > target) {
        	return false;
        } else {
            for (char op : opArray){
                ops.add(op);
                if (searchAndBuild(a, ops, target, order, height + 1, numCount, incomplete)){
                    return true;
                }
                ops.remove(ops.size()-1);
            }
            return false;
        }
    }

    private String calculate(ArrayList<Integer> a, ArrayList<Character> ops, int target, String order, int height, int numCount, int incomplete){
    	if(order.equals("L")){

    		if(ops.size() == 0) numCount = a.get(height);

    		else if(ops.get(height-1) == '*') numCount*=a.get(height);

    		else numCount+=a.get(height);
    		
    		
    	} else {	
    		if(ops.size() == 0) 
    			incomplete = a.get(height);	

    		else if (ops.get(height - 1) == '*') 
    			incomplete *= a.get(height);

    		else {
    			numCount += incomplete; 
    			incomplete = a.get(height);
    		}
    	}
    	String string = numCount + " " + incomplete;
    	return string;
    }

    private String opToString(ArrayList<Integer> a, ArrayList<Character> ops, String order){
        String s = "";
        StringBuilder sb = new StringBuilder(s);
        sb.append(order + " ");
        for(int i = 0; i < a.size(); i++){
            sb.append(a.get(i));
            sb.append(" ");
            if(i < ops.size()){
                sb.append(ops.get(i));
                sb.append(" ");

            }
        }   
        return sb.toString();   
    }
}

