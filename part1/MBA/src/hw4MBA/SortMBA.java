package hw4MBA;
//  some code from:  http://www.yanglajiao.com/article/tom_fans/78367792
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Arrays;

public class SortMBA {
	
	
	private static final int ArrayList = 0;

	public static List<List<String>>  findsort(Collection<String> elements, int n) {
		
		
		
		List result = new ArrayList<List>();
		
		if(n == 0) {
			result.add(new ArrayList());
			return result;
		}
		
		
		List<List<String>> combinations = findsort(elements, n - 1);
		
		for(List<String> combination : combinations) {
					
			for(String element : elements) {
				
				if(combination.contains(element)) {
					continue;
				}
				
				List<String> list = new ArrayList<String>();
				list.addAll(combination);
				
				if(list.contains(element)) {
					continue;
				}
				
				list.add(element);
				Collections.sort(list);
				
				if(result.contains(list)){
					continue;
				}
				
				result.add(list);
				
			}
		}
				
		return result;
		
	}
//	
//	public static void main(String[] args) throws IOException {
//		File file = new File("/Users/ziyi/document/cs1699/hw4/input.txt");
//		BufferedReader reader = null;
//		reader = new BufferedReader(new FileReader(file));
//		String tempString = null;
//		List tempList = new ArrayList();
//		List finalList = new ArrayList();
//		while ((tempString = reader.readLine()) != null) {
////			String content = (String) tempString.subSequence(tempString.length()-15, tempString.length());
//			String content1 = (String) tempString.subSequence(tempString.length()-13, tempString.length()-12);
//			String content2 = (String) tempString.subSequence(tempString.length()-8, tempString.length()-7);
//			String content3 = (String) tempString.subSequence(tempString.length()-3, tempString.length()-2);
//			tempList.add(content1);
//			tempList.add(content2);
//			tempList.add(content3);
////			System.out.println(SortMBA.findsort(tempList, 2));
//			System.out.println(tempList);
//			finalList.addAll(SortMBA.findsort(tempList, 2));
//			tempList.clear();
//		}
//		System.out.println(finalList);
//        if (reader != null) {
//            reader.close();
//        }
//	}
//
//	private static List ArrayList() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
