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
	        if (!checkDraw(gameField,level)) break;
	    doUserMove(gameField,scanner,level);
        System.out.println();
            if (!checkDraw(gameField,level)) break;
        doAIMove(gameField,level);}
        while (checkDraw(gameField,level));


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
        System.out.println("У вас ничья!");
        return false;
    }

}
