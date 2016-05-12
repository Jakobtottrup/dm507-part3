import java.io.*;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.lang.Math.*;

public class Encode {
    public static void main(String[] args) {
        Huffman h = new Huffman();
        int[] frequencyArray = null;
        try {
            frequencyArray = h.readFileFrequencies(args[0]);
        } catch (FileNotFoundException ex) {
            System.out.println("Filen findes ikke");
            return;
        }
        PQHeap pqh = h.buildFrequencyHeap(frequencyArray);
        Node r = h.huffman(pqh);
        String[] codeArray = h.buildCodeArray(r);
        try {
            h.encodeAndWrite(args[0], args[1], codeArray, frequencyArray);
            h.readOutputFile(args[1]);
        } catch (FileNotFoundException ex) {
            System.out.println("Filen findes ikke");
        }
    }
}
