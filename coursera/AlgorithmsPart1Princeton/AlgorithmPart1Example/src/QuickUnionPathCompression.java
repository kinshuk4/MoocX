public class QuickUnionPathCompression {
    public int[] id;
    private int[] sz;
    public QuickUnionPathCompression(int N)
    {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }
    public int root(int p) {
        int q = p;
        while (id[q] != q) { q = id[q];}
        id[p] = q; // Path Compression
        sz[q] = 2; // Change the size after path compression
        return q;
    }
    
    public boolean connected(int id1, int id2) {
        return root(id1) == root(id2);
    }
    
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else               { id[j] = i; sz[i] += sz[j]; } 
    }
    
    public static void main(String[] args) {
        int N = 10;
        QuickUnionPathCompression q = new QuickUnionPathCompression(N);
        q.union(3, 8);
        q.union(5, 2);
        q.union(2, 3);
        q.union(9, 1);
        q.union(7, 4);
        q.union(3, 9);
        for (int i = 0; i < N; i++) 
            System.out.print(q.id[i] + " ");
    }
}