package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Pair implements Comparable<Pair> {
  private int its1st = 0;
  private int its2nd = 1;

  public Pair() {
    this(0, 1);
  }

  public Pair(int the1st, int the2nd) {
    if(Math.min(the1st, the2nd)<0) {
      throw new IllegalArgumentException("The Lowest allowed value in Pair is 0, but got "+Math.min(the1st, the2nd));
    }
    if(Math.max(the1st, the2nd)>34) {
      throw new IllegalArgumentException("The Highest allowed value in Pair is 34, but got "+Math.max(the1st, the2nd));
    }
    if(the1st==the2nd) {
      throw new IllegalArgumentException("The values in Pair must be unique but got two "+the1st);
    }
    its1st = Math.min(the1st, the2nd);
    its2nd = Math.max(the1st, the2nd);
  }

  @Override
  public int compareTo(Pair somePair) {
    return (its1st==somePair.its1st) ? (its2nd-somePair.its2nd):(its1st-somePair.its1st);
  }

  @Override
  public boolean equals(Object someObject) {
    boolean eq = false;
    if(null!=someObject && someObject instanceof Pair) {
      eq = hashCode() == someObject.hashCode();
    }
    return eq;
  }

  public int get1st() {
    return its1st;
  }

  public int get2nd() {
    return its2nd;
  }

  @Override
  public int hashCode() {
    return its1st + its2nd * 35; 
  }

  @Override
  public String toString() {
    return "[ Pair: ("+its1st+","+its2nd+") ]";
  }
}
