package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;
import java.lang.IllegalArgumentException;

public class TemperatureSeriesAnalysis {

    private double[] temperatureSeries;
    private int size = 1;
    private int num;

    public TemperatureSeriesAnalysis() {
        double[] temperatureSeries = new double[size];
        this.temperatureSeries = temperatureSeries;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        num = temperatureSeries.length;

        while (size < num) {
            size = size * 2;
        }

        double[] arr = new double[size];
        for (int i = 0; i < num; i++) {
            if (temperatureSeries[i] < -273) {
                throw new InputMismatchException();
            }
            arr[i] = temperatureSeries[i];
        }

        this.temperatureSeries = arr;
    }

    public double average() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        else {
            double avg = 0;
            for (double d:this.temperatureSeries) {
                avg += d;
            }

            avg = avg / this.num;
            return avg;
        }
    }

    public double deviation() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
            double avg = average();
            double quad_sum = 0;
            for (double d:this.temperatureSeries) {
                quad_sum += Math.pow(d - avg, 2);
            }
            quad_sum = quad_sum / this.num;
            quad_sum = Math.pow(quad_sum, 0.5);
            return quad_sum;
        }
    }

    public double min() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
            double min = Double.MAX_VALUE;
            for (double d:this.temperatureSeries) {
                if (d < min) {
                    min = d;
                }
            }
            return min;
        }
    }

    public double max() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
            double max = -100000000000000000000.0;
            for (double d:this.temperatureSeries) {
                if (d > max) {
                    max = d;
                }
            }
            return max;
        }
    }

    public double findTempClosestToZero() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
            double min_diff = Double.MAX_VALUE;
            for (double d:this.temperatureSeries) {
                if (Math.abs(d) < Math.abs(min_diff)) {
                    min_diff = d;
                }
                else if (Math.abs(d) == Math.abs(min_diff) && d > min_diff) {
                    min_diff = d;
                }
            }
            return min_diff;
        }
    }

    public double findTempClosestToValue(double tempValue) {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
            double min_diff = Double.MAX_VALUE;
            for (double d:this.temperatureSeries) {
                if (Math.abs(d - tempValue) < Math.abs(min_diff - tempValue)) {
                    min_diff = d;
                }
                else if (Math.abs(d - tempValue) == Math.abs(min_diff - tempValue) && d > min_diff) {
                    min_diff = d;
                }
            }
            return min_diff;
        }
    }

    public double[] findTempsLessThen(double tempValue) {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
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
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        else {
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
    }

    public TempSummaryStatistics summaryStatistics() {
        double avgTemp = average();
        double devTemp = deviation();
        double minTemp = min();
        double maxTemp = max();
        TempSummaryStatistics tempSummaryStatistics = new TempSummaryStatistics(avgTemp, devTemp, minTemp, maxTemp);
        return tempSummaryStatistics;
    }

    public int addTemps(double... temps) {
        int len = temps.length;
        boolean changedSize = false;

        for (double d:temps) {
            if (d < -273) {
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
