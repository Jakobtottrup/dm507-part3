public class PQHeap implements PQ {
    private Element[] a;
    public int size;

    public PQHeap(int maxSize) {
        a = new Element[maxSize+1];
        a[0] = new Element(Integer.MAX_VALUE, "SENTINEL");
    }

    public int parent(int i) {
        return i/2;
    }

    public int left(int i) {
        return 2*i;
    }

    public int right(int i) {
        return (2*i)+1;
    }

    private Element[] minHeapify(Element[] a, int i) {
        int l = left(i);
        int r = right(i);
        int smallest;

        if (l <= size && a[l].key < a[i].key) smallest = l;
        else smallest = i;

        if (r <= size && a[r].key < a[smallest].key) smallest = r;
        if (smallest != i){
            Element tmp = a[i];
            a[i] = a[smallest];
            a[smallest] = tmp;
            minHeapify(a, smallest);
        }
        return a;
    }

    private void decreaseKey(Element[] a, int i, int key) {
        if (key > a[i].key) return;
        a[i].key = key;
        if (i == 0) return;
        while ( i > 0 && a[parent(i)].key > a[i].key){
            Element tmp = a[i];
            a[i] = a[parent(i)];
            a[parent(i)] = tmp;
            i = parent(i);
        }
    }

    @Override
    public Element extractMin() {
        if (size < 1) return null;
        Element min = a[0];
        a[0] = a[size];
        size -= 1;
        minHeapify(a, 0);
        return min;
    }

    @Override
    public void insert(Element e) {
        size++;
        a[size] = new Element(Integer.MAX_VALUE, e.data);
        decreaseKey(a, size, e.key);
    }
}

