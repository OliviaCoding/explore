package StockProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


/*

 */
public class Main {
    public static Vector<String> loadSymbols(String path, String file) throws IOException {
        //TODO: Create a Vector symbols
        Vector<String> symbols = new Vector<>();
        try{
            //Open the input file and read line by line, trim the string
            FileReader fr = new FileReader(path + "/" + file);//Stocks.txt
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                symbols.add(line.trim());
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        //return the vector
        return symbols;
    }
    public static void main(String[] args) throws IOException {
        //Write a static method that accepts a path and a file name
        //it opens the file (file symbols) and reads it and creates a
        //Vector of strings of all files.

        //if you are testing a risk based on stoploss and target
        int[] riskFactor = {0, 1, 2, 3, 5, 10};//Holding days

        //set path to appropriate path
        String path = "/Users/olivia/Desktop/Data_Structure/Project/Data/";
        //set file to appropriate file (Stock.txt)
        //ETFs.txt
        String file = "Stock.txt";
        //Vector<Trade> trades = new Vector<>(3000);


        Vector<String> symbols = loadSymbols(path,file);

        //loop through your risk array and do the followings
        for(int i=0; i<6; i++){
            System.out.println("Risk Factor: " + riskFactor[i]);
            //create a Tester with path file and riskFactor[i]
            Tester t = new Tester(path, file, riskFactor[i]);

            //Call run method on the tester
            t.run();
            //get the trades vector getTrades()
            Vector<Trade> trades = t.getTrades();
            //trades.clear();
           //trades.toString();// i dont know why we know this one?
            //call the helper method computerStates with the trade vector
            //Statistics st = Helper.computeStats(getTrades());
            Statistics st = new Statistics(trades);
            //display the results using the toString of the Statistics
            System.out.println(st.toString());
            System.out.println("----------------------------------------------------------------------------------------");
        }
        //Change the filename to EFTs.txt and do the same
        //set file to appropriate file (stock.txt)
//        String file2 = "ETFs.txt";
//
//        Vector<String> symbols2 = Main.loadSymbols(path,file2);
//
//        //loop through your risk array and do the following
//        for(int i=0; i<6; i++){
//            System.out.println("Risk Factor: " + riskFactor[i]);
//            //create a Tester with path file and riskFactor[i]
//            Tester t2 = new Tester(path, file2, riskFactor[i]);
//            //Call run method on the tester
//            t2.run();
//            //get the trades vector getTrades()
//            Vector<Trade> trades2 = t2.getTrades();
//            trades2.toString();
//            //call the helper method computerStates with the trade vector
//            //Statistics st = Helper.computeStats(getTrades());
//            Statistics st2 = Statistics.computeStats(trades2);
//            //display the results using the toString of the Statistics
//            System.out.println(st2.toString());
//            System.out.println("----------------------------------------------------------------------------------------");
//        }

    }







}
