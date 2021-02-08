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
            doUserMove(gameField, scanner, level);
            if (checkNextMove(gameField, level, 'X', "Поздравляем Вы выйграли!")) break;
            System.out.println();
            doAIMove(gameField, level);
            if (checkNextMove(gameField, level, 'O', "Вы проиграли!")) break;
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

    public static void doUserMove (char[][] gameField, Scanner scanner, byte size) {
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
        gameField[X][Y] = 'X';

        drawGameField(gameField,size);

    }
    public static void doAIMove(char[][] gameField,byte size){
        int X,Y;
        Random randNum = new Random();
        do {
            X = randNum.nextInt(gameField.length);
            Y = randNum.nextInt(gameField.length);
        } while (checkMove(gameField,X,Y));
        gameField[X][Y] = 'O';
        drawGameField(gameField, size);
    }

    public static boolean checkCoordinate (int coordinate, byte size){
        return (coordinate >= size || coordinate<0);
    }
    public static boolean checkMove (char[][] gameField,int X,int Y){
        return gameField[X][Y] != '-';
    }

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

    public static boolean checkWin (char[][] gameField,char sign,String msg){
        int countX=0, countY=0;
        int countA=0,countB=0;
        for (int i = 0; i<gameField.length;i++){
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
            if (gameField[i][i]==sign) {
                countA++;
            }
            if (gameField[gameField.length-1-i][i]==sign){
                countB++;
            }
            if (countA == gameField.length || countB==gameField.length){
                System.out.println(msg);
                return true;
            }
        }

        for (int j = 0; j<gameField.length;j++) {
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

}
