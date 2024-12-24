import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class D13P1 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      int tokens = 0;
      while(true) {
        String[] btnAVect = scanner.nextLine().split(":")[1].split(",");
        float xA = Float.parseFloat(btnAVect[0].split("\\+")[1]);
        float yA = Float.parseFloat(btnAVect[1].split("\\+")[1]);

        String[] btnBVect = scanner.nextLine().split(":")[1].split(",");
        float xB = Float.parseFloat(btnBVect[0].split("\\+")[1]);
        float yB = Float.parseFloat(btnBVect[1].split("\\+")[1]);

        String[] prizeVect = scanner.nextLine().split(":")[1].split(",");
        float xPrize = Float.parseFloat(prizeVect[0].split("=")[1]);
        float yPrize = Float.parseFloat(prizeVect[1].split("=")[1]);

        float[][] matrix = new float[2][3];
        matrix[0][0] = xA;
        matrix[0][1] = xB;
        matrix[0][2] = xPrize;
        matrix[1][0] = yA;
        matrix[1][1] = yB;
        matrix[1][2] = yPrize;

        // Gaussian elimination via partial pivoting
        if(matrix[0][0] < matrix[1][0]) {
          float[] topRow = { matrix[0][0], matrix[0][1], matrix[0][2] };
          float[] bottomRow = { matrix[1][0], matrix[1][1], matrix[1][2] };
          matrix[0] = bottomRow;
          matrix[1] = topRow;
        }

        float pivotElement = matrix[0][0];
        matrix[0][0] /= pivotElement;
        matrix[0][1] /= pivotElement;
        matrix[0][2] /= pivotElement;

        float firstRowMultiple = matrix[1][0];
        matrix[1][0] -= firstRowMultiple * matrix[0][0];
        matrix[1][1] -= firstRowMultiple * matrix[0][1];
        matrix[1][2] -= firstRowMultiple * matrix[0][2];

        float numBPresses = matrix[1][2] / matrix[1][1];
        float numAPresses = matrix[0][2] - (numBPresses * matrix[0][1]);
        // handle floating point errors
        if(Math.abs(numAPresses - Math.round(numAPresses)) <= 0.001) {
          if(Math.abs(numBPresses - Math.round(numBPresses)) <= 0.001) {
            tokens += (int) Math.round(numAPresses) * 3 + Math.round(numBPresses) * 1;
          }
        }
        if(!scanner.hasNextLine()) {
          break;
        }
        // skip the newline
        scanner.nextLine();
      }
      System.out.println(tokens);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
