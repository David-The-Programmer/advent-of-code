import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

public class P1 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      int numSafeReports = 0;
      while(scanner.hasNextLine()) {
        String[] reportStr = scanner.nextLine().split(" ");
        int[] report = new int[reportStr.length];
        for (int i = 0; i < report.length; i++) {
          report[i] = Integer.parseInt(reportStr[i]);
        }
        int numConsistentLevelChanges = 0;
        boolean isOutOfDiffRange = false;
        for (int i = 0; i < report.length - 1; i++) {
          int curr = report[i];
          int adjacent = report[i + 1];
          int diff = Math.abs(curr - adjacent);
          if (!(diff >= 1 && diff <= 3)) {
            isOutOfDiffRange = true;
            break;
          }
          if(curr < adjacent) {
            numConsistentLevelChanges++;
          } else if(curr > adjacent) {
            numConsistentLevelChanges--;
          }
        }
        if(isOutOfDiffRange) {
          continue;
        }
        if(Math.abs(numConsistentLevelChanges) != report.length - 1) {
          continue;
        }
        numSafeReports++;
      }
      System.out.println("Number of safe reports: " + numSafeReports);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
