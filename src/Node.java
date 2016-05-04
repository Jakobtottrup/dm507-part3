public class Node {
    Node leftChild = null;
    Node rightChild = null;
    Node parent = null;
    int character;
    int frequency;

    public Node () {}
    public Node(int character, int frequency) {
        this.character = character;
        this.frequency = frequency;

    }

}

