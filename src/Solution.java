import Neural.NeuralXOR;
import Neural.TestData;

import java.io.*;


public class Solution {
    private static double expectedOutput;
    private static double[] in;
    private static double learningRate = 0.1d;

    public static void main(String[] args) throws IOException {
         NeuralXOR net = new NeuralXOR();



        TestData tester = new TestData(net);
        tester.startSimpleTests();
    }

    private static void saveNet(NeuralXOR net) {
        File file = new File("neural.bin");
        if (!file.exists()) {
            try {
                System.out.println("File not found.\nCreating new file: \"neural.bin\"");
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(net);
            System.out.println("Neural net saved.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static NeuralXOR loadNet() throws FileNotFoundException {
        File file = new File("neural.bin");
        Object obj = new Object();

        if (!file.exists()) {
            throw new FileNotFoundException("File not found");
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            obj =  ois.readObject();
            System.out.println("Neural net loaded");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (NeuralXOR) obj;
    }

}
