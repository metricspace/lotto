package sample.fivers;

import java.util.Arrays;
import static sample.fivers.Main.saveRowsAsJson;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Generator implements se.metricspace.lotto.CollectionGenerator {
  public static void main(String[] args) {
    java.util.List<se.metricspace.lotto.Row> rows = new Generator(6).generateRows();
  se.metricspace.lotto.Statistics statistics = se.metricspace.lotto.Statistics.analyseRows(rows);
    java.util.Collections.sort(rows);
    String fileName=(new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new java.util.Date(System.currentTimeMillis()));
    saveRowsAsJson(rows, fileName+".json");
    java.io.FileOutputStream targetStream = null;
    java.io.PrintWriter writer = null;
    java.io.PrintStream printStream = null;
    try {
      targetStream = new java.io.FileOutputStream(fileName+".txt");
      printStream = new java.io.PrintStream(targetStream);
      for(se.metricspace.lotto.Row row:rows) {
        printStream.println(row);
      }
      statistics.showStatistics(printStream);
      statistics.showSimilarity(printStream);
      statistics.showPairStats(printStream);
    } catch (java.io.IOException exception) {
      System.out.println("IOException writing stats: "+exception.getMessage());
      exception.printStackTrace(System.out);
    } catch (Throwable exception) {
      System.out.println("Problems ("+exception.getClass().getName()+") writing stats: "+exception.getMessage());
      exception.printStackTrace(System.out);
    } finally {
      if(null!=printStream) {
        try {
          printStream.close();
        } catch (Throwable exception) {
        }
        printStream = null;
      }
      if(null!=targetStream) {
        try {
          targetStream.close();
        } catch (Throwable exception) {
        }
        targetStream = null;
      }
    }
  }

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
      System.out.println("Counter: "+counter);
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
