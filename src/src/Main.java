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
        int result = gauss.triangularForm();
        if (result == 1) {
        	System.out.println("Приведена к треугольному виду");
        }
        else {
        	System.out.println("Не удалось привести к треугольному виду. Матрица вырождена");
        
        }
        
        gauss.solve();
        getResult(gauss);
    }

    public static void getResult(Gauss gauss) {
        int gaussResult = gauss.getResult();
        switch (gaussResult) {
            case 0: {
                System.out.println("Не удалось привести к треугольному виду. Матрица вырождена");
            }
            break;
            case 1: {
                System.out.println("Треугольная матрица:");
                Main.printMatrix(gauss.getMatrixData());
                double[] answer = gauss.backtrace();//ответ
                System.out.println("Ответ: ");
                System.out.println(" ");
                for (int i = 0; i < gauss.getN(); i++)
                    System.out.printf("%15.6E\n", answer[i]);
                System.out.println(" ");
            }
            break;
            case 2: {
                System.out.println("Нет решений.");
            }
            break;
            case 3: {
                System.out.println("Бесконечно много решений.");
            }
            break;
        }
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