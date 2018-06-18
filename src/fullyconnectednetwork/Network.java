package fullyconnectednetwork;

import parser.Attribute;
import parser.Node;
import parser.Parser;
import parser.ParserTools;

import java.util.Arrays;

import TrainSet.TrainSet;

/**
 * Created by Luecx on 30.05.2017.
 * Modified by Lucas on 3/27/2018.
 */
public class Network {

    public static final double LEARNING_RATE = 0.3; // this is a well tested number.

    public static final int ZERO_OR_ONE = 0;
    public static final int NEGATIVE_ONE_OR_ONE = 1;
    public static final int ZERO_TO_ONE = 2;
    public static final int NEGATIVE_ONE_TO_ONE = 3;
    public static final int ZERO_JUMP_X = 4;
    public static final int NEGATIVE_X_JUMP_X = 5;
    public static final int RECTIFIER = 6; // 0 - infinity

    public final int ACTIVATION_FUNCTION;
    public final double multiplier;

    private double[][] output;
    private double[][][] weights;
    private double[][] bias;

    private double[][] error_signal;
    private double[][] output_derivative;

    public final int[] NETWORK_LAYER_SIZES;
    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;
    public final int NETWORK_SIZE;

    public Network(int ActivationFunction, int... NETWORK_LAYER_SIZES) {
        this.multiplier = 1;
        this.ACTIVATION_FUNCTION = ActivationFunction;
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE - 1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];

        this.error_signal = new double[NETWORK_SIZE][];
        this.output_derivative = new double[NETWORK_SIZE][];

