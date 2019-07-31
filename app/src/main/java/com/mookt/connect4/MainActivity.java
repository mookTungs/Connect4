package com.mookt.connect4;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private final int P1 = 1; //player 1 = yellow
    private final int P2 = 2; //player 2 = red
    private int turn;

    public Cell[][] boardView;
    public GameBoard gameBoard;
    public ImageView playerTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = new GameBoard();
        initBoard();
        turn = P1;
        playerTurn = findViewById(R.id.playerTurn);
        playerTurn.setImageResource(R.drawable.ic_yellow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.newGame){
            reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void reset(){
        for(int y = 0; y < GameBoard.height; y++){
            for(int x = 0; x < GameBoard.width; x++){
                boardView[y][x].setImageResource(R.drawable.ic_emptycell);
                boardView[y][x].setClickable(true);
                turn = P1;
                playerTurn.setImageResource(R.drawable.ic_yellow);
                gameBoard.reset();
            }
        }
    }

    public void initBoard(){
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        boardView = new Cell[GameBoard.height][GameBoard.width];

        for(int y = 0; y < GameBoard.height; y++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for(int x = 0; x < GameBoard.width; x++){
                Cell cell = new Cell(this);
                cell.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                cell.setImageResource(R.drawable.ic_emptycell);
                cell.x = x;
                cell.y = y;

                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickCell((Cell) view);
                    }
                });

                boardView[y][x] = cell;
                tableRow.addView(cell);
            }

            tableLayout.addView(tableRow);
        }
    }

    public void onClickCell(Cell cell){
        int color;
        if(turn == P1){
            color = R.drawable.ic_yellowcell;
        }else {
            color = R.drawable.ic_redcell;
        }

        int dropped = gameBoard.yDropped(cell.x, turn);
        if(dropped != -1) {
            boardView[dropped][cell.x].setImageResource(color);
            gameBoard.updateBoard(cell.x, dropped, turn);

            int over = gameBoard.isGameOver(cell.x, dropped, turn);
            if(over != 0){
                gameOver(over);
            }else if(turn == P1){
                turn = P2;
                playerTurn.setImageResource(R.drawable.ic_red);
            }else{
                turn = P1;
                playerTurn.setImageResource(R.drawable.ic_yellow);
            }
        }
    }

    //-1 == draw, 1 == someone won
    public void gameOver(int tied){
        String s;
        if(tied == -1){
            s = "It's a tied";
        }else if(turn == P1){
            s = "Yellow Win!";
        }else{
            s = "Red Win!";
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Connect 4");
        alertDialog.setMessage(s);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

        for(int y = 0; y < GameBoard.height; y++){
            for(int x =0; x < GameBoard.width; x++){
                boardView[y][x].setClickable(false);
            }
        }
    }



}
