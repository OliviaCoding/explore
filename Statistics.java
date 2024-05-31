package StockProject;

import java.util.Vector;

public class Statistics {
    double averageProfit; // profit / size(有几个trades)
    double averageHoldingPeriod;
    double averageProfitPerDay;
    double winningPercent;
    double averageProfitLong;
    double averageHoldingPeriodLong;
    double averageProfitPerDayLong;
    double winningPercentLong;
    double averageProfitShort;
    double averageHoldingPeriodShort;
    double averageProfitPerDayShort;
    double winningPercentShort;

    int totalNumOfTrades, numOfLongTrades, numOfShortTrades;

    public Statistics(Vector<Trade> trades) {


    }

    public Statistics(double TotalProfit, double TotalHoldingPeriod, Vector<Trade> Trades, double numWinners,
                      double TotalProfitLong, double numLong, double TotalHoldingPeriodLong, double numWinnersLong,
                      double TotalProfitShort, double numShort, double TotalHoldingPeriodShort, double numWinnersShort) {

        this.averageProfit = TotalProfit / Trades.size();
        this.averageHoldingPeriod = TotalHoldingPeriod / Trades.size();
        this.averageProfitPerDay = averageProfit / averageHoldingPeriod; // Most important
        this.winningPercent = numWinners / Trades.size() * 100;

        // Do the same for longs
        this.averageProfitLong = TotalProfitLong / numLong;
        this.averageHoldingPeriodLong = TotalHoldingPeriodLong / numLong;
        this.averageProfitPerDayLong = averageProfitLong / averageHoldingPeriodLong;
        this.winningPercentLong = numWinnersLong / numLong * 100;

        // Do the same for shorts
        this.averageProfitShort = TotalProfitShort / numShort;
        this.averageHoldingPeriodShort = TotalHoldingPeriodShort / numShort;
        this.averageProfitPerDayShort = averageProfitShort / averageHoldingPeriodShort;
        this.winningPercentShort = numWinnersShort / numShort * 100;
    }

    //Write a Static method that accepts a Vector of Trades and goes
    //through it to compute all statistics, return the statistics as
    //an object.
    public Statistics computeStats(Vector<Trade> trades) {
        //Create a Statistics object
        Statistics stats = new Statistics(trades);

        double totalProfit = 0, ProfitOfLong = 0, ProfitOfShort = 0;
        double totalHoldingPeriod = 0, HoldingPeriodOfLong = 0, HoldingPeriodOfShort = 0;
        int totalLongTrades = 0, totalShortTrades = 0;
        int totalWinners = 0, WinnersofLong = 0, WinnersofShort = 0;

        // Go through Vector trades one by one and compute all the Stats
        for (Trade trade : trades) {
            double profit = trade.percentPL();
            double holdingPeriod = trade.getHoldingPeriod();
            totalProfit += profit;
            totalHoldingPeriod += holdingPeriod;

            if (trade.getDir() == Direction.LONG) {
                ProfitOfLong += profit;
                HoldingPeriodOfLong += holdingPeriod;
                totalLongTrades++;
                if (profit >= 0) {
                    WinnersofLong++;
                    totalWinners++;
                }
            } else if (trade.getDir() == Direction.SHORT) {
                ProfitOfShort += profit;
                HoldingPeriodOfShort += holdingPeriod;
                totalShortTrades++;
                if (profit >= 0) {
                    WinnersofShort++;
                    totalWinners++;
                }
            } else {
                System.out.println("Wrong！");
            }
        }

        // Total stats
        this.totalNumOfTrades = trades.size();
        this.averageProfit = totalProfit / trades.size();
        this.averageHoldingPeriod = totalHoldingPeriod / trades.size();
        this.averageProfitPerDay = this.averageProfit / this.averageHoldingPeriod;
        this.winningPercent = (double) totalWinners / trades.size() * 100;

        // Long trades stats
        if (totalLongTrades > 0) {
            this.numOfLongTrades = totalLongTrades;
            this.averageProfitLong = ProfitOfLong / totalLongTrades;
            this.averageHoldingPeriodLong = HoldingPeriodOfLong / totalLongTrades;
            this.averageProfitPerDayLong = this.averageProfitLong / this.averageHoldingPeriodLong;
            this.winningPercentLong = (double) WinnersofLong / totalLongTrades * 100;
        }

        // Short trades stats
        if (totalShortTrades > 0) {
            this.numOfShortTrades = totalShortTrades;
            this.averageProfitShort = ProfitOfShort / totalShortTrades;
            this.averageHoldingPeriodShort = HoldingPeriodOfShort / totalShortTrades;
            this.averageProfitPerDayShort = this.averageProfitShort / this.averageHoldingPeriodShort;
            this.winningPercentShort = (double) WinnersofShort / totalShortTrades * 100;
        }

        // Return your object
        return stats;
    }


    @Override
    public String toString() {
        return "Statistics{" +
                "Total trades = "+ this.totalNumOfTrades+
                ", averageProfit=" + this.averageProfit +
                ", averageHoldingPeriod=" + this.averageHoldingPeriod +
                ", averageProfitPerDay=" + this.averageProfitPerDay +
                ", winningPercent=" + this.winningPercent + "\n" +
                ", averageProfitLong=" + this.averageProfitLong +
                ", averageHoldingPeriodLong=" + this.averageHoldingPeriodLong +
                ", averageProfitPerDayLong=" + this.averageProfitPerDayLong +
                ", winningPercentLong=" + this.winningPercentLong + "\n" +
                ", averageProfitShort=" + this.averageProfitShort +
                ", averageHoldingPeriodShort=" + this.averageHoldingPeriodShort +
                ", averageProfitPerDayShort=" + this.averageProfitPerDayShort +
                ", winningPercentShort=" + this.winningPercentShort +
                '}';
    }
}