public class Stack {
    static int MAX_SIZE = 5;
    static int currentSize = 0;
    static int[] data = new int[MAX_SIZE];

    public static void main(String[] args) {
        push(3);
        push(2);
        push(1);
        push(4);
        push(5);
        print();
        System.out.println(peek());
        System.out.println(pop());
        System.out.println(pop());
        print();
    }

    public static int pop() {
        if(isEmpty()) {
            throw new RuntimeException("Стек пуст!");
        }
        int elem = data[currentSize - 1];
        currentSize--;
        return elem;
    }

    public static int peek() {
        if(isEmpty()) {
            throw new RuntimeException("Стек пуст!");
        }
        return data[currentSize - 1];
    }

    public static void push(int value) {
        if(currentSize > MAX_SIZE) {
            throw new RuntimeException("Стек переполнен!");
        }
        data[currentSize] = value;
        currentSize++;
    }

    public static void print() {
        System.out.println("___");
        if(currentSize > 0) {
            for(int i = currentSize - 1; i >= 0; i--) {
                System.out.println("|" + data[i] + "|");
            }
        }
        System.out.println("___");
    }

    public static boolean isEmpty() {
        return currentSize == 0;
    }
}
