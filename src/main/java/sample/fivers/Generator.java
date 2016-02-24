package sample.fivers;

import java.util.Arrays;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Generator implements se.metricspace.lotto.CollectionGenerator {
  private java.util.Random itsRandom = null;
  private int itsNumberOfQuintets = 0;

  public Generator() {
    this(4);
  }

  public Generator(int theNumberOfQuintets) {
    this(theNumberOfQuintets, new java.util.Random(System.currentTimeMillis()));
  }

  public Generator(int theNumberOfQuintets, java.util.Random theRandom) {
    itsNumberOfQuintets = (theNumberOfQuintets>0) ? theNumberOfQuintets : 0;
    itsRandom = (null!=theRandom) ? theRandom: new java.util.Random(System.currentTimeMillis());
  }

  @Override
  public java.util.List<se.metricspace.lotto.Row> generateRows() {
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();

    for(int counter=0; counter < itsNumberOfQuintets; counter ++) {
      boolean toSimilar = false;
      se.metricspace.lotto.Row[] tmpRows = new se.metricspace.lotto.Row[5];
      do {
        boolean [] free = { true, true, true, true, true, true, true, 
                            true, true, true, true, true, true, true, 
                            true, true, true, true, true, true, true, 
                            true, true, true, true, true, true, true, 
                            true, true, true, true, true, true, true};
        for(int index = 0; index< tmpRows.length; index++) {
          int[] numbers = new int[7];
          for(int i = 0; i < numbers.length; i++) {
            int tmp = -1;
            do {
              tmp = itsRandom.nextInt(35);
            } while (!free[tmp]);
            free[tmp] = false;
            numbers[i] = tmp;
          }
          tmpRows[index] = new se.metricspace.lotto.Row(numbers);
        }
        int max = -1 , tmp;
        for(int o =0; o < rows.size(); o++) {
          for(int i = 0; i < 5 ; i++) {
            tmp = rows.get(o).getSimilarity(tmpRows[i]);
            if(tmp>max) max = tmp;
          }
        }
        toSimilar = (max>2);
      } while(toSimilar);
      rows.addAll(Arrays.asList(tmpRows));
    }
    java.util.Collections.sort(rows);
    return rows;
  }
}
