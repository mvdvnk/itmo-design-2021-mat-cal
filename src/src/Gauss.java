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
        File file = new File(filename); //файл
        try (Scanner scan = new Scanner(file)) { //сканнер
            n = scan.nextInt();
            countOfNumbersAfterZero = scan.nextInt();

            precision = Math.pow(10, -(countOfNumbersAfterZero + 1)); //лучше с файла
            matrixData = new double[n][];
            for (int i = 0; i < n; i++) { //читаем матрицу
                matrixData[i] = new double[n + 1];
                for (int j = 0; j < n + 1; j++) {
                    matrixData[i][j] = scan.nextDouble();
                }
            }
        }
    }


    public double[] backtrace() { // обратная подстановка
        //matrixData.length - количество строчек. У нас ещё есть дополнительный столбец
        //под номером как раз matrixData.length - столбец свободного члена
        double[] answerData = new double[matrixData.length];

        for (int k = answerData.length - 1; k >= 0; k--) {
            for (int j = k + 1; j < answerData.length; j++) { // все найд. ответы умножаем на соответствующие
                // коэффициенты в текущем уравнении и вычитаем из b
                matrixData[k][matrixData.length] -= answerData[j] * matrixData[k][j];
            }
            answerData[k] = matrixData[k][matrixData.length] / matrixData[k][k];
        }
        return answerData;
    }

    protected void checkZeroSolutions() {
        int len = matrixData.length;
        if (isZero(matrixData[len - 1][len - 1]) && !isZero(matrixData[len - 1][len])) {//строка с которой работаю и с какими отрабатываю
            result = 2;
        }
    }

    // 1 2 3 3 |1
    // 1 5 4 5 |2
    // 1 0 0 5 |3
    // 0 0 0 0 |0 <- проверка последних двух нулей
    private void checkInftySolutions() {
        int len = matrixData.length;
        if (isZero(matrixData[len - 1][len - 1]) && isZero(matrixData[len - 1][len])) {
            result = 3; // infty solutions
        }
    }

    public int triangularForm() { // приведение к треугольному виду
        for (int i = 0; i < matrixData.length; i++) { // первый цикл
            // zerocoeff - второй цикл
            if (!zeroCoeff(i)) { // если функция вернула true, то найден ненулевой, идем дальше. Иначе приведение
                // невозможно
                return 0;//
            }
            eliminateDiagonal(i); // исключаем диаг. коэфф,
        }
        return 1;
    }


//    1 0 0
//    2 1 1
//    3 0 0

    private void eliminateDiagonal(int diagonalIndex) { // зануляем весь столбец под ненулевым элементом.
        for (int k = diagonalIndex + 1; k < matrixData.length; k++) { // Исключаем коэффициент в k-м уравнении, начиная
            // со следующего
             // это m - занести в метод
            //System.out.println("d = " + diagonalIndex + "; k = " + k + "; m = " + m);
            matrixData[k][diagonalIndex] = 0; // matrixData[k][i] = matrixData[k][i] -
            // relationCoefficient*matrixData[i][i]
//            matrixData[k][diagonalIndex] -= m * matrixData[diagonalIndex][diagonalIndex];
            coeffCount(matrixData, diagonalIndex, k);
            //private void coeffCount(double matrixData[][], double m) {
            //for (int j = diagonalIndex + 1; j < matrixData.length ; j++) //
            //matrixData[k][j] = matrixData[k][j] - matrixData[diagonalIndex][j] * m; // пересчёт строчки !! в отд. метод

            //matrixData[k][matrixData.length] -= matrixData[diagonalIndex][matrixData.length]  * m; // пересчёт свободных членов
        }
    }
    private void coeffCount(double matrixData[][], int diagonalIndex, int k) {
    	double m = matrixData[k][diagonalIndex] / matrixData[diagonalIndex][diagonalIndex];
        for (int j = diagonalIndex + 1; j < matrixData.length; j++) {
            matrixData[k][j] -= matrixData[diagonalIndex][j] * m;
        }
    }


    private boolean zeroCoeff(int diagonalIndex) { // ищем первую строчку, в которой коэфф. не равен 0, меняем местами,
        // увеличиваем кол-во перестановок (a11 !=0)
        for (int numberToSwap = diagonalIndex; numberToSwap < matrixData.length; numberToSwap++) {
            if (!isZero(matrixData[numberToSwap][diagonalIndex])) { //!! не сравн. с 0 (точность - из файла/константа)
                swapEquations(numberToSwap, diagonalIndex);
                return true;
            }
            // System.out.println(matrixData[equationNumberToSwap][indexOfDiagonalCoef]);
        }
        return false; // столбец нулей, приведение невозможно
    }

    private void swapEquations(int numberToSwap, int diagonalIndex) { // меняем местами уравнения
        double[] temp = matrixData[numberToSwap];
        matrixData[numberToSwap] = matrixData[diagonalIndex];
        matrixData[diagonalIndex] = temp;
    }

    public boolean isZero(double value) { //сравнение с
        return Math.abs(value) <= this.precision;
    }

    public double[][] getMatrixData() {
        return matrixData;
    }

    public int getN() {
        return n;
    }

    public int solve() {
        if (triangularForm()) { // если привели к треугольному виду, то есть n решений
            result = 1;
        } else { // Матрицу не удалось преобразовать к треугольному виду, det = 0;
            result = 0;
        }
        checkInftySolutions();
        if (result ==3) {
        	System.out.println("Бесконечно много решений.");
        }
        checkZeroSolutions();
        if (result == 2) {
        	System.out.println("Нет решений.");
        }
    }

}