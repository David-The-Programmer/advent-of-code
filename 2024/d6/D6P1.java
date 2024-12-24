import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

public class D6P1 {

  public static void main(String[] args) {
    HashSet<String> guardOrientation = new HashSet<>();
    guardOrientation.add("^");
    guardOrientation.add("v");
    guardOrientation.add("<");
    guardOrientation.add(">");

    HashMap<String, List<Integer>> posToStep = new HashMap<>();
    posToStep.put("^", List.of(0, -1));
    posToStep.put("v", List.of(0, 1));
    posToStep.put("<", List.of(-1, 0));
    posToStep.put(">", List.of(1, 0));

    HashMap<String, String> posToTurnRightPos = new HashMap<>();
    posToTurnRightPos.put("^", ">");
    posToTurnRightPos.put(">", "v");
    posToTurnRightPos.put("v", "<");
    posToTurnRightPos.put("<", "^");
    int guardXPos = 0;
    int guardYPos = 0;
    String currGuardOrientation = "";
    try {
      List<List<String>> grid = new ArrayList<>();
      Scanner scanner = new Scanner(new File("./input.txt"));
      while(scanner.hasNextLine()) {
        grid.add(Arrays.asList(scanner.nextLine().split("")));
      }
      for(int r = 0; r < grid.size(); r++) {
        for(int c = 0; c < grid.get(0).size(); c++) {
          if(guardOrientation.contains(grid.get(r).get(c))) {
            guardXPos = c;
            guardYPos = r;
            currGuardOrientation = grid.get(guardYPos).get(guardXPos);
            break;
          }
        }
      }
      while(true) {
        int futureXStep = guardXPos + posToStep.get(currGuardOrientation).get(0);
        int futureYStep = guardYPos + posToStep.get(currGuardOrientation).get(1);
        if(futureXStep < 0 || futureXStep > grid.get(0).size() - 1) {
          grid.get(guardYPos).set(guardXPos, "X");
          break;
        }
        if(futureYStep < 0 || futureYStep > grid.size() - 1) {
          grid.get(guardYPos).set(guardXPos, "X");
          break;
        }
        if(grid.get(futureYStep).get(futureXStep).equals("#")) {
          currGuardOrientation = posToTurnRightPos.get(currGuardOrientation);
          continue;
        }
        grid.get(guardYPos).set(guardXPos, "X");
        guardXPos = futureXStep;
        guardYPos = futureYStep;
      }
      int numDistinctPos = 0;
      for(int r = 0; r < grid.size(); r++) {
        for(int c = 0; c < grid.get(0).size(); c++) {
          if(grid.get(r).get(c).equals("X")) {
            numDistinctPos++;
          }
        }
      }
      for(int r = 0; r < grid.size(); r++) {
        for(int c = 0; c < grid.get(0).size(); c++) {
          System.out.print(grid.get(r).get(c));
        }
        System.out.print("\n");
      }
      System.out.println("Number of distinct positions = " + numDistinctPos);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
