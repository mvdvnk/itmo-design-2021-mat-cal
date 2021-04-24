package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {//����������� ������, �� ������� ����� print ���. ����� ����� �������, ����� ��������� � ����. ����
        //������� �� ����� ������ �����, ���� ����� ����� ����-��, �� ������ �����, ���� ���, �� ������ ����� ����� ��. ���������.
        Gauss gauss = new Gauss();
        try{
            gauss.init("src/input.txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!!!");
        }

        System.out.println("�������:");
        printMatrix(gauss.matrixData); //����� ������� � ��������� �����.
        int result = gauss.triangularForm();
        if (result == 1) {
        	System.out.println("��������� � ������������ ����");
        }
        else {
        	System.out.println("�� ������� �������� � ������������ ����. ������� ���������");
        
        }
        
        gauss.solve();
        getResult(gauss);
    }

    public static void getResult(Gauss gauss) {
        int gaussResult = gauss.getResult();
        switch (gaussResult) {
            case 0: {
                System.out.println("�� ������� �������� � ������������ ����. ������� ���������");
            }
            break;
            case 1: {
                System.out.println("����������� �������:");
                Main.printMatrix(gauss.getMatrixData());
                double[] answer = gauss.backtrace();//�����
                System.out.println("�����: ");
                System.out.println(" ");
                for (int i = 0; i < gauss.getN(); i++)
                    System.out.printf("%15.6E\n", answer[i]);
                System.out.println(" ");
            }
            break;
            case 2: {
                System.out.println("��� �������.");
            }
            break;
            case 3: {
                System.out.println("���������� ����� �������.");
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