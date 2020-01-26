import java.util.Arrays;

public class StatFormulas {
    public static double mean(double values[]) {
        double sum = 0.0;
        for (double v : values)
            sum += v;
        return (sum / values.length);
    }

    public static double median(double values[]) {
        Arrays.parallelSort(values);
        int len = values.length;
        return values.length % 2 == 0 ? ((values[len / 2] + values[len / 2 - 1]) / 2) : values[(int) Math.floor(len / 2)];
    }

    public static double std(double values[]) {
        double mean = mean(values), sum = 0.0;
        for (double v : values)
            sum += Math.pow(v - mean, 2);
        return Math.sqrt(sum / values.length);
    }
}