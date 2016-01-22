package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Row implements Comparable<Row>, java.io.Serializable {
  private static final long serialVersionUID = -4113938448358637166L;
  private int[] itsItems = null;

  public Row() {
    itsItems = null;
  }

  public Row(int[] items) {
    itsItems = (null!=items && items.length>0) ? new int[items.length] : null;
    if(null!=itsItems) {
      System.arraycopy(items, 0, itsItems, 0, itsItems.length);
      java.util.Arrays.sort(itsItems);
    }
  }

  public Row(Row someRow) {
    this((null!=someRow) ? someRow.itsItems:null);
  }

  @Override 
  public int compareTo(Row someRow) {
    int cmp = 0;
    if(null!=itsItems && null!=someRow && null!=someRow.itsItems && itsItems.length ==someRow.itsItems.length) {
      int index=0;
      while(index<itsItems.length && 0==cmp) {
        cmp = itsItems[index]-someRow.itsItems[index];
        index++;
      }
    }
    return cmp;
  }
  
  @Override 
  public boolean equals(Object someObject) {
    boolean eq = false;
    if(null!=someObject && someObject instanceof Row) {
      Row someRow = (Row)someObject;
      if(null!=itsItems && null!=someRow.itsItems && itsItems.length ==someRow.itsItems.length) {
        boolean tmp = true;
        for(int index=0; index<itsItems.length; index++) {
          if(itsItems[index]!=someRow.itsItems[index]) {
            tmp = false;
          }
          eq = tmp;
        }
               
      } else {
        eq = (null==itsItems && null==someRow.itsItems);
      }
    }
    return eq;
  }

  public static Row generateRow(java.util.Random random) {
    int items[] = new int[7];
    boolean[] taken = {
      false, false, false, false, false, false, false, 
      false, false, false, false, false, false, false, 
      false, false, false, false, false, false, false, 
      false, false, false, false, false, false, false, 
      false, false, false, false, false, false, false
    };
    for(int index=0; index<items.length; index++) {
      do {
        items[index] = random.nextInt(35);
      } while(taken[items[index]]);
      taken[items[index]] = true;
    }
    return new Row(items);
  }

  public int[] getItems() {
    return itsItems;
  }

  public int getSimilarity(Row someRow) {
    int sim = 0;
    if(null!=itsItems && null!=someRow && null!=someRow.itsItems && itsItems.length ==someRow.itsItems.length) {
      for(int outer=0; outer <itsItems.length; outer++) {
        for(int inner=0; inner <itsItems.length; inner++) {
          if(itsItems[outer]==someRow.itsItems[inner]) {
            sim++;
          }
        }
      }
    }
    return sim;
  }

  @Override 
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if(null!=itsItems && 7 == itsItems.length) {
      builder.append("LottoRad : [ ");
      for(int index = 0; index<itsItems.length; index++ ) {
        if(index>0) {
          builder.append(", ");
        }
        if(itsItems[index]<9) {
          builder.append(' ');
        }
        builder.append(1+itsItems[index]);
      }
      builder.append(" ]");
    } else {
      builder.append("Lottorad inte initierad!");
    }
    return builder.toString();
  }
}
