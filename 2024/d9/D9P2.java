import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class D9P2 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<Block2> layout = new ArrayList<>();
      String[] s = scanner.nextLine().split("");
      for (int i = 0; i < s.length; i++) {
        if(i % 2 != 0) {
          for(int x = 0; x < Long.parseLong(s[i]); x++) {
            layout.add(new Block2(-1));
          }
        } else {
          for(int x = 0; x < Long.parseLong(s[i]); x++) {
            layout.add(new Block2(i / 2));
          }
        }
      }
      int rightIndex = layout.size() - 1;
      while(true) {
        if(rightIndex == 0) {
          break;
        }
        while(layout.get(rightIndex).id() == -1 && rightIndex > 0) {
          rightIndex--;
        }
        int j = rightIndex;
        long currentId = layout.get(rightIndex).id();
        while(layout.get(j).id() == currentId && j > 0) {
          j--;
        }
        int numFileBlocks = rightIndex - j;
        List<List<Integer>> spaceBlks = spaces(layout, j + 1);

        for(List<Integer> blk : spaceBlks) {
          if(blk.get(2) < numFileBlocks) {
            continue;
          }
          for(int x = 0; x < numFileBlocks; x++)  {
            Block2 tmp = new Block2(layout.get(blk.get(0) + x).id());
            layout.set(blk.get(0) + x, layout.get(rightIndex - x));
            layout.set(rightIndex - x, tmp);
          }
          break;
        }
        rightIndex = j;
      }
      long checksum = 0;
      for(int i = 0; i < layout.size(); i++) {
        if(layout.get(i).id() == -1) {
          continue;
        }
        checksum += layout.get(i).id() * i;
      }
      System.out.println(checksum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static List<List<Integer>> spaces(List<Block2> layout, int rightIndex) {
    int i = 0;
    int j = i;
    List<List<Integer>> spaceBlks = new ArrayList<>();
    while(true) {
      while(layout.get(i).id() != -1 && i < rightIndex) {
        i++;
      }
      if(i == rightIndex) {
        break;
      }
      j = i;
      while(layout.get(j).id() == -1 && i < rightIndex) {
        j++;
      }
      spaceBlks.add(List.of(i, j - 1, j - i));
      i = j;
    }
    return spaceBlks;
  }
}
