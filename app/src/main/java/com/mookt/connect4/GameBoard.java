package com.mookt.connect4;

public class GameBoard {
    public static final int width = 7;
    public static final int height = 6;
    private int[][] board;
    private int totalEmpty;

    public GameBoard(){
        board = new int[height][width];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                board[y][x] = 0;
            }
        }
        totalEmpty = width * height;
    }

    public int yDropped(int xCoor, int player){
        int dropped = -1;
        for(int y = height - 1; y >= 0; y--){
            if(board[y][xCoor] == 0){
                board[y][xCoor] = player;
                dropped = y;
                break;
            }
        }
        return dropped;
    }

    public void updateBoard(int x, int y, int player){
        board[y][x] = player;
        totalEmpty--;
    }

    public void reset(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                board[y][x] = 0;
            }
        }
        totalEmpty = width * height;
    }

    public boolean isGameOver(int x, int y, int player){
        if(checkHorizontal(y, player) || checkVertical(x, player) || checkLeftDiagonal(x, y, player) || checkRightDiagonal(x, y, player)){
            return true;
        }

        return false;
    }

    public boolean checkHorizontal(int y, int player){
        int consecutive = 0;

        for(int x = 0; x < width; x++){
            if (board[y][x] == player) {
                consecutive++;
            } else {
                consecutive = 0;
            }
            if(consecutive == 4){
                return true;
            }
        }
        return false;
    }

    public boolean checkVertical(int x, int player){
        int consecutive = 0;

        for(int y = 0; y < height; y++){
            if(board[y][x] == player){
                consecutive++;
            }else{
                consecutive = 0;
            }
            if(consecutive == 4){
                return true;
            }
        }
        return false;
    }

    public boolean checkLeftDiagonal(int x, int y, int player){
        int tempX = 0;
        int tempY = 0;
        int consecutive = 0;

        if(x > y){
            tempX = x - y;
            tempY = y - y;
        }else if(x < y){
            tempX = x - x;
            tempY = y - x;
        }

        while(tempX < width && tempY < height){
            if(board[tempY][tempX] == player){
                consecutive++;
            }else{
                consecutive = 0;
            }
            if(consecutive == 4){
                return true;
            }
            tempX++;
            tempY++;
        }

        return false;
    }

    public boolean checkRightDiagonal(int x, int y, int player){
        int tempX;
        int tempY;
        int consecutive = 0;

        if(x + y > 6){
            tempX = 6;
            tempY = x + y - 6;
        }else{
            tempX = y + x;
            tempY = y - y;
        }

        while(tempX >= 0 && tempY < height){
            if(board[tempY][tempX] == player){
                consecutive++;
            }else{
                consecutive = 0;
            }
            if(consecutive == 4){
                return true;
            }
            tempX--;
            tempY++;
        }
        return false;
    }

}
