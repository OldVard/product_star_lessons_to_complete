package homework_data_structures;

import java.util.Arrays;

public class Array {
    static int MAX_SIZE = 2;
    static int currentSize = 0;
    static int[] array = new int[MAX_SIZE];

    public static void main(String[] args) {
        add(4);
        add(3);
        add(1);
        add(5);
        print();
        System.out.println("\n=====================");
        sort(array, 0, array.length - 1);
        print();
    }

    // low — откуда начать, high — где закончить.
    public static int[] sort(int[] array, int low, int high) {
        if(low < high) {
            int partition = partition(array, low, high);

            sort(array, low, partition - 1);
            sort(array,partition + 1, high);
        }
        return array;
    }

    // Эта функция находит опорный элемент (pivot) и переставляет всё так, чтобы:
    // слева от него были меньшие элементы
    // справа — большие
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        int temp;
        for(int j = low; j < high; j++) {
            if(array[j] < pivot) {
                i++;
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        temp = array[i+1];
        array[i+1] = array[high];
        array[high] = temp;

        return i+1;
    }

    public static void add(int value) {
        if(currentSize >= MAX_SIZE) {
            MAX_SIZE *= 2;
            array = Arrays.copyOf(array, MAX_SIZE);
        }
        array[currentSize] = value;
        currentSize++;
    }

    public static void print() {
        System.out.print("{ ");
        for(int n : array) {
            System.out.print(n + " ");
        }
        System.out.print("}");
    }
}
