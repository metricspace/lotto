package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public interface CollectionGenerator {
  public java.util.List<se.metricspace.lotto.Row> generateRows();
}
