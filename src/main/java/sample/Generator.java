package sample;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Generator implements se.metricspace.lotto.CollectionGenerator {
  private int itsNumberOfRows = -1;
  private java.util.Random itsRandom = null;
  
  public Generator() {
    this(20);
  }

  public Generator(int theNumberOfRows) {
    this(theNumberOfRows, new java.util.Random(System.currentTimeMillis()));
  }

  public Generator(int theNumberOfRows, java.util.Random theRandom) {
    itsNumberOfRows = (theNumberOfRows>0) ? theNumberOfRows : 20;
    itsRandom = (null!=theRandom) ? theRandom : new java.util.Random(System.currentTimeMillis());
  }

  @Override
  public java.util.List<se.metricspace.lotto.Row> generateRows() {
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();
    for(int counter = 0; counter < itsNumberOfRows; counter++) {
      int max = -1;
      se.metricspace.lotto.Row row = null;
      do {
        max = -1;
        row = se.metricspace.lotto.Row.generateRow(itsRandom);
        for(int i = 0; i < counter ; i++) {
          int tmp = row.getSimilarity(rows.get(i));
          if(tmp>max) max = tmp;
        }
      } while (max>2);
      rows.add(row);
    }
    return rows;
  }
}
