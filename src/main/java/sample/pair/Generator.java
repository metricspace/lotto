package sample.pair;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Generator implements se.metricspace.lotto.CollectionGenerator {
  private java.util.Random itsRandom = null;

  public Generator() {
   this(new java.util.Random(System.currentTimeMillis()));
  }

  public Generator(java.util.Random theRandom) {
   itsRandom = (null!=theRandom) ? theRandom: new java.util.Random(System.currentTimeMillis());
  }

  @Override
  public java.util.List<se.metricspace.lotto.Row> generateRows() {
    return Generator.generateRows(itsRandom);
  }

  public static se.metricspace.lotto.Row generateRow(java.util.Random theRandom, java.util.List<se.metricspace.lotto.Pair> thePairs) {
    long start = System.currentTimeMillis();
    se.metricspace.lotto.Row row = null;
    int[] someInts = null;
    se.metricspace.lotto.Pair pair = thePairs.get(theRandom.nextInt(thePairs.size()));
    for(int i0 = 0; i0 < 31 && null == someInts ; i0++) {
      if(i0 != pair.get1st() && i0 != pair.get2nd()) {
        int tmp = (i0<5) ? 5 : i0+1;
        for(int i1 = tmp; i1 < 32 && null == someInts ; i1++) {
          if(i1 != pair.get1st() && i1 != pair.get2nd()) {
            tmp = (i1<10) ? 10 : i1+1;
            for(int i2 = tmp; i2 < 33 && null == someInts ; i2++) {
              if(i2 != pair.get1st() && i2 != pair.get2nd()) {
                tmp = (i2<15) ? 15 : i2+1;
                for(int i3 = tmp; i3 < 34 && null == someInts ; i3++) {
                  if(i3 != pair.get1st() && i3 != pair.get2nd()) {
                    tmp = (i3<20) ? 20 : i3+1;
                    for(int i4 = tmp; i4 < 35 && null == someInts ; i4++) {
                      if(i4 != pair.get1st() && i4 != pair.get2nd()) {
                        someInts = new int[7];
                        someInts[0] = pair.get1st();
                        someInts[1] = pair.get2nd();
                        someInts[2] = i0;
                        someInts[3] = i1;
                        someInts[4] = i2;
                        someInts[5] = i3;
                        someInts[6] = i4;
                        boolean ok = true;
                        for(int o=0;o<6;o++) {
                          for(int i=1+o;i<7;i++) {
                            se.metricspace.lotto.Pair p = new se.metricspace.lotto.Pair(someInts[o],someInts[i]);
                            if(!thePairs.contains(p)) {
                              ok = false;
                            }
                          }
                        }
                        if(!ok) {
                          someInts = null;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    if(null!=someInts) {
      row = new se.metricspace.lotto.Row(someInts);
    }
    return row;
  }

  public static java.util.List<se.metricspace.lotto.Row> generateRows(java.util.Random theRandom) {
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();
    java.util.List<se.metricspace.lotto.Pair> pairs = se.metricspace.lotto.Pair.initializePairs();
    se.metricspace.lotto.Row row = null;
    int counter = 0;
    do {
      row = generateRow(theRandom, pairs);
      if(null!=row) {
        counter = 0;
        rows.add(row);
        int [] items = row.getItems();
        for(int o=0; o < items.length-1; o++) {
          for(int i=1+o; i<items.length; i++) {
            se.metricspace.lotto.Pair pair = new se.metricspace.lotto.Pair(items[o],items[i]);
            pairs.remove(pair);
          }
        }
      } else {
        counter++;
      }
    } while (counter < 25);
          
    return rows;
  }
}
