import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

public class D12P2 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      HashMap<String, List<Pair<Integer, Integer>>> regions = new HashMap<>();
      List<List<String>> map = new ArrayList<>();
      while(scanner.hasNextLine()) {
        map.add(Arrays.asList(scanner.nextLine().split("")));
      }
      for(int r = 0; r < map.size(); r++) {
        for(int c = 0; c < map.get(0).size(); c++) {
          String regionType = map.get(r).get(c);
          if(regions.containsKey(regionType)) {
            List<Pair<Integer, Integer>> coords = regions.get(regionType);
            coords.add(new Pair<>(c, r));
            regions.put(regionType, coords);
            continue;
          }
          List<Pair<Integer, Integer>> coords = new ArrayList<>();
          coords.add(new Pair<>(c, r));
          regions.put(regionType, coords);
        }
      }
      long price = 0;
      for(Map.Entry<String, List<Pair<Integer, Integer>>> entry: regions.entrySet()) {
        String regionType = entry.getKey();
        List<Pair<Integer, Integer>> regionCoords = entry.getValue();
        List<List<Pair<Integer, Integer>>> separated = separatedRegions(regionType, regionCoords, map);
        for(List<Pair<Integer, Integer>> subRegion: separated) {
          int perimeter = numCorners(regionType, subRegion, map);
          price += perimeter * subRegion.size();
        }
      }
      System.out.println(price);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void printRegions(HashMap<String, List<Pair<Integer, Integer>>> regions) {
    for(Map.Entry<String, List<Pair<Integer, Integer>>> entry: regions.entrySet()) {
      String regionType = entry.getKey();
      System.out.print(regionType);
      List<Pair<Integer, Integer>> regionCoords = entry.getValue();
      System.out.print(regionCoords + "\n");
    }
  }

  public static List<List<Pair<Integer, Integer>>> separatedRegions(String regionType, List<Pair<Integer, Integer>> regionCoords, List<List<String>> map) {
    List<List<Pair<Integer, Integer>>> sepRegions = new ArrayList<>();
    while(!(regionCoords.isEmpty())) {
      Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
      HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
      List<Pair<Integer, Integer>> subRegion = new ArrayList<>();
      queue.add(regionCoords.get(0));
      visited.add(regionCoords.get(0));
      while(!(queue.isEmpty())) {
        Pair<Integer, Integer> coords = queue.remove();
        subRegion.add(coords);
        List<Pair<Integer, Integer>> blks = perimeterBlks(coords, map);
        for(Pair<Integer, Integer> b: blks) {
          if(!(map.get(b.second()).get(b.first()).equals(regionType))) {
            continue;
          }
          if(visited.contains(b)) {
            continue;
          }
          visited.add(b);
          queue.add(b);
        }
      }
      sepRegions.add(subRegion);
      for(Pair<Integer, Integer> c: subRegion) {
        regionCoords.remove(c);
      }
    }
    return sepRegions;
  }

  public static int numCorners(String regionType, List<Pair<Integer, Integer>> regionCoords, List<List<String>> map) {
    int corners = 0;
    List<Pair<Integer, Integer>> directions = List.of(new Pair<>(0, -1), new Pair<>(1, 0), new Pair<>(0, 1), new Pair<>(-1, 0));
    for(Pair<Integer, Integer> coords: regionCoords) {
      for(int i = 0; i < directions.size(); i++) {
        Pair<Integer, Integer> dirVect = directions.get(i);
        Pair<Integer, Integer> perpendicularVect = directions.get((i + 1) % 4);
        Pair<Integer, Integer> diagVect = new Pair<>(dirVect.first() + perpendicularVect.first(), dirVect.second() + perpendicularVect.second());
        int x = coords.first();
        int y = coords.second();
        Pair<Integer, Integer> dirVectCoords = new Pair<>(x + dirVect.first(), y + dirVect.second());
        Pair<Integer, Integer> perpendicularVectCoords = new Pair<>(x + perpendicularVect.first(), y + perpendicularVect.second());
        Pair<Integer, Integer> diagVectCoords = new Pair<>(x + diagVect.first(), y + diagVect.second());

        if(isOutOfMap(dirVectCoords, map) || !(map.get(dirVectCoords.second()).get(dirVectCoords.first()).equals(regionType))) {
          if(isOutOfMap(perpendicularVectCoords, map) || !(map.get(perpendicularVectCoords.second()).get(perpendicularVectCoords.first()).equals(regionType))) {
            corners++;
            continue;
          }
        }
        if(!isOutOfMap(dirVectCoords, map) && map.get(dirVectCoords.second()).get(dirVectCoords.first()).equals(regionType)) {
          if(!isOutOfMap(perpendicularVectCoords, map) && map.get(perpendicularVectCoords.second()).get(perpendicularVectCoords.first()).equals(regionType)) {
            if(!isOutOfMap(diagVectCoords, map) && !(map.get(diagVectCoords.second()).get(diagVectCoords.first()).equals(regionType))) {
              corners++;
            }
          }
        }
      }
    }
    return corners;
  }

  public static boolean isOutOfMap(Pair<Integer, Integer> coords, List<List<String>> map) {
    return coords.first() < 0 || coords.first() > map.get(0).size() - 1 || coords.second() < 0 || coords.second() > map.size() - 1;
  }

  public static List<Pair<Integer, Integer>> perimeterBlks(Pair<Integer, Integer> coords, List<List<String>> map) {
    List<Pair<Integer, Integer>> directions = List.of(new Pair<>(0, -1), new Pair<>(0, 1), new Pair<>(-1, 0), new Pair<>(1, 0));
    List<Pair<Integer, Integer>> perimeterBlks = new ArrayList<>();
    for(Pair<Integer, Integer> d: directions) {
      int x = coords.first();
      int y = coords.second();
      x += d.first();
      y += d.second();
      if(x < 0 || x > map.get(0).size() - 1) {
        continue;
      }
      if(y < 0 || y > map.size() - 1) {
        continue;
      }
      perimeterBlks.add(new Pair<>(x, y));
    }
    return perimeterBlks;
  }
}
