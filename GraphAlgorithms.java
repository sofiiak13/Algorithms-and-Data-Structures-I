import java.awt.Color;
import java.util.*;


public class GraphAlgorithms{

  /* 
   * To draw a list of integers int_list (of type List<Integer)
   * to the canvas, call drawSequence(int_list, writer).
   *
   * The index of each integer in the list will be
   * plotted along the x-axis; the integer value itself
   * is plotted on the y-axis.
   *                                                      */

  public static List<Integer> MergeSort(List<Integer> S, PixelWriter writer) {
    if (S.size() < 2) {
      return S;
    }


    List<List<Integer>> dividedList = divide(S);
    List<Integer> s1 = MergeSort(dividedList.get(0), writer);
    List<Integer> s2 = MergeSort(dividedList.get(1), writer);
    List<Integer> sortedList = merge(s1, s2);
  
    drawSequence(sortedList, writer);

    return sortedList;
  }

  public static List<List<Integer>> divide(List<Integer> S){
    List<Integer> s1 = new ArrayList<Integer>();
    List<Integer> s2 = new ArrayList<Integer>();

    // s1 = S.subList(0,(Math.ceil(S.size()/2.0)-1));
    // s2 = S.subList((Math.ceil(S.size()/2.0),S.size()-1);

    List<List<Integer>> result = new ArrayList<List<Integer>>(2);

    for (int i = 0; i < S.size()/2; i++) {
      s1.add(S.get(i));
    }


    for (int i = S.size()/2; i < S.size(); i++) {
      s2.add(S.get(i));
    }

    result.add(s1);
    result.add(s2);

    return result;

  }

  public static List<Integer> merge(List<Integer> s1, List<Integer> s2) {
    
    List<Integer> S = new ArrayList<>();

    int i = 0;
    int j = 0;
    int k = 0;

    int n1 = s1.size();
    int n2 = s2.size();

    while (i < n1 && j < n2){
      if (s1.get(i) <= s2.get(j)){
        S.add(k, s1.get(i));
        i++; 
        k++;
      } else {
       S.add(k, s2.get(j));
        j++;
        k++;
      }
    }

    while (i < n1) {
      S.add(k, s1.get(i));
      i++;
      k++;
    }
    while (j < n2) {
      S.add(k, s2.get(j));
      j++;
      k++;
    }

    return S;
  }


  public static List<Integer> QuickSort(List<Integer> S, PixelWriter writer) {

    if (S.size() < 2) {
      return S;
    }

    Integer pivot = pickPivot(S);
    List<List<Integer>> splittedList = split(S, pivot);
    List<Integer> less = QuickSort(splittedList.get(0), writer);
    List<Integer> equal = splittedList.get(1);
    List<Integer> greater = QuickSort(splittedList.get(2), writer);
    List<Integer> sortedList = concatenate(less, equal, greater);

    drawSequence(sortedList, writer);
    return sortedList;
  }

  public static Integer pickPivot(List<Integer> S) {
    int n = S.size();
    return S.get(n/2);
  }

  public static List<List<Integer>> split(List<Integer> S, Integer pivot) {
    List<Integer> less = new ArrayList<>();
    List<Integer> equal = new ArrayList<>();
    List<Integer> greater = new ArrayList<>();
    List<List<Integer>> result = new ArrayList<>(3);

    for (int i = 0; i < S.size(); i++) {
      if (S.get(i) < pivot){
        less.add(S.get(i));
      } else if (S.get(i) > pivot) {
        greater.add(S.get(i));
      } else {
        equal.add(S.get(i));
      }
    }

    result.add(less);
    result.add(equal);
    result.add(greater);

    return result;
  }

  public static List<Integer> concatenate(List<Integer> less, List<Integer> equal, List<Integer> greater) {
    List<Integer> result = new ArrayList<>();

    result.addAll(less);
    result.addAll(equal);
    result.addAll(greater);

    return result;
  }


  public static List<Integer> InsertionSort(List<Integer> S, PixelWriter writer) {
    int n = S.size();

    for (int i = 1; i < n; i++){
      Integer val = S.get(i);
      int j = i-1;
      while (j >= 0 && S.get(j) > val) {
        S.set(j+1, S.get(j));
        j = j-1;
      }

      S.set(j+1, val);
    }

    drawSequence(S, writer);
    return S;
  }

  public static List<Integer> RadixSort(List<Integer> S, PixelWriter writer) {
    

    // we know that the max number of digits is 4 so
    List<Integer> sorted =  new ArrayList<>();
    sorted.addAll(S);

    for (int loop = 3; loop >= 0; loop--) {
      sorted = BucketSort(sorted, loop);
      drawSequence(sorted, writer);
    }

    return sorted;
  }

  
  public static List<Integer> BucketSort(List<Integer> S, int loop) {
    // since our values are only Integers we will create 10 buckets (0-9)
    List<List<Integer>> buckets = new ArrayList<>(10);

    for (int i = 0; i < 10; i++) {
      List<Integer> curBucket = new ArrayList<>();
      buckets.add(curBucket);
    }


    for (int i = 0; i < S.size(); i++) {
      Integer val = S.get(i);
      int k = key(val, loop);
      List<Integer> curBucket = buckets.get(k);
      curBucket.add(val);
    }

    List<Integer> sorted = new ArrayList<Integer>();

    for (int i = 0; i < 10; i++) {
      List<Integer> curBucket = buckets.get(i);
      Iterator<Integer> iter = curBucket.iterator();
      while (iter.hasNext()) {
        sorted.add(curBucket.remove(0));
      }
    }

    return sorted;

  }
 
  public static int key(Integer x, int i) {
   
    int depth = 4;
    String str = String.format("%0"+ depth + "d", x);
    char key = str.charAt(i);
    return Character.getNumericValue(key);
  }



  /* DO NOT CHANGE THIS METHOD */
  private static void drawSequence(List<Integer> sequence, PixelWriter writer) {
    for (Integer curr : sequence) {
      for (int j=0; j<sequence.size(); j++) {
        Color c = writer.getColor(j, curr);
        if (c.equals(Color.BLACK))
          writer.setPixel(j, curr, Color.WHITE);
      }
      int x = sequence.indexOf(curr);
      if (!writer.getColor(x, curr).equals(Color.BLACK)) {
        writer.setPixel(sequence.indexOf(curr), curr, Color.BLACK);
      }
    }
  } 


  /* THE FOLLOWING METHODS WILL NOT BE MARKED;
   * YOU MAY IMPLEMENT THEM OPTIONALLY
   */

	/* FloodFillDFS(v, writer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
	}
	
	/* FloodFillBFS(v, writer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
	}
	
}
