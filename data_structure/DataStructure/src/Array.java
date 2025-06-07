public class Array {
    static int MAX_SIZE = 2;
    static int currentSize = 0;
    static int[] array = new int[MAX_SIZE];

    public static void main(String[] args) {
        System.out.println("Состояние массива: " + isEmpty());
        add(1);
        System.out.println("Состояние массива: " + isEmpty());
        add(2);
        add(3);
        System.out.println("Индекс 2: " + indexOf(2));
        System.out.println("Содержит 3: " + contains(3));
        System.out.println("Содержит 4: " + contains(4));
        add(4);
        add(5);
        remove(3);
        System.out.println(get(3));
        print();
    }

    public static void print() {
        System.out.print("[");
        for(int n : array) {
            System.out.print(" " + n);
        }
        System.out.println(" ]");
    }

    public static String isEmpty() {
        return currentSize == 0 ? "пусто" : "есть элементы";
    }

    public static void add(int elem) {
        if(currentSize >= MAX_SIZE) {
            MAX_SIZE *= 2;
            array = createNewArray();
        }
        array[currentSize] = elem;
        currentSize++;
    }

    public static int[] createNewArray() {
        int[] newArray = new int[MAX_SIZE];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    public static void remove(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Индекс не найден :(");
        }
        for (int i = index; i < currentSize - 1; i++) {
            array[i] = array[i + 1];
        }
        array[currentSize - 1] = 0;
        currentSize--;
    }

    public static int indexOf(int value) {
        for (int i = 0; i < currentSize; i++) {
            if(array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(int value) {
        return indexOf(value) >= 0;
    }

    public static int get(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Индекс не найден :(");
        }
        return array[index];
    }
}