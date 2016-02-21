package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// Spela inte för mer än du har råd att förlora!
public class Statistics {
  private double itsCountAverage = 0.0;
  private int itsCountMax = 0;
  private int itsCountMin = 0;
  private double itsCountStdDev = 0.0;
  private int itsNumberOfRows = 0;
  private double itsPairAverage = 0.0;
  private int itsPairMax = 0;
  private int itsPairMin = 0;
  private int itsPairUniqueCount = 0;
  private int[] itsSimilarity = null;
  private double itsSimilarityAverage = 0.0;
  private int itsSimilarityMax = 0;
  private int itsSimilarityMin = 0;

  public Statistics() {
    itsCountAverage = 0.0;
    itsCountMax = 0;
    itsCountMin = 0;
    itsCountStdDev = 0.0;
    itsNumberOfRows = 0;
    itsPairAverage = 0.0;
    itsPairMax = 0;
    itsPairMin = 0;
    itsPairUniqueCount = 0;
    itsSimilarity = null;
    itsSimilarityAverage = 0.0;
    itsSimilarityMax = 0;
    itsSimilarityMin = 0;
  }

  public static Statistics analyseRows(java.util.List<Row> rows) {
    Statistics statistics = new Statistics();
    
    if(null!=rows && rows.size()>0) {
      statistics.itsNumberOfRows = rows.size();

      // Some basics
      int count[] = {
        0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0};
      rows.stream().map((Row row) -> row.getItems()).forEach((int[] items) -> {
        for(int item:items) {
          count[item]++;
        }
      });
      statistics.itsCountMax = -1;
      statistics.itsCountMin = 99999999;
      statistics.itsCountAverage = rows.size() / 5.0; // rows.size() * 7 / 35
      double variance = 0.0;
      for(int index=0; index<count.length; index++) {
        if(count[index]>statistics.itsCountMax) {
          statistics.itsCountMax = count[index];
        }
        if(count[index]<statistics.itsCountMin) {
          statistics.itsCountMin = count[index];
        }
        variance += (count[index]-statistics.itsCountAverage) * (count[index]-statistics.itsCountAverage);
      }
      variance /= count.length;

      statistics.itsCountStdDev = Math.sqrt(variance);

      // Some similarity-stats
      int similarity[] = {0, 0, 0, 0, 0, 0, 0, 0};
      statistics.itsSimilarityMax = -1;
      statistics.itsSimilarityMin = 9;
      statistics.itsSimilarityAverage = 0.0;
      for(int outer = 0; outer < rows.size()-1; outer++) {
        for(int inner = 1+outer; inner < rows.size(); inner++) {
          int sim = rows.get(inner).getSimilarity(rows.get(outer));
          similarity[sim]++;
          statistics.itsSimilarityAverage += sim;
          if(sim>statistics.itsSimilarityMax) {
            statistics.itsSimilarityMax = sim;
          }
          if(sim<statistics.itsSimilarityMin) {
            statistics.itsSimilarityMin = sim;
          }
        }
      }
      statistics.itsSimilarityAverage /= (rows.size()*(rows.size()-1)/2);
      statistics.itsSimilarity = similarity;
      
      // Some pair statistics
      java.util.Map<Pair,Integer> pairCount = new java.util.HashMap<>(595);
      for(int i = 0; i < 34 ; i++) {
        for(int j = i+1; j < 35; j++) {
          pairCount.put(new Pair(i,j), 0);
        }
      }
      for(Row row:rows) {
        int [] items = row.getItems();
        for(int i = 0; i < 6; i++) {
          for(int j = 1+i; j < 7; j++) {
            Pair pair = new Pair(items[i],items[j]);
            Integer pcount = 1+pairCount.getOrDefault(pair, 0);
            pairCount.replace(pair, pcount);
          }
        }
      }
      statistics.itsPairMax = -1;
      statistics.itsPairMin = 999999;
      statistics.itsPairAverage = 0.0;
      statistics.itsPairUniqueCount = 0;

      for(int i = 0; i < 34 ; i++) {
        for(int j = i+1; j < 35; j++) {
          Integer pcount = pairCount.getOrDefault(new Pair(i,j), 0);
          if(pcount>statistics.itsPairMax) {
            statistics.itsPairMax = pcount;
          }
          if(pcount<statistics.itsPairMin) {
            statistics.itsPairMin = pcount;
          }
          statistics.itsPairAverage += pcount;
        }
      }
      statistics.itsPairAverage /= 595.0;
      pairCount.keySet().stream().filter((pair) -> (pairCount.getOrDefault(pair,0)>0)).forEach((_item) -> {
        statistics.itsPairUniqueCount ++;
      });
    }

    return statistics;
  }

  public double getCountAverage() {
    return itsCountAverage;
  }

  public int getCountMax() {
    return itsCountMax;
  }

  public int getCountMin() {
    return itsCountMin;
  }

  public double getCountStandardDeviation() {
    return this.itsCountStdDev;
  }
  
  public int getNumberOfRows() {
    return itsNumberOfRows;
  }

  public double getPairAverage() {
    return this.itsPairAverage;
  }

  public int getPairMax() {
    return itsPairMax;
  }

