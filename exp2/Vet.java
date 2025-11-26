import java.util.Arrays;

public class Vet implements Database {

    private String name;
    private Pet[] clients;
    private int n;

    public Vet(int startSize, String who) {
        this.name = who;
        int cap = startSize > 0 ? startSize : STARTSIZE;
        this.clients = new Pet[cap];
        this.n = 0;
    }

    private void ensureCapacity() {
        if (n >= clients.length) {
            clients = Arrays.copyOf(clients, clients.length * 2);
        }
    }

    @Override
    public int size() { return n; }

    @Override
    public void display() {
        System.out.println("Vet " + name + " client list: ");
        for (int i = 0; i < n; i++) {
            System.out.println(clients[i]);
        }
    }

    /** 线性查找，按 Pet.equals 忽略大小写匹配 */
    public Object find(Object o) {
        if (!(o instanceof Pet)) return null;
        Pet key = (Pet) o;
        for (int i = 0; i < n; i++) {
            if (clients[i].equals(key)) return clients[i];
        }
        return null;
    }

    @Override
    public boolean add(Object o) {
        if (!(o instanceof Pet)) return false;
        ensureCapacity();
        clients[n++] = (Pet) o;
        return true;
    }

    @Override
    public Object delete(Object o) {
        if (!(o instanceof Pet)) return null;
        Pet key = (Pet) o;
        for (int i = 0; i < n; i++) {
            if (clients[i].equals(key)) {
                Pet removed = clients[i];
                // 保持顺序：后面元素左移
                for (int j = i + 1; j < n; j++) clients[j - 1] = clients[j];
                clients[--n] = null;
                return removed;
            }
        }
        return null;
    }

    public double averageWeight() {
        if (n == 0) return 0.0;
        double sum = 0.0;
        for (int i = 0; i < n; i++) sum += clients[i].getWeight();
        return sum / n;
    }

    public void sort() {
        Arrays.sort(this.clients, 0, this.size());
    }
}
