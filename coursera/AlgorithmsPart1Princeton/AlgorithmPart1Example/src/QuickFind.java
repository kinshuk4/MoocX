public class QuickFind {
    private int[] id;
    public QuickFind(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }
    
    public boolean connected(int id1, int id2) {
        return id[id1] == id[id2];
    }
    
    public void union(int id1, int id2) {
        int id_1 = id[id1];
        int id_2 = id[id2];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == id_1) id[i] = id_2; 
        }
    }
    
    public static void main(String[] args) {
        QuickFind q = new QuickFind(10);
        q.union(2, 9);
        q.union(9, 5);
        q.union(1, 3);
        q.union(4, 7);
        q.union(0, 8);
        q.union(9, 4);
        for (int i = 0; i < 10; i++) 
            System.out.print(q.id[i] + " ");
    }
}