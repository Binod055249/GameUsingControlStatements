package com.example.gameusingcontrolstatements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private static final SecureRandom secureRandomNumbers=new SecureRandom();

    private enum Status { NOTSTARTEDYET, PROCEED, WON, LOST }

    private static final int TIGER_CLAWS=2;
      private static final int TREE=3;
    private static final int CEVEN=7;
    private static final int WE_LEVEN=11;
    private static final int PANTHER=12;

    TextView txtCalculations,txtGameStatus;
    ImageView imgDice;
    Button btnRestartTheGame;
    String oldTxtCalculationValue="";
    boolean firstTime=true;
    Status gameStatus=Status.NOTSTARTEDYET;
    int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCalculations=findViewById(R.id.txtCalculations);
        imgDice=findViewById(R.id.imgDice);
        btnRestartTheGame=findViewById(R.id.btnRestartTheGame);
        txtGameStatus=findViewById(R.id.txtGameStatus);


        makeBtnRestartInvisibe();

        txtGameStatus.setText("");
        txtCalculations.setText("");

        imgDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstTime){
                    txtGameStatus.setText("");
                    txtCalculations.setText("");
                    firstTime=false;
                }
                if(gameStatus==Status.NOTSTARTEDYET){

                    int diceSum=letsRollTheDice();

                    oldTxtCalculationValue=txtCalculations.getText().toString();
                    points=0;

                    switch (diceSum){

                        case CEVEN:
                        case WE_LEVEN:
                            gameStatus=Status.WON;
                            txtGameStatus.setText("You Win");
                            makeImgDiceInvisible();
                            makeBtnRestartVisible();
                            break;

                        case TIGER_CLAWS:
                        case TREE:
                        case PANTHER:
                            gameStatus=Status.LOST;
                            txtGameStatus.setText("You Lost");
                            makeImgDiceInvisible();
                            makeBtnRestartVisible();
                            break;

                        case 4:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                            gameStatus=Status.PROCEED;
                            points=diceSum;
                            txtCalculations.setText(oldTxtCalculationValue+" Your Point is "+points+"\n");
                            txtGameStatus.setText("Continue The game");
                            oldTxtCalculationValue="Your Points is: "+points+"\n";
                           break;
                    }
                    return;
                }
                if(gameStatus==Status.PROCEED){

                    int diceSum=letsRollTheDice();
                    if(diceSum==points){
                         gameStatus=Status.WON;
                         txtGameStatus.setText("You won");
                         makeImgDiceInvisible();
                         makeBtnRestartVisible();
                    }else if(diceSum==CEVEN){
                        gameStatus=Status.LOST;
                        txtGameStatus.setText("You Lost");
                        makeImgDiceInvisible();
                        makeBtnRestartVisible();
                    }
                }
            }
        });

        btnRestartTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameStatus=Status.NOTSTARTEDYET;
                txtGameStatus.setText("");
                txtCalculations.setText("");
               oldTxtCalculationValue="";
               makeImgDiceVisible();
               makeBtnRestartInvisibe();
            }
        });

    }

    private void makeImgDiceInvisible(){

        imgDice.setVisibility(View.INVISIBLE);
    }

    private void makeBtnRestartInvisibe(){

        btnRestartTheGame.setVisibility(View.INVISIBLE);
    }

    private void makeImgDiceVisible(){

        imgDice.setVisibility(View.VISIBLE);
    }

    private void makeBtnRestartVisible(){

        btnRestartTheGame.setVisibility(View.VISIBLE);
    }

    private int letsRollTheDice(){

        int randDie1=1+secureRandomNumbers.nextInt(6);
        int randDie2=1+1+secureRandomNumbers.nextInt(6);
        int sum=randDie1+randDie2;

        txtCalculations.setText(String.format(oldTxtCalculationValue+" You rolled %d + %d = %d%n",
                                                randDie1,randDie2,sum));
        return sum;
    }

}