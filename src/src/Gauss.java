package src;


import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
public class Gauss {
    public double[][] matrixData;
    private double precision;
    private int n;

    public int getResult() {
        return result;
    }

    private int result;

    public void init(String filename) throws FileNotFoundException {
        int countOfNumbersAfterZero;
        double[][] matrix;
        File file = new File(filename); //����
        try (Scanner scan = new Scanner(file)) { //�������
            n = scan.nextInt();
            countOfNumbersAfterZero = scan.nextInt();

            precision = Math.pow(10, -(countOfNumbersAfterZero + 1)); //����� � �����
            matrixData = new double[n][];
            for (int i = 0; i < n; i++) { //������ �������
                matrixData[i] = new double[n + 1];
                for (int j = 0; j < n + 1; j++) {
                    matrixData[i][j] = scan.nextDouble();
                }
            }
        }
    }


    public double[] backtrace() { // �������� �����������
        //matrixData.length - ���������� �������. � ��� ��� ���� �������������� �������
        //��� ������� ��� ��� matrixData.length - ������� ���������� �����
        double[] answerData = new double[matrixData.length];

        for (int k = answerData.length - 1; k >= 0; k--) {
            for (int j = k + 1; j < answerData.length; j++) { // ��� ����. ������ �������� �� ���������������
                // ������������ � ������� ��������� � �������� �� b
                matrixData[k][matrixData.length] -= answerData[j] * matrixData[k][j];
            }
            answerData[k] = matrixData[k][matrixData.length] / matrixData[k][k];
        }
        return answerData;
    }

    protected void checkZeroSolutions() {
        int len = matrixData.length;
        if (isZero(matrixData[len - 1][len - 1]) && !isZero(matrixData[len - 1][len])) {//������ � ������� ������� � � ������ �����������
            result = 2;
        }
    }

    // 1 2 3 3 |1
    // 1 5 4 5 |2
    // 1 0 0 5 |3
    // 0 0 0 0 |0 <- �������� ��������� ���� �����
    private void checkInftySolutions() {
        int len = matrixData.length;
        if (isZero(matrixData[len - 1][len - 1]) && isZero(matrixData[len - 1][len])) {
            result = 3; // infty solutions
        }
    }

    public int triangularForm() { // ���������� � ������������ ����
        for (int i = 0; i < matrixData.length; i++) { // ������ ����
            // zerocoeff - ������ ����
            if (!zeroCoeff(i)) { // ���� ������� ������� true, �� ������ ���������, ���� ������. ����� ����������
                // ����������
                return 0;//
            }
            eliminateDiagonal(i); // ��������� ����. �����,
        }
        return 1;
    }


//    1 0 0
//    2 1 1
//    3 0 0

    private void eliminateDiagonal(int diagonalIndex) { // �������� ���� ������� ��� ��������� ���������.
        for (int k = diagonalIndex + 1; k < matrixData.length; k++) { // ��������� ����������� � k-� ���������, �������
            // �� ����������
             // ��� m - ������� � �����
            //System.out.println("d = " + diagonalIndex + "; k = " + k + "; m = " + m);
            matrixData[k][diagonalIndex] = 0; // matrixData[k][i] = matrixData[k][i] -
            // relationCoefficient*matrixData[i][i]
//            matrixData[k][diagonalIndex] -= m * matrixData[diagonalIndex][diagonalIndex];
            coeffCount(matrixData, diagonalIndex, k);
            //private void coeffCount(double matrixData[][], double m) {
            //for (int j = diagonalIndex + 1; j < matrixData.length ; j++) //
            //matrixData[k][j] = matrixData[k][j] - matrixData[diagonalIndex][j] * m; // �������� ������� !! � ���. �����

            //matrixData[k][matrixData.length] -= matrixData[diagonalIndex][matrixData.length]  * m; // �������� ��������� ������
        }
    }
    private void coeffCount(double matrixData[][], int diagonalIndex, int k) {
    	double m = matrixData[k][diagonalIndex] / matrixData[diagonalIndex][diagonalIndex];
        for (int j = diagonalIndex + 1; j < matrixData.length; j++) {
            matrixData[k][j] -= matrixData[diagonalIndex][j] * m;
        }
    }


    private boolean zeroCoeff(int diagonalIndex) { // ���� ������ �������, � ������� �����. �� ����� 0, ������ �������,
        // ����������� ���-�� ������������ (a11 !=0)
        for (int numberToSwap = diagonalIndex; numberToSwap < matrixData.length; numberToSwap++) {
            if (!isZero(matrixData[numberToSwap][diagonalIndex])) { //!! �� �����. � 0 (�������� - �� �����/���������)
                swapEquations(numberToSwap, diagonalIndex);
                return true;
            }
            // System.out.println(matrixData[equationNumberToSwap][indexOfDiagonalCoef]);
        }
        return false; // ������� �����, ���������� ����������
    }

    private void swapEquations(int numberToSwap, int diagonalIndex) { // ������ ������� ���������
        double[] temp = matrixData[numberToSwap];
        matrixData[numberToSwap] = matrixData[diagonalIndex];
        matrixData[diagonalIndex] = temp;
    }

    public boolean isZero(double value) { //��������� �
        return Math.abs(value) <= this.precision;
    }

    public double[][] getMatrixData() {
        return matrixData;
    }

    public int getN() {
        return n;
    }

    public int solve() {
        if (triangularForm()) { // ���� ������� � ������������ ����, �� ���� n �������
            result = 1;
        } else { // ������� �� ������� ������������� � ������������ ����, det = 0;
            result = 0;
        }
        checkInftySolutions();
        if (result ==3) {
        	System.out.println("���������� ����� �������.");
        }
        checkZeroSolutions();
        if (result == 2) {
        	System.out.println("��� �������.");
        }
    }

}