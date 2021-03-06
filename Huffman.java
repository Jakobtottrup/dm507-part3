import java.io.*;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.lang.Math.*;

public class Huffman {
    public Node huffman(PQHeap minHeap) {
        int n = minHeap.size;
        PQHeap q = minHeap;
        for (int i = 0; i < n-1; i++) {
            Node node = new Node();
            node.leftChild = (Node) q.extractMin().data;
            node.rightChild = (Node) q.extractMin().data;
            node.leftChild.parent = node;
            node.rightChild.parent = node;
            node.frequency = node.leftChild.frequency + node.rightChild.frequency;
            q.insert(new Element(node.frequency, node));
        }
        return (Node) q.extractMin().data;
    }

    public int[] readFileFrequencies(String fileName) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(fileName);
        BitInputStream bis = new BitInputStream(inputStream);
        int[] frequencies = new int[256];
        boolean read = true;
        while (read) {
            try {
                frequencies[inputStream.read()] += 1;
            } catch (Exception e) {
                read = false;
            }
        }
        return frequencies;
    }

    public PQHeap buildFrequencyHeap(int[] frequencies) {
        PQHeap pqh = new PQHeap(frequencies.length);
        for(int i = 0 ; i < frequencies.length ; i++){
            if(frequencies[i] != 0){
                Node n = new Node(i, frequencies[i]);
                pqh.insert(new Element(frequencies[i], n));
            }
        }
        System.out.println(pqh.size);
        return pqh;
    }

    public String[] buildCodeArray(Node root) {
        String[] codeArray = new String[256];
        if(root != null){
            traverse(root, codeArray);
        }
        return codeArray;
    }

    public void traverse(Node n, String[] a) {
        if(n != null){
            traverse(n.leftChild, a);
            if(n.leftChild == null && n.rightChild == null){
                a[n.character] = findCode(n);
            }
            traverse(n.rightChild, a);
        }
    }

    public String findCode(Node n) {
        StringBuilder sb = new StringBuilder();
        findCodeRecurse(n, sb);
        return sb.reverse().toString();
    }

    public void findCodeRecurse(Node n, StringBuilder sb) {
        if (n.parent != null) {
            if (n == n.parent.leftChild) {
                sb.append(0);
            } else {
                sb.append(1);
            }
            findCodeRecurse(n.parent, sb);
        }
    }
}
