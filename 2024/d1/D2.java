import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashMap;

public class D2 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      ArrayList<Integer> left = new ArrayList<>();
      HashMap<Integer, Integer> hashMap = new HashMap<>();
      while(scanner.hasNextLine()) {
        String[] ids = scanner.nextLine().split("   ");
        left.add(Integer.parseInt(ids[0]));
        int rightValue = Integer.parseInt(ids[1]);
        if(!hashMap.containsKey(rightValue)) {
          hashMap.put(rightValue, 1);
        } else {
          hashMap.put(rightValue, hashMap.get(rightValue) + 1);
        }
      }
      int similarityScore = 0;
      for(int i = 0; i < left.toArray().length; i++) {
        if(hashMap.containsKey(left.get(i))) {
          similarityScore += left.get(i) * hashMap.get(left.get(i));
        } 
      }
      System.out.println("Total Distance: " + similarityScore);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
