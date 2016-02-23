package sample.pair;

import se.metricspace.lotto.Statistics;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Competition implements se.metricspace.lotto.Competition {
  public Statistics getWinner(Statistics the1stStatistics, Statistics the2ndStatistics) {
    return the1stStatistics.getNumberOfRows()>the2ndStatistics.getNumberOfRows() ? the1stStatistics : the2ndStatistics;
  }
}
