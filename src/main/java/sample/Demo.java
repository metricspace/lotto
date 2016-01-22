package sample;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Demo {
  public static void main(String[] args) {
    java.util.Random random = new java.util.Random(System.currentTimeMillis());
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();
    for(int i = 0; i < 100; i++) {
      rows.add(se.metricspace.lotto.Row.generateRow(random));
    }
    java.util.Collections.sort(rows);
    rows.stream().forEach((se.metricspace.lotto.Row row) -> {
      System.out.println(row);
    });
    System.out.println();
    se.metricspace.lotto.Statistics.showStatistics(rows);
    System.out.println();
    se.metricspace.lotto.Statistics.showSimilarity(rows);
    System.out.println();
    se.metricspace.lotto.Statistics.showPairStats(rows);
  }
}
