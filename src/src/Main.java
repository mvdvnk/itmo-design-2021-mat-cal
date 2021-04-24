package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {//объявляется объект, на объекте метод print вып. файла вывод объекта, метод привеения к труг. виду
        //вернуть из этого метода число, если число равно чему-то, то вывожу ответ, если нет, то вывожу ответ через им. константы.
        Gauss gauss = new Gauss();
        try{
            gauss.init("src/input.txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!!!");
        }

        System.out.println("Матрица:");
        printMatrix(gauss.matrixData); //вывод матрицы и свободных коэфф.

        int result = gauss.solve();
        printResult(result);

        if(result == 0){
            System.out.println("Треугольный вид матрицы:");
            printMatrix(gauss.matrixData);

            System.out.println("Ответ:");
            for(double x : gauss.backtrace())
                System.out.printf("%15.6E\n", x);
        }
    }

    public static void printResult(int gaussResult) {
        String output = switch (gaussResult){
            case 0 -> "У матрицы есть решение!";
            case 1 -> "Матрица вырождена";
            case 2 -> "Нет решения";
            case 3 -> "Бесконечно много решений";
            default -> throw new IllegalStateException("Че ваще произошло. Результат: " + gaussResult);
        };
        System.out.println(output);
    }


    public static void printMatrix(double[][] matrix) {
        System.out.println(" ");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++)
                System.out.printf("%15.6E", matrix[row][col]);
            System.out.printf("|%15.6E", matrix[row][matrix.length]);
            System.out.println();
        }
        System.out.println(" ");
    }
}