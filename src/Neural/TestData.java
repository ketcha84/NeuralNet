package Neural;

import java.util.ArrayList;
import java.util.Arrays;

public class TestData {

    private NeuralXOR neuralNet;

    public TestData(NeuralXOR neuralNet) {
        this.neuralNet = neuralNet;
    }

    public void startSimpleTests() {
        ArrayList<double[]> tests = new ArrayList<>();
        tests.add(new double[]{0, 0});
        tests.add(new double[]{0, 1});
        tests.add(new double[]{1, 0});
        tests.add(new double[]{1, 1});

        ArrayList<Double>results = new ArrayList<>();
        results.add(0.2d);
        results.add(0.8d);
        results.add(0.8d);
        results.add(0.2d);
        double res = 0d;
        double[] in = new double[2];

        do {
            for (int i = 0; i < 10000; i++) {
                for (int j = 0; j < tests.size(); j++) {
                    in = tests.get(j);
                    try {
                        res = neuralNet.startNet(in, results.get(j));
                        System.out.println("inputs: " + Arrays.toString(in) + "\t||\tResult: " + res);
                        System.out.println(neuralNet);
                    } catch (NeuralException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (res - results.get(3) != 0);
    }

    public void startHardTest() {
        double[] in = new double[2];
        for (int i = 0; i < 10000; i++) {
            in[0] = Math.random();
            in[1] = Math.random();
            try {
                double res = neuralNet.startNet(in);
                System.out.println("inputs: " + Arrays.toString(in) + "\t||\tExpected: " + getExpected(in) + "\t||\tResult: " + res);
            } catch (NeuralException e) {
                e.printStackTrace();
            }
        }

    }

    private double getExpected(double[] in) {
        int n1 = in[0] < 0.5 ? 0 : 1;
        int n2 = in[1] < 0.5 ? 0 : 1;

        if (n1 + n2 == 2 || n1 + n2 == 0) {
            return 0.2d;
        } else {
            return 0.8d;
        }
    }
}