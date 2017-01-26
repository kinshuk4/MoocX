package dynamiccompilation;

public class MathTestMain {
    public interface Operator {
        public double operate(double d);
    }

    public static class SimpleAdder implements Operator {
        public double operate(double d) {
            return d + 1.0;
        }
    }

    public static class DoubleAdder implements Operator {
        public double operate(double d) {
            return d + 0.5 + 0.5;
        }
    }

    public static class RoundaboutAdder implements Operator {
        public double operate(double d) {
            return d + 2.0 - 1.0;
        }
    }

    public static void runABunch(Operator op) {
        long start = System.currentTimeMillis();
        double d = 0.0;
        for (int i = 0; i < 5000000; i++)
            d = op.operate(d);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end-start) + "   ignore:" + d);
    }

    public static void main(String[] args) {
        Operator ra = new RoundaboutAdder();
        runABunch(ra); // misguided warmup attempt
        runABunch(ra);
        Operator sa = new SimpleAdder();
        Operator da = new DoubleAdder();
        runABunch(sa);
        runABunch(da);
        
    }
}
