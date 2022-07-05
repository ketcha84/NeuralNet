package Neural;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * inptus[] - входной слой
 * w1[][] - веса на входе в скрытый слой
 * w2[] - веса на входе в output нейрон
 * hL1[] - скрытый слой
 * output -  выходной нейрон
 * double expectedOutput - ожидаемый ответ
 * double learningRate - влияет на скорость обучения
 */
public class NeuralXOR implements Serializable {
    @Serial
    private static final long serialVersionUID = 1656354785726L; // Date : (27.03.2022 : 20:33)

    transient private double[] inputs = new double[2];
    transient private double expectedOutput;
    transient private double output;
    transient private double[] hL1 = new double[2];
    transient private ModeNet mode;
    transient private int bias = 1;

    private double[][] w1 = new double[2][2];
    private double[] w2 = new double[2];
    private double learningRate;


    /**
     * Конструктор в нем инициализируем веса двух слоев.
     **/
    public NeuralXOR() {
        this.learningRate = 0L;
        this.mode = ModeNet.NORMAL;
        w1[0][0] = 0.6103314022204716;
        w1[0][1] = 0.5021948389376554;
        w1[1][0] = 0.4759116042155219;
        w1[1][1] = 0.4759116042155219;

        w2[0] = 0.518656516584927;
        w2[1] = -0.0313434834150734;
    }

    /**
     * @param learningRate learning rate
     * @param mode         working mode for neural net
     */
    public NeuralXOR(double learningRate, ModeNet mode) {
        this.learningRate = learningRate;
        this.mode = mode;
        switch (mode) {
            case LEARNING -> {
                Arrays.fill(w1[0], Math.random());
                Arrays.fill(w1[1], Math.random());
                Arrays.fill(w2, Math.random());
//                w1[0][0] = 0.6103314022204716;
//                w1[0][1] = 0.5021948389376554;
//                w1[1][0] = 0.4759116042155219;
//                w1[1][1] = 0.4759116042155219;
//
//                w2[0] = 0.518656516584927;
//                w2[1] = -0.0313434834150734;
            }
        }
    }

    /**
     * Method for NORMAL mode
     *
     * @param in array inputs
     * @return double result
     */
    public double startNet(double[] in) throws NeuralException {
        switch (mode) {
            case LEARNING -> throw new NeuralException("\nWrong Mode of Neural Net. \nMissing expected result");
        }
        this.inputs = in;
        fire();
        return output;
    }

    /**
     * Method for LEARNING mode
     *
     * @param in             array of inputs
     * @param expectedOutput value of expected output
     * @return double value the result of work net
     */
    public double startNet(double[] in, double expectedOutput) throws NeuralException {
        switch (mode) {
            case NORMAL -> throw new NeuralException("\nWrong Mode of Neural Net.\nThere are extra parameters");
        }
        this.inputs = in;
        this.expectedOutput = expectedOutput;
        fire();
        return output;
    }

    private void fire() {
        hL1[0] = activationFunction(inputs, w1[0]);
        hL1[1] = activationFunction(inputs, w1[1]);
        output = activationFunction(hL1, w2);
        switch (mode) {
            case LEARNING -> recalcAll();
        }
    }

    //--------------------------------------------- activation and sigmoid
    private double activationFunction(double[] in, double[] weight) {
        double sum = 0d;
        for (int i = 0; i < in.length; i++) {
            sum += in[i] * weight[i] + bias;
        }
//        return sum > 0.5 ? 1 : 0;
        return sigmoid(sum);
    }

    private double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    //--------------------------------------------- Errors and learnings
    //-------------------------------- Error Block

    /**
     * Used for output layer
     *
     * @param actual double
     * @return double value
     */
    private double getError(double actual) {
        return actual - expectedOutput;
    }

    /**
     * Used for hidden layers
     *
     * @param actual      double
     * @param weightDelta double
     * @return double value
     */
    private double getError(double actual, double weightDelta) {
        return actual * weightDelta;
    }

    //-------------------------------- Recalculate weights Block

    /**
     * Recalculate all weights in all layers
     */
    private void recalcAll() {
        double outError = getError(output);
        double deltaOut1 = delta(output, outError);
        recalcWeight(w2, hL1, deltaOut1);
        for (int i = 0; i < hL1.length; i++) {
            double hLerror = getError(hL1[i], deltaOut1);
            double hidDelta = delta(hL1[i], hLerror);
            recalcWeight(w1[i], inputs, hidDelta);
        }
    }

    private void recalcWeight(double[] weights, double[] layer, double delta) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] - layer[i] * delta * learningRate;
        }
    }

    private double delta(double actual, double error) {
        return error * actual * (1 - actual);
    }

    //-------------------------------------------- getters and setters

    public double[][] getW1() {
        return w1;
    }

    public double[] getW2() {
        return w2;
    }


    //--------------------------------------------- toString
    @Override
    public String toString() {
        String head = "\tw1\tw2\t\n";
        String vals = Arrays.deepToString(w1) + "\t" + Arrays.toString(w2) + "\n";
        String bottom = "----------\t----------\t----------\t----------";
        return head + vals + bottom;
    }
}