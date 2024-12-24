import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

public class P2 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./demo.txt"));
      int numSafeReports = 0;
      while(scanner.hasNextLine()) {
        String[] reportStr = scanner.nextLine().split(" ");
        int[] report = new int[reportStr.length];
        for (int i = 0; i < report.length; i++) {
          report[i] = Integer.parseInt(reportStr[i]);
        }
        int numConsistentLevelChanges = 0;
        int prev = numConsistentLevelChanges;
        int leftPtr = 0;
        int rightPtr = 1;
        boolean hasSkipped = false;
        boolean isSafe = true;
        while(leftPtr <= report.length - 2) {
          int current = report[leftPtr];
          int adjacent = report[rightPtr];
          int diff = Math.abs(current - adjacent);
          if(current < adjacent) {
            numConsistentLevelChanges++;
          } else if(current > adjacent) {
            numConsistentLevelChanges--;
          }
          if (Math.abs(numConsistentLevelChanges) < Math.abs(prev) 
          || !(diff >= 1 && diff <= 3) || diff == 0) {
            if (hasSkipped) {
              isSafe = false;
              break;
            }
            if (leftPtr == 0 || leftPtr == report.length - 2) {
              leftPtr++;
              rightPtr = leftPtr + 1;
            } else {
              rightPtr++;
            }
            hasSkipped = true;
            prev = numConsistentLevelChanges;
            continue;
          }
          leftPtr++;
          rightPtr = leftPtr + 1;
          prev = numConsistentLevelChanges;
        }
        if(isSafe) {
          System.out.println();
          numSafeReports++;
        }
        for (int i = 0; i < report.length; i++) {
          System.out.print(report[i] + " ");
        }
        System.out.println(isSafe);
      }
      System.out.println("Number of safe reports: " + numSafeReports);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
