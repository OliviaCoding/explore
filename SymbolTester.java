package StockProject;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SymbolTester {
    private int riskFactor;//TODO: change this to your
    private String mSymbol;
    private String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";

    private Vector<Bar> mData;
    private Vector<Trade> mTrades;
    private boolean loaded = false;

    public SymbolTester(String s, String p, int risk) {
        riskFactor = risk;
        mSymbol = s;
        dataPath = p;
        mData = new Vector<Bar>(3000, 100);
        mTrades = new Vector<Trade>(200, 100);
        loaded = false;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }
    public void loadData() {
        //create file name
        String fileName = dataPath + mSymbol + "_Daily.csv";
        //"C:\Users\oaith\Courses\MAC286\Fall2023\Data\AAPL_Daily.csv"
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null) {
                //create a bar using the constructor that accepts the data as a String
                Bar b = new Bar(line);
                //add the bar to the Vector
                mData.add(b);
            }
            loaded = true;
            br.close();
            fr.close();
        }catch(IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
            return;
        }
    }

    private boolean xDaysLow(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getLow() < mData.elementAt(ind).getLow())
                return false;
        }
        return true;
    }
    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getHigh() > mData.elementAt(ind).getHigh())
                return false;
        }
        return true;
    }
//    void outcomes(Trade T, int ind) {
//        for(int i = ind; i < mData.size(); i++) {
//            if(T.getDir() == Direction.LONG) {
//                if(mData.elementAt(i).getHigh() > T.getTarget()) { //it is a win
//                    //consider a gap day
//                    if(mData.elementAt(i).getOpen() > T.getTarget()) {
//                        //close at open  a gap day
//                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
//                        return;
//                    }else {
//                        //close the trade at target
//                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
//                        return;
//                    }
//                } else if(mData.elementAt(i).getLow() < T.getStopLoss()) {
//                    //check if there is a gap down
//                    if(mData.elementAt(i).getOpen() < T.getStopLoss()) {
//                        //get out at the open
//                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
//                        return;
//                    }else {
//                        //get out at stoploss
//                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
//                        return;
//                    }
//
//                }
//            }else {// it is a short trade
//                if(mData.elementAt(i).getLow() <= T.getTarget()) { //it is a win
//                    //consider a gap day
//                    if(mData.elementAt(i).getOpen() < T.getTarget()) {
//                        //close at open  a gap down day
//                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
//                        return;
//                    }else {
//                        //close the trade at target
//                        T.close(mData.elementAt(i).getDate(), T.getTarget(), i-ind);
//                        return;
//                    }
//                } else if(mData.elementAt(i).getHigh() >= T.getStopLoss()) {
//                    //check if there is a gap down
//                    if(mData.elementAt(i).getOpen() > T.getStopLoss()) {
//                        //get out at the open
//                        T.close(mData.elementAt(i).getDate(), mData.elementAt(i).getOpen(), i-ind);
//                        return;
//                    }else {
//                        //get out at stoploss
//                        T.close(mData.elementAt(i).getDate(), T.getStopLoss(), i-ind);
//                        return;
//                    }
//
//                }
//
//            }
//        }//end of for
//        //if we get here the trade is not closed, close it at the close of the last day
//        T.close(mData.elementAt(mData.size()-1).getDate(), mData.elementAt(mData.size()-1).getClose(), mData.size()-1-ind);
//    }

    public boolean test() {
        if(!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("cannot load data");
                return false;
            }
        }
        //display the first 120 bars
        /*As an example let's test the following pattern
         * 1- today makes a 10 days low
         * 2- today is an outside bar (reversal) today's low is smaller than yesterday's low and today's high is larger than yesterday's high.
         * 3- today's close near the high (within less than 10%) (high-close)/(high-low)<0.1;
         * 4- buy at open tomorrow and stop today's low and target factor*risk
         */


        for(int i = 20; i <mData.size()-11; i++) {
            //i makes 20 days lower closer near low of the day. Open lower than low of i-1
            //Open lower than low of i-1
            //low of i is lower than low of i-1;
            //low of i-1 is lower than low of i-2;
            //low of i-2 is lower than low of i-3;
            //low of i-3 is lower than low of i-4;
            //i-1, i-2, i-3 all Red (close lower than previous day)
            //Buy the next day (open of i+1)
            if(xDaysLow(i, 20)
                    && mData.elementAt(i).getOpen() < mData.elementAt(i-1).getLow()
//                    && mData.elementAt(i-1).getLow() < mData.elementAt(i-2).getLow()
//                    && mData.elementAt(i-2).getLow() < mData.elementAt(i-3).getLow()
//                    && mData.elementAt(i-3).getLow() < mData.elementAt(i-4).getLow()
                    && mData.elementAt(i-1).getClose() < mData.elementAt(i-2).getClose()
                    && mData.elementAt(i-2).getClose() < mData.elementAt(i-3).getClose()
                    && mData.elementAt(i-3).getClose() < mData.elementAt(i-4).getClose()
                    && (mData.elementAt(i).getClose() - mData.elementAt(i).getLow())/(mData.elementAt(i).getHigh() - mData.elementAt(i).getLow()) < 0.1)

            {
                //we have a trade, buy at opne of i+1 (tomorrow) stoploss i.low, target = entry+factor*risk
                float entryPrice = mData.elementAt(i+1).getOpen();
                float exitPrice = mData.elementAt(i+1+riskFactor).getClose();
                //float stoploss = mData.elementAt(i).getLow() - 0.01f;
                //float risk = entryprice - stoploss;
                //float target = entryprice + riskFactor * risk;
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryPrice, 0, 0, Direction.LONG);
                T.close(mData.elementAt(i+1+riskFactor).getDate(), exitPrice, riskFactor);
                //outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);

                //Short for reverse trade change low to high, high to low larger to smaller and smaller to larger

                //1- i (today) makes a 20 day high
                //open of i is higher than high of i-1
                //2- i-1, i-2, and i-3 all green bars (close higher than close of previous day)
                //3- sell the next day <- (i+1) day0 (short trade)
                //Close near the high (within 10% of the range of the day near high)
            }else if(xDaysHigh(i, 20)
                    && mData.elementAt(i).getOpen() > mData.elementAt(i-1).getHigh()
//                    && mData.elementAt(i-1).getHigh() > mData.elementAt(i-2).getHigh()
//                    && mData.elementAt(i-2).getHigh() > mData.elementAt(i-3).getHigh()
//                    && mData.elementAt(i-3).getHigh() > mData.elementAt(i-4).getHigh()
                    && mData.elementAt(i-1).getClose() > mData.elementAt(i-2).getClose()
                    && mData.elementAt(i-2).getClose() > mData.elementAt(i-3).getClose()
                    && mData.elementAt(i-3).getClose() > mData.elementAt(i-4).getClose()
                    && (mData.elementAt(i).getHigh() - mData.elementAt(i).getClose())/(mData.elementAt(i).getHigh() - mData.elementAt(i).getLow()) < 0.1)
            {
                //we have a trade, buy at opne of i+1 (tomorrow) stoploss i.low, target = entry+factor*risk
                float entryPrice = mData.elementAt(i+1).getOpen();
                float exitPrice = mData.elementAt(i+1+riskFactor).getClose();
                //float stoploss = mData.elementAt(i).getHigh() + 0.01f;
                //float risk = stoploss - entryprice;
                //float target = entryprice - riskFactor * risk;
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i+1).getDate(), entryPrice, 0, 0, Direction.SHORT);
                T.close(mData.elementAt(i+1+riskFactor).getDate(), exitPrice, (int) riskFactor);
                //outcomes(T, i+1);
                //add the trade to the Trade vector
                mTrades.add(T);
            }
        }

        return true;
    }

}