        for (int i = 0; i < NETWORK_SIZE; i++) {
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.error_signal[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];

            this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], -1, 1);

            if (i > 0) {
                weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1], -1, 1);
            }
        }
    }

    public Network(int ActivationFunction, double multiplier, int... NETWORK_LAYER_SIZES) {
        this.multiplier = multiplier;
        this.ACTIVATION_FUNCTION = ActivationFunction;
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE - 1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];

        this.error_signal = new double[NETWORK_SIZE][];
        this.output_derivative = new double[NETWORK_SIZE][];

        for (int i = 0; i < NETWORK_SIZE; i++) {
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.error_signal[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];

            this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], -1, 1);

            if (i > 0) {
                weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1], -1, 1);
            }
        }
    }

    public double[] calculate(double... input) {
        if (input.length != this.INPUT_SIZE) {
            return null;
        }
        switch (this.ACTIVATION_FUNCTION) {
            case 0:
                this.unitStepLoops(input);
                break;
            case 1:
                this.signumLoops(input);
                break;
            case 2:
                this.sigmoidLoops(input);
                break;
            case 3:
                this.hyperbolicTangentLoops(input);
                break;
            case 4:
                this.jumpStepLoops(input);
                break;
            case 5:
                this.jumpSignumLoops(input);
                break;
            case 6:
                this.rectifierLoops(input);
                break;
        }
        return output[NETWORK_SIZE - 1];
    }

    private void unitStepLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.unitStep(sum);
                output_derivative[layer][neuron] = output[layer][neuron];
            }
        }
    }

    private void signumLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.signum(sum);
                output_derivative[layer][neuron] = output[layer][neuron];
            }
        }
    }

    private void sigmoidLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.sigmoid(sum);
                output_derivative[layer][neuron] = (this.multiplier * Math.exp(-sum)) / Math.pow(1 + Math.exp(-sum), 2);
            }
        }
    }

    private void hyperbolicTangentLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.hyperbolicTangent(sum);
                output_derivative[layer][neuron] = this.multiplier * ((Math.exp(2 * sum / this.multiplier) - 1) / (Math.exp(2 * sum / this.multiplier) + 1));
            }
        }
    }

    private void jumpStepLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.jumpStep(sum);
                output_derivative[layer][neuron] = output[layer][neuron];
            }
        }
    }

    private void jumpSignumLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.jumpSignum(sum);
                output_derivative[layer][neuron] = output[layer][neuron];
            }
        }
    }

    private void rectifierLoops(double... input) {
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = this.rectifier(sum);
                output_derivative[layer][neuron] = output[layer][neuron];
            }
        }
    }

    public void train(TrainSet set, int loops, int batch_size) {
        if (set.INPUT_SIZE != INPUT_SIZE || set.OUTPUT_SIZE != OUTPUT_SIZE) {
            return;
        }
        for (int i = 0; i < loops; i++) {
            TrainSet batch = set.extractBatch(batch_size);
            for (int b = 0; b < batch_size; b++) {
                this.train(batch.getInput(b), batch.getOutput(b), LEARNING_RATE);
            }
            //System.out.println(MSE(batch));
        }
    }

    public void train(TrainSet set, int loops, int batch_size, int saveInterval, String file) {
        if (set.INPUT_SIZE != INPUT_SIZE || set.OUTPUT_SIZE != OUTPUT_SIZE) {
            return;
        }
        for (int i = 0; i < loops; i++) {
            TrainSet batch = set.extractBatch(batch_size);
            for (int b = 0; b < batch_size; b++) {
                this.train(batch.getInput(b), batch.getOutput(b), LEARNING_RATE);
            }
            System.out.println(MSE(batch));
            if (i % saveInterval == 0) {
                try {
                    saveNetwork(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            saveNetwork(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void train(double[] input, double[] target, double eta) {
        if (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) {
            return;
        }
        calculate(input);
        backpropError(target);
        updateWeights(eta);
    }

    public double MSE(double[] input, double[] target) {
        if (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) {
            return 0;
        }
        calculate(input);
        double v = 0;
        for (int i = 0; i < target.length; i++) {
            v += (target[i] - output[NETWORK_SIZE - 1][i]) * (target[i] - output[NETWORK_SIZE - 1][i]);
        }
        return v / (2d * target.length);
    }

    public double MSE(TrainSet set) {
        double v = 0;
        for (int i = 0; i < set.size(); i++) {
            v += MSE(set.getInput(i), set.getOutput(i));
        }
        return v / set.size();
    }

    public void backpropError(double[] target) {
        for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[NETWORK_SIZE - 1]; neuron++) {
            error_signal[NETWORK_SIZE - 1][neuron] = (output[NETWORK_SIZE - 1][neuron] - target[neuron])
                    * output_derivative[NETWORK_SIZE - 1][neuron];
        }
        for (int layer = NETWORK_SIZE - 2; layer > 0; layer--) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
                double sum = 0;
                for (int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZES[layer + 1]; nextNeuron++) {
                    sum += weights[layer + 1][nextNeuron][neuron] * error_signal[layer + 1][nextNeuron];
                }
                this.error_signal[layer][neuron] = sum * output_derivative[layer][neuron];
            }
        }
    }

    public void updateWeights(double eta) {
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

                double delta = -eta * error_signal[layer][neuron];
                bias[layer][neuron] += delta;

                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    weights[layer][neuron][prevNeuron] += delta * output[layer - 1][prevNeuron];
                }
            }
        }
    }

    private double sigmoid(double x) {// 0 - 1
        return this.multiplier / (1 + Math.exp(-x));
    }

    public double hyperbolicTangent(double x) { // -1 - 1
        return this.multiplier * ((Math.exp(x / this.multiplier) - Math.exp(-x / this.multiplier)) / (Math.exp(x / this.multiplier) + Math.exp(-x / this.multiplier)));
    }

    private double unitStep(double x) {// 1 or 0
        if (x > this.multiplier / 2) {
            return 1;
        } else if (x < this.multiplier / 2) {
            return 0;
        } else {
            return 0.5;
        }
    }

    private double signum(double x) {//values -1 or 1
        if (x > this.multiplier / 2) {
            return 1;
        } else if (x < this.multiplier / 2) {
            return -1;
        } else {
            return 0;
        }
    }

    private double jumpStep(double x) {
        if (x > this.multiplier) {
            return this.multiplier;
        } else if (x < 0) {
            return 0;
        } else {
            return Math.round(x);
        }
    }

    private double jumpSignum(double x) {
        if (x > this.multiplier) {
            return this.multiplier;
        } else if (x < -this.multiplier) {
            return -this.multiplier;
        } else {
            return Math.round(x);
        }
    }

    private double rectifier(double x) {
        return Math.log(1 + Math.exp(x));
    }

    public static void main(String[] args) {
        //NN network = new NN();
        //try {
        //network.saveNet(new File("").getAbsolutePath() + "\\NeuralNetworks\\saves\\frictionNetSave1.txt");
        //} catch (Exception e) {
        //e.printStackTrace();
        //}
        //network.trainAndSave(1000000, 20000, new File("").getAbsolutePath() + "\\NeuralNetworks\\saves\\frictionNetSave1.txt");;
    }

    public void saveNetwork(String fileName) throws Exception {
        Parser p = new Parser();
        p.create(fileName);
        Node root = p.getContent();
        Node netw = new Node("Network");
        Node ly = new Node("Layers");
        netw.addAttribute(new Attribute("Activation Function", Integer.toString(this.ACTIVATION_FUNCTION)));
        netw.addAttribute(new Attribute("Multiplier", Double.toString(this.multiplier)));
        netw.addAttribute(new Attribute("sizes", Arrays.toString(this.NETWORK_LAYER_SIZES)));
        netw.addChild(ly);
        root.addChild(netw);
        for (int layer = 1; layer < this.NETWORK_SIZE; layer++) {

            Node c = new Node("" + layer);
            ly.addChild(c);
            Node w = new Node("weights");
            Node b = new Node("biases");
            c.addChild(w);
            c.addChild(b);

            b.addAttribute("values", Arrays.toString(this.bias[layer]));

            for (int we = 0; we < this.weights[layer].length; we++) {

                w.addAttribute("" + we, Arrays.toString(weights[layer][we]));
            }
        }
        p.close();
    }

    public static Network loadNetwork(String fileName) throws Exception {

        Parser p = new Parser();

        p.load(fileName);
        int af = Integer.parseInt(p.getValue(new String[]{"Network"}, "Activation Function"));
        double Multiplyer = Double.parseDouble(p.getValue(new String[]{"Network"}, "Multiplier"));
        String sizes = p.getValue(new String[]{"Network"}, "sizes");
        int[] si = ParserTools.parseIntArray(sizes);
        Network ne = new Network(af, Multiplyer, si);

        for (int i = 1; i < ne.NETWORK_SIZE; i++) {
            String biases = p.getValue(new String[]{"Network", "Layers", new String(i + ""), "biases"}, "values");
            double[] bias = ParserTools.parseDoubleArray(biases);
            ne.bias[i] = bias;

            for (int n = 0; n < ne.NETWORK_LAYER_SIZES[i]; n++) {

                String current = p.getValue(new String[]{"Network", "Layers", new String(i + ""), "weights"}, "" + n);
                double[] val = ParserTools.parseDoubleArray(current);

                ne.weights[i][n] = val;
            }
        }
        p.close();
        return ne;

    }

}
