import java.io.*;
import java.util.Arrays;
import java.lang.Exception;

public class Decode {
    public static void main(String[] args) {
        Huffman h = new Huffman();
        int[] frequencyArray = new int[256];

        try {
            FileInputStream fis = new FileInputStream(args[0]);
            FileOutputStream fos = new FileOutputStream(args[1]);
            BitInputStream bis = new BitInputStream(fis);

            for (int i = 0; i < 256; i++) {
                frequencyArray[i] = bis.readInt();
            }
            PQHeap frequencyHeap = h.buildFrequencyHeap(frequencyArray);
            Node n = h.huffman(frequencyHeap);
            Decode d = new Decode();
            d.decode(bis, fos, n);
            System.out.println("Succes! File decoded");
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void decode(BitInputStream bis, FileOutputStream fos, Node root) {
        Node n = root;
        try {
            boolean reading = true;
            while (reading) {
                int i = bis.readBit();
                if (i == 0) {
                    n = n.leftChild;
                } else if (i == 1) {
                    n = n.rightChild;
                } else {
                    reading = false;
                }

                if (n.leftChild == null && n.rightChild == null) {
                    fos.write(n.character);
                    n = root; 
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
