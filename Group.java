import java.util.ArrayList;
import java.util.*;

public class Group {

  private ArrayList<Frog> frogs;
  private int curSize;

  public Group(){
    frogs = new ArrayList<Frog>();
    curSize = 0;
  }
  
  public void addFrog(Frog f) {
    if (curSize == 0) {
      frogs.add(0, f);
    } else {
      int index = findSpot(0, curSize-1, f);
      frogs.add(index, f);
    }
    
    curSize++;
  }

  public int findSpot(int low, int high, Frog toInsert) {


    if (low > high) {
      return low;
    } else {
    int mid = (low+high)/2;
    if (frogs.get(mid).compareTo(toInsert) == 0) {
      return mid;
    } else if (toInsert.compareTo(frogs.get(mid)) < 0) {
      return findSpot(low, mid-1, toInsert);
    } else {
      return findSpot(mid+1, high, toInsert);
    }
    
  }
  }

  public int size() {
    return curSize;
  }

  public Frog get(int i) {
    return frogs.get(i);
  }

  public Group[] halfGroups() {

    Group g1 = new Group();
    Group g2 = new Group();

    for (int i = 0; i <= (curSize/2)-1; i++){
      g1.addFrog(frogs.get(i));
      g2.addFrog(frogs.get(i+(curSize/2)));
    }
    if ((curSize % 2) == 1) {
      g2.addFrog(frogs.get(curSize-1));
    }
    
    Group[] result = {g1, g2};

    return result;
  }

  @Override
  public String toString() {

    Frog[] frogsArray = new Frog[frogs.size()];
    frogs.toArray(frogsArray);

    return Arrays.toString(frogsArray);
  }

  public static boolean FrogEquals(Group g1, Group g2) {

    if ( g1.toString().equals(g2.toString())) {
      return true;
    }

    if (g1.size() != g2.size()) {
      return false;
    }
    
    
    if (g1.size() == 1 || g2.size() == 1) {
      return g1.get(0).equals(g2.get(0));
    } else {
            Group g1Top = g1.halfGroups()[0];
            Group g1Bottom = g1.halfGroups()[1];
            Group g2Top = g2.halfGroups()[0];
            Group g2Bottom = g2.halfGroups()[1];
            return FrogEquals(g1Top, g2Bottom) || FrogEquals(g2Top, g1Bottom);
    }

  }
}
