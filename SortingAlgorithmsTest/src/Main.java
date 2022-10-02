import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    static int movement = 0;
    static int comparison = 0;
    public static long selectionSortExecutionTime = 0L;
    public static long insertionSortExecutionTime = 0L;
    public static long heapSortExecutionTime = 0L;
    public static long quickSortExecutionTime = 0L;
    public static long mergeSortExecutionTime = 0L;
    public static long radixSortExecutionTime = 0L;

    //Random order
    static int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = min + random.nextInt(100);
        }
        return array;
    }



    public static int[] reverseOrder(int[] array) {
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[array.length - 1 - i] = array[i];
        }
        return array;
    }
    // Returns true if the array values are sorted
    // false otherwise.


    static void print(int arr[], int n) {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }

    //selection sort method
    static void selectionSort(int[] list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < list.length - 1; i++) {
            // Find the minimum in the list[i..list.length-1]
            int currentMin = list[i];
            int currentMinIndex = i;

            for (int j = i + 1; j < list.length; j++) {
                comparison++;
                if (currentMin > list[j]) {
                    currentMin = list[j];
                    currentMinIndex = j;
                }
            }

            //	Swap list[i] wiht list[currentMinIndex[ if necessary
            if (currentMinIndex != i) {
                movement++;
                list[currentMinIndex] = list[i];
                list[i] = currentMin;
            }
        }
        long endTime = System.nanoTime();
        selectionSortExecutionTime = endTime-startTime;

    }

    // Insertion Sort
    public static void insertionSort(int[] list) {
        long startTime = System.nanoTime();
        for (int i = 1; i < list.length; i++) {
            int currentElement = list[i];
            int k;
            for (k = i - 1; k >= 0 && list[k] > currentElement; k--) {
                comparison++;
                list[k + 1] = list[k];

            }
            //insert the current element into list[k + 1]
            movement++;
            list[k + 1] = currentElement;
        }
        long endTime = System.nanoTime();
        insertionSortExecutionTime = endTime-startTime;
    }

    //merge sort
    static void mergeSort(int[] list) {
        comparison++;
        if (list.length > 1) {
            comparison++;
            //	Merge sort the first half
            int [] firstHalf = new int[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
            comparison++;
            mergeSort(firstHalf);
            comparison++;
            //Merge sort the second half
            int secondHalfLength = list.length - list.length / 2;
            int[] secondHalf = new int[secondHalfLength];
            System.arraycopy(list, list.length / 2,
                    secondHalf, 0, secondHalfLength);
            comparison++;
            mergeSort(secondHalf);
            comparison++;

        }

    }
    public static void merge(int[] list1, int[] list2, int[] temp) {
        int current1 = 0; // Current index in list1
        int current2 = 0; // Current index in list2
        int current3 = 0; // Current index in temp

        long startTime = System.nanoTime();

        while (current1 < list1.length && current2 < list2.length) {
            comparison++;
            if (list1[current1] < list2[current2])
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
            comparison++;
        }

        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
        long endTime = System.nanoTime();

        mergeSortExecutionTime = endTime-startTime;
    }

    //Radix Sort
    static int getMax(int arr[], int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)

                mx = arr[i];
                comparison++;
        return mx;
    }

    static void countSort(int arr[], int n, int exp) {
        long starTime = System.nanoTime();
        int output[] = new int[n];
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;
        comparison++;
        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];
        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            movement++;
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
            comparison++;

        }
        for (i = 0; i < n; i++)
            arr[i] = output[i];
        movement++;
        long endTime = System.nanoTime();
        radixSortExecutionTime = endTime-starTime;
    }

    static void radixsort(int arr[], int n) { // Find the maximum number to know number of digits
        int m = getMax(arr, n);
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }


    // Quick Sort
    public static int partition(int[] list, int first, int last) {
        int pivot = list[first]; // Choose the first element as the pivot
        int low = first + 1; // Index for forward search
        int high = last; //Index for backward search

        long startTime = System.nanoTime();
        while (high > low) {
            comparison++;
            // Search forward from left
            while (low <= high && list[low] <= pivot)
                low++;
            comparison++;

            // Search backward from right
            while (low <= high && list[high] > pivot)
                high--;
            comparison++;

            //	Swap two elements in the list

            if (high > low) {
                comparison+=2;
                int temp = list[high];
                list[high] = list[low];
                list[low] = temp;
                movement++;
                comparison++;
            }
        }


        while (high > first && list[high] >= pivot)
            high--;

        //	Swap pivot with list[high]
        if (pivot > list[high]) {

            list[first] = list[high];
            list[high] = pivot;
            comparison+=2;
            movement++;
            return high;


        }

        else
            return first;
    }

    public static void quickSort(int[] list, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSort(list, first, pivotIndex -1);
            quickSort(list, pivotIndex + 1, last);
        }



    }
    static void heapify(int a[], int n, int i)
    {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left child
        int right = 2 * i + 2; // right child
        comparison++;
        // If left child is larger than root
        if (left < n && a[left] > a[largest])

            largest = left;
        // If right child is larger than root
        if (right < n && a[right] > a[largest])
            largest = right;
        comparison++;
        // If root is not largest

        if (largest != i) {
            comparison++;
            // swap a[i] with a[largest]
            int temp = a[i];
            a[i] = a[largest];
            a[largest] = temp;

            heapify(a, n, largest);
        }
    }
    static void heapSort(int a[], int n)
    {
        long startTime = System.nanoTime();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(a, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            /* Move current root element to end*/
            // swap a[0] with a[i]
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;

            heapify(a, i, 0);
            movement+=1;
        }
        long endTime = System.nanoTime();
        heapSortExecutionTime = endTime-startTime;
    }

    // Main
    public static void main(String[] args) {


        //Selection Sort
        int[] randomArray =  generateRandomArray(50000,0,50000);

        //inOrder
        //Arrays.sort(randomArray);
        //reverseOrder(randomArray);
        //almostOrder
        //int n = randomArray.length/2;
        //Arrays.sort(randomArray, 0,n );


        System.out.println();

        Main.comparison = 0;

        Main.movement = 0;

        selectionSort(randomArray);
        System.out.print("Show Array: " + "\n");
        //print(randomArray, randomArray.length);
        System.out.println("\n" + "Input Size: " + randomArray.length);
        System.out.println("Data Type: " + "Random Order");
        System.out.println("Sort: Selection Sort");
        System.out.println("Number of Movements: " + Main.movement);
        System.out.println("Number of Comparisons: " + Main.comparison);
        System.out.println("Total time: " + selectionSortExecutionTime + " ns" + " \n");

        //Insertion Sort
        int[] randomArray2 =  generateRandomArray(50000,0,50000);
        //Arrays.sort(randomArray2);
        //reverseOrder(randomArray2);
        //int n1 = randomArray2.length/2;
        //Arrays.sort(randomArray2, 0,n1 );

        System.out.println();

        Main.comparison = 0;

        Main.movement = 0;

        insertionSort(randomArray2);
        System.out.print("Show Array: " + "\n");
        //print(randomArray2, randomArray2.length);
        System.out.println("\n" + "Input Size: " + randomArray2.length);
        System.out.println("Data Type: " + "Random Order");
        System.out.println("Sort: Insertion Sort");
        System.out.println("Number of Movements: " + Main.movement);
        System.out.println("Number of Comparisons: " + Main.comparison);
        System.out.println("Total time: " + insertionSortExecutionTime + " ns" + "\n");


        //Merge Sort
        Main.comparison = 0;

        Main.movement = 0;
        int[] mergeRandomArray1 =  generateRandomArray(25000,0,25000);
        int[] mergerRandomArray2 = generateRandomArray(25000,0,25000);
        int[] temp = new int[mergeRandomArray1.length + mergerRandomArray2.length];
        merge(mergeRandomArray1, mergerRandomArray2, temp);
        mergeSort(temp);

        //Arrays.sort(temp);
        //reverseOrder(temp);
       // int n2 = temp.length/2;
        //Arrays.sort(temp, 0,n2 );

        System.out.println();

        System.out.print("Show Array: " + "\n");
        //print(temp, temp.length);
        System.out.println("\n" + "Input Size: " + temp.length);
        System.out.println("Data Type: " + "Random Order");
        System.out.println("Sort: Merge Sort");
        System.out.println("Number of Movements: " + Main.movement);
        System.out.println("Number of Comparisons: " + Main.comparison);
        System.out.println("Total time: " + mergeSortExecutionTime + " ns" + "\n");

        //Quicksort
        int[] randomArray3 = generateRandomArray(50000,0,50000);


        Main.comparison = 0;

        Main.movement = 0;

        partition(randomArray3, 0, randomArray3.length-1);
        quickSort(randomArray3, 0, randomArray3.length-1);

        System.out.print("Show Array: " + "\n");
        //print(randomArray3, randomArray3.length);
        System.out.println("\n" + "Input Size: " + randomArray3.length);
        System.out.println("Data Type: " + "Random Order");
        System.out.println("Sort: Quick Sort");
        System.out.println("Number of Movements: " + Main.movement);
        System.out.println("Number of Comparisons: " + Main.comparison);
        System.out.println("Total time: " + radixSortExecutionTime + " ns" + "\n");

        //Radix Sort
        int[] randomArray4 = generateRandomArray(50000,0000,50000);
        //Arrays.sort(randomArray4);
       //reverseOrder(randomArray4);
        //int n3 = randomArray4.length/2;
        //Arrays.sort(randomArray4, 0,n3 );



        Main.comparison = 0;

        Main.movement = 0;

        radixsort(randomArray4, randomArray4.length);

        System.out.print("Show Array: " + "\n");
        //print(randomArray4, randomArray4.length);
        System.out.println("\n" + "Input Size: " + randomArray4.length);
        System.out.println("Data Type: " + "Random Order");
        System.out.println("Sort: Radix Sort");
        System.out.println("Number of Movements: " + Main.movement);
        System.out.println("Number of Comparisons: " + Main.comparison);
        System.out.println("Total time: " + radixSortExecutionTime + " ns" + "\n");

        //Heap Sort
        int[] randomArray5 = generateRandomArray(50000,0,50000);
        //Arrays.sort(randomArray5);
        //reverseOrder(randomArray5);
       // int n4 = randomArray5.length/2;
        //Arrays.sort(randomArray5, 0,n4 );


        Main.comparison = 0;

        Main.movement = 0;

        heapSort(randomArray5, randomArray5.length);
        System.out.print("Show Array: " + "\n");
        //print(randomArray5, randomArray5.length);
        System.out.println("\n" + "Input Size: " + randomArray5.length);
        System.out.println("Data Type: " + "Random Order");
        System.out.println("Sort: Heap Sort");
        System.out.println("Number of Movements: " + Main.movement);
        System.out.println("Number of Comparisons: " + Main.comparison);
        System.out.println("Total time: " + heapSortExecutionTime + " ns" + "\n");

        System.out.print("Test case for Data Type: Random Order. " + "\n" + "Test cases of all other Data Types and Algorithms recorded in report");
    }

}