  public int getPairMin() {
    return itsPairMin;
  }

  public int getPairUniqueCount() {
    return itsPairUniqueCount;
  }

  public int[] getSimilarity() {
    return itsSimilarity;
  }

  public double getSimilarityAverage() {
    return itsSimilarityAverage;
  }

  public int getSimilarityMax() {
    return itsSimilarityMax;
  }

  public int getSimilarityMin() {
    return itsSimilarityMin;
  }

  public static void showPairStats(java.util.List<Row> rows, java.io.PrintStream theWriter) {
    java.util.Map<Pair,Integer> pairCount = new java.util.HashMap<>(595);
    for(int i = 0; i < 34 ; i++) {
      for(int j = i+1; j < 35; j++) {
        pairCount.put(new Pair(i,j), 0);
      }
    }
    for(Row row:rows) {
      int [] items = row.getItems();
      for(int i = 0; i < 6; i++) {
        for(int j = 1+i; j < 7; j++) {
          Pair pair = new Pair(items[i],items[j]);
          Integer count = 1+pairCount.getOrDefault(pair, 0);
          pairCount.replace(pair, count);
        }
      }
    }
    int max = -1;
    int min = 999999;
    double average = 0.0;
    for(int i = 0; i < 34 ; i++) {
      for(int j = i+1; j < 35; j++) {
        Integer count = pairCount.getOrDefault(new Pair(i,j), 0);
        if(count>max) {
          max = count;
        }
        if(count<min) {
          min = count;
        }
        average += count;
      }
    }
    average /= 595.0;
    theWriter.println("Pairs");
    theWriter.println("Average: "+average);
    theWriter.println("Min-Max: ["+min+","+max+"]");
  }

  public void showPairStats(java.io.PrintStream theWriter) {
    theWriter.println("Pairs");
    theWriter.println("Average: "+itsPairAverage);
    theWriter.println("Min-Max: ["+itsPairMin+","+itsPairMax+"]");
    theWriter.println("Unique Count: "+this.itsPairUniqueCount);
  }

  public static void showSimilarity(java.util.List<Row> rows, java.io.PrintStream theWriter) {
    int similarity[] = {0, 0, 0, 0, 0, 0, 0, 0};
    int max = -1;
    int min = 9;
    double average = 0.0;
    for(int outer = 0; outer < rows.size()-1; outer++) {
      for(int inner = 1+outer; inner < rows.size(); inner++) {
        int sim = rows.get(inner).getSimilarity(rows.get(outer));
        similarity[sim]++;
        average += sim;
        if(sim>max) {
          max = sim;
        }
        if(sim<min) {
          min = sim;
        }
      }
    }
    average /= (rows.size()*(rows.size()-1)/2);
    theWriter.println("Similarity");
    theWriter.println("Average: "+average);
    theWriter.println("Min-Max: ["+min+","+max+"]");
    theWriter.println("Distribution: ["+similarity[0]+","+similarity[1]+","+similarity[2]+","+similarity[3]+","+similarity[4]+","+similarity[5]+","+similarity[6]+","+similarity[7]+"]");
  }

  public void showSimilarity(java.io.PrintStream theWriter) {
    theWriter.println("Similarity");
    theWriter.println("Average: "+itsSimilarityAverage);
    theWriter.println("Min-Max: ["+itsSimilarityMin+","+itsSimilarityMax+"]");
    theWriter.println("Distribution: ["+itsSimilarity[0]+","+itsSimilarity[1]+","+itsSimilarity[2]+","+itsSimilarity[3]+","+itsSimilarity[4]+","+itsSimilarity[5]+","+itsSimilarity[6]+","+itsSimilarity[7]+"]");
  }

  public static void showStatistics(java.util.List<Row> rows, java.io.PrintStream theWriter) {
    int count[] = {
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0};
    rows.stream().map((Row row) -> row.getItems()).forEach((int[] items) -> {
      for(int item:items) {
        count[item]++;
      }
    });
    int max = -1;
    int min = 99999999;
    double average = rows.size() / 5.0; // rows.size() * 7 / 35
    double variance = 0.0;
    for(int index=0; index<count.length; index++) {
      if(count[index]>max) {
        max = count[index];
      }
      if(count[index]<min) {
        min = count[index];
      }
      variance += (count[index]-average) * (count[index]-average);
    }
    variance /= count.length;
    
    theWriter.println("Distribution");
    theWriter.println("Average: "+average);
    theWriter.println("Stddev: "+Math.sqrt(variance));
    theWriter.println("Min-Max: ["+min+","+max+"]");
  }

  public void showStatistics(java.io.PrintStream theWriter) {
    theWriter.println("Distribution");
    theWriter.println("Average: "+itsCountAverage);
    theWriter.println("Stddev: "+itsCountStdDev);
    theWriter.println("Min-Max: ["+itsCountMin+","+itsCountMax+"]");
  }

  @Override
  public String toString() {
    return "Number of Rows: "+itsNumberOfRows+", Count [Min, Max, Avg]: ["+this.itsCountMin+", "+this.itsCountMax+", "+this.itsCountAverage+"], Max Similarity: "+this.itsSimilarityMax+", Unique Pair: "+this.itsPairUniqueCount;
  }
}
