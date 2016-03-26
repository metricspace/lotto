package sample.pair;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Generator implements se.metricspace.lotto.CollectionGenerator {
  private static final long TIMEOUT = 1000000L; // 1000 sek, 16 m 40s
  private java.util.Random itsRandom = null;
  private int itsSize = 0;

  public Generator() {
   this(1);
  }

  public Generator(int theSize) {
   this((theSize>0) ? theSize : 0, new java.util.Random(System.currentTimeMillis()));
  }

  public Generator(int theSize, java.util.Random theRandom) {
   itsRandom = (null!=theRandom) ? theRandom: new java.util.Random(System.currentTimeMillis());
   itsSize = (theSize>0) ? theSize : 1;
  }

  @Override
  public java.util.List<se.metricspace.lotto.Row> generateRows() {
    return generateRows(itsRandom);
  }

  public static se.metricspace.lotto.Row generateRow(java.util.Random theRandom, java.util.List<se.metricspace.lotto.Pair> thePairs) {
    boolean free[] = { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
    se.metricspace.lotto.Pair pair = thePairs.get(theRandom.nextInt(thePairs.size()));
    int[] someInts = { pair.get1st(), pair.get2nd(), 0, 0, 0, 0, 0};
    free[pair.get1st()] = false;
    free[pair.get2nd()] = false;
    for(int index = 2; index < someInts.length; index++) {
      do {
        someInts[index] = theRandom.nextInt(35);
      } while (!free[someInts[index]]);
      free[someInts[index]] = false;
    }
    return new se.metricspace.lotto.Row(someInts);
  }

  public java.util.List<se.metricspace.lotto.Row> generateRows(java.util.Random theRandom) {
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();
    java.util.List<se.metricspace.lotto.Pair> pairs = se.metricspace.lotto.Pair.initializePairs();
    se.metricspace.lotto.Row row = null;
    long start = System.currentTimeMillis();
    for(int counter = 0; counter < this.itsSize; counter++) {
      System.out.println(counter);
      int maxSim = 0;
      int uniquePairs = 0;
      do {
        row = generateRow(theRandom, pairs);
        maxSim = 0;
        for(int index = 0; index < rows.size(); index++) {
          int sim = row.getSimilarity(rows.get(index));
          maxSim = Math.max(sim, maxSim);
        }
        int [] someInts = row.getItems();
        uniquePairs = 0;
        for(int i = 0; i < 6; i++) {
          for(int j = i+1; j < 7; j++) {
            if(pairs.contains(new se.metricspace.lotto.Pair(someInts[i], someInts[j]))) {
              uniquePairs++;
            }
          }
        }
      } while ((maxSim>2 || uniquePairs<18) && (System.currentTimeMillis()-start<TIMEOUT));
      if(System.currentTimeMillis()-start<TIMEOUT)  {
        rows.add(row);
        int [] someInts = row.getItems();
        for(int i = 0; i < 6; i++) {
          for(int j = i+1; j < 7; j++) {
            se.metricspace.lotto.Pair pair = new se.metricspace.lotto.Pair(someInts[i], someInts[j]);
            pairs.remove(pair);
          }
        }
      } else {
        break;
      }
    }
          
    return rows;
  }
}
