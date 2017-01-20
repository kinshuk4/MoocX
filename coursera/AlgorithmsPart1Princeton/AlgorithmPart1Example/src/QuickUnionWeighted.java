public class QuickUnionWeighted {
    public int[] id;
    private int[] sz;
    public QuickUnionWeighted(int N)
    {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }
    
    private int root(int p) {
        while (id[p] != p) p = id[p];
        return p;
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
        QuickUnionWeighted q = new QuickUnionWeighted(N);
        q.union(7, 0);
        q.union(3, 0);
        q.union(9, 1);
        q.union(4, 0);
        q.union(8, 6);
        q.union(4, 2);
        q.union(9, 8);
        q.union(6, 2);
        q.union(8, 5);
        for (int i = 0; i < N; i++) 
            System.out.print(q.id[i] + " ");
    }
}