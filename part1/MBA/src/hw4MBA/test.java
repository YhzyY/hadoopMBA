package hw4MBA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    public static void main(String []args) {
		String value = "[A, B]  29";
		System.out.println(value);
		String[] products = value.toString().split("\\[|,|\\]|\\s+");
		System.out.println("this is 0 : " + products[0]);
		System.out.println("this is 1 : " + products[1]);
		System.out.println("this is 2 : " + products[2]);
		System.out.println("this is 3 : " + products[3]);
		List<String> list = new ArrayList<String>(Arrays.asList(products));
		list.removeAll(Arrays.asList("", null));
		// list = [P1, P2, 1]
		System.out.println("this is list : " + list);
		String key1 = list.get(0);
		List<String> temp1 = Arrays.asList(list.get(1), list.get(2));
		String key2 = list.get(1);
		List<String> temp2 = Arrays.asList(list.get(0), list.get(1));
		System.out.println(key1.toString());
		System.out.println(((String[]) temp1.toArray())[0]);
		System.out.println(((String[]) temp1.toArray())[1]);
		System.out.println(key2.toString());
		System.out.println();
		String[] output = (String[]) temp2.toArray(new String[temp2.size()]);
		System.out.println((String[]) temp2.toArray());
		System.out.println(output);
    }
}