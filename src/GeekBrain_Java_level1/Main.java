package GeekBrain_Java_level1;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        byte level = 3;
        Scanner scanner = new Scanner(System.in);
        char[][] gameField = new char[level][level];
        fillGameField(gameField,level);
	    drawGameField(gameField,level);
	    do {
            System.out.println("Ход игрока 1");
            doUserMove(gameField, scanner, level,'X');
            if (checkNextMove(gameField, level, 'X', "Поздравляем выйграл игрок 1!")) break;
            System.out.println();
            System.out.println("Ход компьютера");
            doAIMove(gameField, level,'O');
            if (checkNextMove(gameField, level, 'O', "Вы проиграли!")) break;
            /*  System.out.println("Ход игрока 2");
            doUserMove(gameField, scanner, level,'Y');
            if (checkNextMove(gameField, level, 'Y', "Поздравляем выйграл игрок 2!")) break;
            System.out.println("Ход игрока 3");
            doUserMove(gameField, scanner, level,'Z');
            if (checkNextMove(gameField, level, 'Z', "Поздравляем выйграл игрок 3!")) break;
            */
        }while (checkDraw(gameField,level));


    }

    public static void fillGameField (char[][] field, byte size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = '-';
            }
        }
    }
    public static void drawGameField (char[][] gameField, byte size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void doUserMove (char[][] gameField, Scanner scanner, byte size,char sign) {
        int X, Y;
        do{
            do {
                System.out.println("Введите координату X от 0 до " + size);
                X = scanner.nextInt() - 1;
            } while (checkCoordinate(X, size));
            do {
                System.out.println("Введите координату Y от 0 до " + size);
                Y = scanner.nextInt() - 1;
            } while (checkCoordinate(Y, size));
        } while (checkMove(gameField,X,Y));
        gameField[X][Y] = sign;

        drawGameField(gameField,size);

    }
    public static void doAIMove(char[][] gameField,byte size,char sign){
        int X=0,Y=0;
        int nextX = checkUserWinNextMoveX(gameField,'X');
        int nextY = checkUserWinNextMoveY(gameField,'X');
        boolean nextMainDiag = checkUserWinNextMoveMainDiag(gameField,'X');
        boolean nextSecDiag = checkUserWinNextMoveSecDiag(gameField,'X');
        Random randNum = new Random();
        do {
            if (nextX != -1){                                   //Если возможен выйгрыш игрока по одной из строк
                X = nextX;
                Y = randNum.nextInt(gameField.length);
            }
            else if (nextY != -1){                              //Если возможен выйгрыш игрока по одному из столбцов
                X = randNum.nextInt(gameField.length);
                Y = nextY;
            }
            else if (nextMainDiag) {                                 // Если возможен выйгрыш игрока по главной диагонали
                for (int i = 0; i < gameField.length; i++){
                    if (gameField[i][i]=='-'){
                        X=i;
                        Y=i;
                    }
                }
            }
            else if (nextSecDiag){                              // Если возможен выйгрыш игрока по второстепенной диагонали
                for (int i = 0; i < gameField.length; i++){
                    if (gameField[gameField.length-1-i][i]=='-'){
                        X=i;
                        Y=i;
                    }
                }
            }
            else                                                // Если у игрока нет возможности выйграть следующим ходом
            {
            X = randNum.nextInt(gameField.length);
            Y = randNum.nextInt(gameField.length);
            }
        } while (checkMove(gameField,X,Y));
        gameField[X][Y] = sign;
        drawGameField(gameField, size);
    }
    // Проверка на принадлежность координаты введенной пользователем игровому полю
    public static boolean checkCoordinate (int coordinate, byte size){
        return (coordinate >= size || coordinate<0);
    }
    // Проверка на не занятость той ячейки, которую выбрал пользователь или компьютер
    public static boolean checkMove (char[][] gameField,int X,int Y){
        return gameField[X][Y] != '-';
    }
    // Проверка на ничью
    public static boolean checkDraw (char[][] gameField,int size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameField[i][j] =='-'){
                    return true;
                }
            }
        }
        return false;
    }
    // Проверка на победу игрока или компьютера
    public static boolean checkWin (char[][] gameField,char sign,String msg){
        int countX=0, countY=0;
        int countA=0,countB=0;
        for (int i = 0; i<gameField.length;i++){                            //Проверка на победу по одной из строк
            for (int j = 0;j<gameField[i].length; j++){
                if (gameField[i][j]==sign){
                    countX++;
                }
                if (countX == gameField.length) {
                    System.out.println(msg);
                    return true;
                }
            }
            countX = 0;
            if (gameField[i][i]==sign) {                                    // Проверка на победу по главной диагонали
                countA++;
            }
            if (gameField[gameField.length-1-i][i]==sign){                  // Проверка на победу по второстепенной диагонали
                countB++;
            }
            if (countA == gameField.length || countB==gameField.length){
                System.out.println(msg);
                return true;
            }
        }

        for (int j = 0; j<gameField.length;j++) {                           // Проверка на победу по одному из столбцов
            for (int i = 0; i < gameField.length; i++) {
                if (gameField[i][j] == sign) {
                    countY++;
                }
                if (countY == gameField.length) {
                    System.out.println(msg);
                    return true;
                }
            }
            countY = 0;
        }
        return false;
    }
    // Проверка на необходимость следующего хода
    public static boolean checkNextMove (char[][] gameField,int size,char sign, String msg){
        if (checkWin(gameField,sign,msg)){
            return true;
        }
        if (!checkDraw(gameField,size)){
            System.out.println("У вас ничья!");
            return true;
        }
        return false;
    }
    // Метод определяет, что на следующем ходе возможна победа игрока по одной из строк
    public static int checkUserWinNextMoveX (char[][] gameField,char sign) {
        int countX = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j] == sign) {
                    countX++;
                } else if (gameField[i][j] == 'O') {
                    countX--;
                }
            }
            if (countX == gameField.length - 1) {
                return i;
            }
            countX = 0;
        }
        return -1;
    }
    // Метод определяет что на следующем ходе возможна победа игрока по одному из столбцов
    public static int checkUserWinNextMoveY (char[][] gameField,char sign) {
        int countY = 0;
        for (int j = 0; j<gameField.length;j++) {
            for (int i = 0; i < gameField.length; i++) {
                if (gameField[i][j] == sign) {
                    countY++;
                } else if (gameField[i][j] == 'O'){
                    countY--;
                }

            }
            if (countY == gameField.length-1) {
                return j;
            }
            countY = 0;
        }
        return -1;
    }
    // Метод определяет что на следующем ходе возможна победа игрока по главной диагонали
    public static boolean checkUserWinNextMoveMainDiag (char[][] gameField,char sign) {
        int countA = 0;
        for (int i = 0; i < gameField.length; i++) {
            if (gameField[i][i] == sign) {
                countA++;
            }
            else if (gameField[i][i] == 'O') {
                countA--;
            }
        }
        return (countA == gameField.length - 1);
    }
    // Метод определяет, что на следующем ходе возможна победа игрока по второстепенной диагонали
    public static boolean checkUserWinNextMoveSecDiag (char[][] gameField,char sign) {
        int countB = 0;
        for (int i = 0; i < gameField.length; i++) {
            if (gameField[gameField.length - 1 - i][i] == sign) {
                countB++;
            } else if (gameField[i][i] == 'O') {
                countB--;
            }
        }
        return (countB == gameField.length - 1);
    }

}
