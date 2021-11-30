package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private double[] temperatureSeries;
    private int size = 1;
    private int num;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[size];
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        final double MIN_TEMP = -273.0;
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        num = temperatureSeries.length;

        while (size < num) {
            size = size * 2;
        }

        double[] arr = new double[size];
        for (int i = 0; i < num; i++) {
            if (temperatureSeries[i] < MIN_TEMP) {
                throw new InputMismatchException();
            }
            arr[i] = temperatureSeries[i];
        }

        this.temperatureSeries = arr;
    }

    public double average() {
        double avg = 0;
        for (double d:this.temperatureSeries) {
            avg += d;
        }

        avg = avg / this.num;
        return avg;
    }

    public double deviation() {
        double avg = average();
        double quadSum = 0;
        for (double d:this.temperatureSeries) {
            quadSum += (d - avg) * (d - avg);
        }
        quadSum = quadSum / this.num;
        quadSum = Math.pow(quadSum, 0.5);
        return quadSum;
    }

    public double min() {
        double min = Double.MAX_VALUE;
        for (double d:this.temperatureSeries) {
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    public double max() {
        final double MIN_SIZE = -100000000000000000000.0;
        double max = MIN_SIZE;
        for (double d:this.temperatureSeries) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        double minDiff = Double.MAX_VALUE;
        for (double d:this.temperatureSeries) {
            if (Math.abs(d) < Math.abs(minDiff)) {
                minDiff = d;
            }
            else if (Math.abs(d) == Math.abs(minDiff) && d > minDiff) {
                minDiff = d;
            }
        }
        return minDiff;
    }

    public double findTempClosestToValue(double tempValue) {
        double minDiff = Double.MAX_VALUE;
        for (double d:this.temperatureSeries) {
            if (Math.abs(d - tempValue) < Math.abs(minDiff - tempValue)) {
                minDiff = d;
            }
            else if (Math.abs(d - tempValue)
                    == Math.abs(minDiff - tempValue) && d > minDiff) {
                minDiff = d;
            }
        }
        return minDiff;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] lessThan = new double[this.temperatureSeries.length];
        int idx = 0;

        for (double d:this.temperatureSeries) {
            if (d < tempValue) {
                lessThan[idx] = d;
                idx += 1;
            }
        }
        return lessThan;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] greaterThan = new double[this.temperatureSeries.length];
        int idx = 0;

        for (double d:this.temperatureSeries) {
            if (d >= tempValue) {
                greaterThan[idx] = d;
                idx += 1;
            }
        }
        return greaterThan;

    }

    public TempSummaryStatistics summaryStatistics() {
        double avgTemp = average();
        double devTemp = deviation();
        double minTemp = min();
        double maxTemp = max();
        return new TempSummaryStatistics(avgTemp, devTemp, minTemp, maxTemp);
    }

    public int addTemps(double... temps) {
        final double MIN_TEMP = -273.0;
        int len = temps.length;
        boolean changedSize = false;

        for (double d:temps) {
            if (d < MIN_TEMP) {
                throw new InputMismatchException();
            }
        }

        while (num + len > size) {
            size = size * 2;
            changedSize = true;
        }

        if (changedSize) {
            double[] arr = new double[size];

            for (int i = 0; i < num; i++) {
                arr[i] = temperatureSeries[i];
            }
            this.temperatureSeries = arr;
        }

        for (double d:temps) {
            temperatureSeries[num] = d;
            num += 1;
        }

        return num;
    }
}
