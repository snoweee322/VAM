import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void makeDeltas(int[][] matrix, int[] deltaI, int[] deltaJ) {

        for(int i = 0; i < deltaI.length; i++) { // deltas for each row in the table
            if(deltaI[i] != -9999) deltaI[i] = delta(matrix[i]);
        }
        for(int j = 0; j < deltaJ.length; j++) {
            int lambdaJ = j;
            if(deltaJ[j] != -9999) deltaJ[j] = delta(IntStream.range(0, deltaI.length) // deltas for each column in the table
                    .map(i -> matrix[i][lambdaJ])
                    .toArray());
        }
    }

    public static void display(int[][] matrix, int rows, int cols) {

        for(int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
        }
    }

    public static int delta(int[] arr) { // finding delta from a row or column

        int[] temp = Arrays.copyOf(arr, arr.length);
        Arrays.sort(temp);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i < temp.length; i++) {
            arrayList.add(temp[i]);
        }
        arrayList.removeAll(Collections.singleton(0));
        if(arrayList.size() == 1)
            return 0;
        if(arrayList.isEmpty())
            return 0;
        return arrayList.get(1)-arrayList.get(0);
    }

    public static int lowestValueInArray(int[] arr) { // taking minimum from array

        int[] temp = Arrays.copyOf(arr, arr.length);
        Arrays.sort(temp);
        if(temp[0] == 0)
            return temp[1];
        return temp[0];
    }

    public static int lowestValueInArrayIndex(int[] arr) { // returning index of X or Y (depends on Max type)

        int minNum = 9999;
        int index = 9999;
        for(int i = 0; i < arr.length; i++) {
            if(minNum > arr[i] && arr[i] != 0) {
                minNum = arr[i];
                index = i;
            }
        }
        return index;
    }

    public static Max lowestValueByDelta(int[][] matrix, int[] deltaI, int[] deltaJ) {

        Max maxStore = new Max(0); // max for store
        maxStore.value = deltaI[0];
        maxStore.X = 0;
        for(int i = 0; i < deltaI.length; i++) { // among deltas by rows
            if(maxStore.value < deltaI[i] && deltaI[i] != 0) {
                maxStore.value = deltaI[i];
                maxStore.X = i;
            } else if(maxStore.value == deltaI[i]) {
                if(lowestValueInArray(matrix[i]) < lowestValueInArray(matrix[maxStore.X])) {
                    maxStore.value = deltaI[i];
                    maxStore.X = i;
                    maxStore.valueAtMatr = lowestValueInArray(matrix[i]);
                }
            }
        }

        Max maxConsumers = new Max(1);// max for consumers
        maxConsumers.value = deltaJ[0];
        maxConsumers.X = 0;
        maxConsumers.valueAtMatr = 9999;
        for(int j = 0; j < deltaJ.length; j++) { // among deltas by cols
            if(maxConsumers.value < deltaJ[j] && deltaJ[j] != 0) {
                maxConsumers.value = deltaJ[j];
                maxConsumers.X = j;
            } else if(maxConsumers.value == deltaJ[j]) {
                int lambdaJ = j;
                int lambdaMCI = maxConsumers.X;
                if(lowestValueInArray(IntStream.range(0, deltaI.length).map(i -> matrix[i][lambdaJ]).toArray())
                        < lowestValueInArray(IntStream.range(0, deltaI.length).map(i -> matrix[i][lambdaMCI]).toArray())) {
                    maxConsumers.value = deltaJ[j];
                    maxConsumers.X = j;
                    maxConsumers.valueAtMatr = lowestValueInArray(IntStream.range(0, deltaI.length).map(i -> matrix[i][lambdaMCI]).toArray());
                }
            }
        }
        if(maxStore.value > maxConsumers.value) {
            return maxStore;
        }
        else if(maxStore.value == maxConsumers.value) {
            if(maxStore.valueAtMatr <= maxConsumers.valueAtMatr)
                return maxStore;
            else
                return maxConsumers;
        } else {
            return maxConsumers;
        }
    }

    public static void displayAll(int[][] matrix, int[] store, int[] consumers, int rows, int cols, int[] deltaI, int[] deltaJ, Max max) {

        System.out.print("\n\n\tTable:              A   D");
        for(int i = 0; i < rows; i++) {
            System.out.println("  ");
            for (int j = 0; j < cols; j++) {
                System.out.print("\t" + matrix[i][j] + " ");
            }
            System.out.print("\t" + store[i]);
            System.out.print("\t" + deltaI[i]);
        }
        System.out.println();
        System.out.print("B ");
        for(int i = 0; i < cols; i++)
            System.out.print("\t" + consumers[i] + " ");
        System.out.println();
        System.out.print("D ");
        for(int i = 0; i < cols; i++)
            System.out.print("\t" + deltaJ[i] + " ");
        //System.out.println("\nType: " + max.type + " Value: " + max.value + " Index: " + max.X);
    }

    public static void main(String[] args) throws IOException {

        int rows = 0;
        int cols = 0;
        FileReader fileReader = new FileReader(new File("src/main/resources/matrix.txt").getAbsolutePath());
        Scanner scanner = new Scanner(fileReader);
        String line = scanner.nextLine();
        int[] storeTemp = Arrays.stream(line.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        line = scanner.nextLine();
        int[] consumersTemp = Arrays.stream(line.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int storeSum = Arrays.stream(storeTemp).reduce(0, Integer::sum);
        int needsSum = Arrays.stream(consumersTemp).reduce(0, Integer::sum);
        rows = storeTemp.length;
        cols = consumersTemp.length;
        int rowsOld = rows; // vars for proper reading
        int colsOld = cols;

        if(storeSum < needsSum) {
            rows++; // add row (fictitious consumer)
        } else if (storeSum > needsSum) {
            cols++; // add column (fictitious consumer)
        }
        int[][] matrix = new int[rows][cols]; // matrix
        int[] store = Arrays.copyOf(storeTemp, rows); // storages
        int[] consumers = Arrays.copyOf(consumersTemp, cols); // consumers
        if(storeSum < needsSum) {
            store[store.length - 1] = needsSum - storeSum;
        } else if (storeSum > needsSum) {
            consumers[consumers.length - 1] = storeSum - needsSum;
        }

        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                matrix[i][j] = 0;
        for(int i = 0; i < rowsOld; i++)
            for (int j = 0; j < colsOld; j++)
                matrix[i][j] = scanner.nextInt();
        scanner.close();
        fileReader.close();


        int[] deltaI = new int[rows];
        int[] deltaJ = new int[cols];

        int[][] zMatrix = new int[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                zMatrix[i][j] = 0;

        Max max;
        int x;
        int y;

        int[][] matrixORIG = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
        int matrixIsZeroed = 0;
        while(true) {
            makeDeltas(matrix, deltaI, deltaJ);
            max = lowestValueByDelta(matrix, deltaI, deltaJ);

            if(max.type == 0) {
                y = max.X;
                x = lowestValueInArrayIndex(matrix[y]);
            }
            else {
                x = max.X;
                int finalY = x;
                y = lowestValueInArrayIndex(IntStream.range(0, deltaI.length).map(i -> matrix[i][finalY]).toArray());
            }
            //System.out.println("X: " + x + "; Y: " + y); // coordinates of min val
            displayAll(matrix, store, consumers, rows, cols, deltaI, deltaJ, max);

            for(int i = 0; i < rows; i++) // breaking condition
                for(int j = 0; j < cols; j++)
                    matrixIsZeroed += matrix[i][j];
            if(matrixIsZeroed == 0) break;

            if(store[y] < consumers[x]) { // supply < demand
                for(int i = 0; i < cols; i++) {
                    matrix[y][i] = 0;
                }
                zMatrix[y][x] = Math.min(consumers[x], store[y]);
                consumers[x] = consumers[x] - store[y];
                store[y] = 0;
                deltaI[y] = -9999;
                matrix[y][x] = 0;
            } else if(store[y] > consumers[x]) { // demand < supply
                for(int i = 0; i < rows; i++) {
                    if(i != y) matrix[i][x] = 0;
                }
                zMatrix[y][x] = Math.min(consumers[x], store[y]);
                store[y] = store[y] - consumers[x];
                consumers[x] = 0;
                deltaJ[x] = -9999;
                matrix[y][x] = 0;
            }
            else if(store[y] == consumers[x]) { // demand == supply
                for(int i = 0; i < rows; i++) {
                    if(i != y) matrix[i][x] = 0;
                }
                zMatrix[y][x] = Math.min(consumers[x], store[y]);
                store[y] = 0;
                consumers[x] = 0;
                deltaJ[x] = -9999;
                matrix[y][x] = 0;
            }
            matrixIsZeroed = 0;
        }
        int RESULT = 0;

        if(rowsOld < rows)
            for(int i = 0; i < rows; i++)
                zMatrix[rows-1][i] = consumers[i];
        if(colsOld < cols)
        for(int i = 0; i < store.length; i++)
            zMatrix[i][cols-1] = store[i];

        System.out.println("\n\n");
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(zMatrix[i][j] > 0) {
                    System.out.print("\t" + zMatrix[i][j] + "*" + matrixORIG[i][j]);
                    RESULT += (zMatrix[i][j] * matrixORIG[i][j]);
                } else {
                    System.out.print("\t" + zMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("RESULT: " + RESULT);
    }
}