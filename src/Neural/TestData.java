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

        for (int i = 0; i < 10000; i++) {
            for (double[] in : tests) {
                try {
                    double res = neuralNet.startNet(in);
                    System.out.println("inputs: " + Arrays.toString(in) + "\t||\tResult: " + res);
                } catch (NeuralException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private double activationFX(double x) {
        return x > 0.5 ? 1 : 0;
    }

    private double getExpected(double[] in) {
        int n1 = in[0] < 0.5 ? 0 : 1;
        int n2 = in[1] < 0.5 ? 0 : 1;

        if (n1 + n2 == 2 || n1 + n2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}