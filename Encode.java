import java.io.*;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.lang.Math.*;

public class Encode {
    public static void main(String[] args) {
        Huffman h = new Huffman();
        Encode e = new Encode();

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
            e.encodeAndWrite(args[0], args[1], codeArray, frequencyArray);
            e.readOutputFile(args[1]);
        } catch (FileNotFoundException ex) {
            System.out.println("Filen findes ikke");
        }
    }

    public void encodeAndWrite(String inputFile, String outputFile, String[] codeArray, int[] frequencyArray) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);

        BitInputStream bis = new BitInputStream(fis);
        BitOutputStream bos = new BitOutputStream(fos);


        try {
            for (int i = 0; i < 256; i++) {
                bos.writeInt(frequencyArray[i]);
            }
            boolean reading = true;
            while (reading){
                int a = fis.read();
                if (a != -1){
                    String s = codeArray[a];
                    int[] numbers = new int [s.length()];

                    for (int i = 0; i < s.length(); i++){
                        numbers[i] = Character.getNumericValue(s.charAt(i));
                    }

                    for (int i : numbers){
                        bos.writeBit(i);
                    }
                } else {
                    reading = false;
                }
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void readOutputFile(String outputFile) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(outputFile);
        BitInputStream bis = new BitInputStream(fis);

        try {
            for (int i = 0; i < 256; i++) {
                System.out.print(bis.readInt()+" ");
            }
            boolean reading = true;
            while (reading) {
                int a = bis.readBit();
                if (a != -1) {
                    System.out.print(a);
                } else {
                    reading = false;
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
