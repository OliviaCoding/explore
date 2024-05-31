package StockProject;
//Tester will receive a file and a path as well as a riskFactor or how many days until exit
//Have a method run() that will do the following:
//1- Create a Vector of all symbols from the input file, if not done yet
//

import java.io.IOException;
import java.util.Vector;

import static StockProject.Statistics.computeStats;

public class Tester {
    private Vector<String> vSymbols;
    private Vector<Trade> mTrades;
    private String mPath, mFile;
    private int mRisk;
    public Tester(String path, String file, int riskFactor) throws IOException {
        //set path and file
        this.mPath = path;
        this.mFile = file;
        this.mRisk = riskFactor;
        //open the file and create a Vector of symbols
        this.vSymbols = new Vector<String>();
        vSymbols = Main.loadSymbols(path,file);
        //create a vector of Trades
        this.mTrades = new Vector<Trade>();
    }
//    public void setRisk(float r){
//        mRisk = r;
//    }
//    public void setPath(String p){
//        mPath = p;
//    }
//    public void setFile(String f){
//    }
    public boolean run(){
        //if mSymbols is empty create a new one
        if(this.vSymbols.isEmpty()){
            this.vSymbols = new Vector<String>();
        }
        //if mTrades is empty or null
        if(this.mTrades.isEmpty() || this.mTrades == null){
            this.mTrades = new Vector<Trade>();
        }
        //go to the vSymbols, for each symbol,
        for(String symbols : vSymbols){
            //create a symbol Tester with appropriate parameters
            SymbolTester t = new SymbolTester(symbols, this.mPath, this.mRisk);
            //Test the symbol (calling .test() method)
            t.test();
            //collect the trades by calling getTrades() method of the Symbol
            mTrades.addAll(t.getTrades());
        }
        Statistics s = Statistics.computeStats(this.mTrades);
        return true;
    }

    public Vector<Trade> getTrades(){
        return mTrades;
    }

    public void reset(){
        vSymbols = null;
        mTrades = null;
    }
}
