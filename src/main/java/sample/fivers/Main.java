package sample.fivers;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Main {
  public static void main(String[] args) {
    se.metricspace.lotto.Statistics statistics = se.metricspace.lotto.ForkJoined.execute(32, new Competition(), new Generator(4,new java.util.Random(System.currentTimeMillis())));
    java.util.List<se.metricspace.lotto.Row> rows = statistics.getRows();
    if(null!=rows && rows.size()>0) {
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
    } else {
      System.out.println("No rows");
    }
  }

  public static void saveRowsAsJson(java.util.List<se.metricspace.lotto.Row> theWows, String theFileName) {
    try {
      System.out.println("Trying to save rows as "+theFileName);
      se.metricspace.lotto.PersistJSon.save(theFileName, theWows);
    } catch (java.io.IOException exception) {
      System.out.println("IOException when saving rows as json: "+exception.getMessage());
      exception.printStackTrace(System.out);
    }
  }
}
