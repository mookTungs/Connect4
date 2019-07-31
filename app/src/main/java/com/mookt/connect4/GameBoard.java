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

    //return 1 if game over, 0 if not, -1 if draw
    public int isGameOver(int x, int y, int player){
        if(checkHorizontal(y, player) || checkVertical(x, player) || checkLeftDiagonal(x, y, player) || checkRightDiagonal(x, y, player)){
            return 1;
        }else if(totalEmpty == 0){
            return -1;
        }

        return 0;
    }

    private boolean checkHorizontal(int y, int player){
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

    private boolean checkVertical(int x, int player){
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

    private boolean checkLeftDiagonal(int x, int y, int player){
        int tempX = 0;
        int tempY = 0;
        int consecutive = 0;

        if(x > y){
            tempX = x - y;
            tempY = 0;
        }else if(x < y){
            tempX = 0;
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

    private boolean checkRightDiagonal(int x, int y, int player){
        int tempX;
        int tempY;
        int consecutive = 0;

        if(x + y > 6){
            tempX = 6;
            tempY = x + y - 6;
        }else{
            tempX = y + x;
            tempY = 0;
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
