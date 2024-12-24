import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class D9P1 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<Block> layout = new ArrayList<>();
      String[] s = scanner.nextLine().split("");
      for (int i = 0; i < s.length; i++) {
        if(i % 2 != 0) {
          String space = ".";
          for(int x = 0; x < Integer.parseInt(s[i]); x++) {
            layout.add(new Block("."));
          }
        } else {
          String id = String.valueOf(i / 2);
          for(int x = 0; x < Integer.parseInt(s[i]); x++) {
            layout.add(new Block(id));
          }
        }
      }
      int leftIndex = 0;
      int rightIndex = layout.size() - 1;
      // for left index, keep going right until u reach a space(dot)
      // for right index, keep going left until u reach a number
      while(true) {
        while(!layout.get(leftIndex).getVal().equals(".") && leftIndex < rightIndex) {
          leftIndex++;
        }
        while(!isDigit(layout.get(rightIndex).getVal().charAt(0)) && rightIndex > leftIndex) {
          rightIndex--;
        }
        if(leftIndex == rightIndex) {
          break;
        }
        Block tmp = new Block(layout.get(leftIndex).getVal());
        layout.set(leftIndex, layout.get(rightIndex));
        layout.set(rightIndex, tmp);
      }
      List<Integer> checksum = new ArrayList<>();
      for(int i = 0; i < layout.size(); i++) {
        if(layout.get(i).getVal().equals(".")) {
          continue;
        }
        checksum.add(Integer.parseInt(layout.get(i).getVal()) * i);
      }
      System.out.println(checksum);
      long sum = 0;
      for(int i = 0; i < checksum.size(); i++) {
        sum += checksum.get(i);
      }
      System.out.println(sum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public static boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

}
