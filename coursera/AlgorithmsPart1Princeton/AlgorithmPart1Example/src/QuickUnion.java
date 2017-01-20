public class QuickUnion {
    public int[] id;
    public QuickUnion(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }
    
    public int root(int p) {
        while (id[p] != p) p = id[p];
        return p;
    }
    
    public boolean connected(int id1, int id2) {
        return root(id1) == root(id2);
    }
    
    public void union(int p, int q) {
        id[root(p)] = root(q);
    }
    
    public static void main(String[] args) {
        int N = 10;
        QuickUnion q = new QuickUnion(N);
        q.union(7, 4);
        q.union(8, 9);
        q.union(1, 0);
        q.union(4, 3);
        q.union(2, 5);
        q.union(4, 6);
        q.union(9, 2);
        q.union(7, 9);
        q.union(5, 1);
        //q.union(2, 3);
        for (int i = 0; i < N; i++) 
            System.out.print(q.id[i] + " ");
    }
}