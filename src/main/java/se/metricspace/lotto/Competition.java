package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public interface Competition {
  public Statistics getWinner(Statistics the1stStatistics, Statistics the2ndStatistics);
}
