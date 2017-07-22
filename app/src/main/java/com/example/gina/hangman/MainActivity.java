package com.example.gina.hangman;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;


public class MainActivity extends Activity {
   public String wordToGuess;
   public String currDisplay = new String("");

    public int incorrectGuessCount =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseWord();
        addMask();
        final TextView lblWordMask = (TextView) findViewById(R.id.lblMask);
        lblWordMask.setText(currDisplay);
        //Adding a listener to the text box
      EditText txtInput = (EditText) findViewById(R.id.txtInput);
        txtInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView lblDisplay = (TextView) findViewById(R.id.lblAnswer);


                EditText txtSameInputBox = (EditText) findViewById(R.id.txtInput);
                if (!txtSameInputBox.getText().toString().equals("")) {
                    String textIn = s.toString().toLowerCase();
                    lblDisplay.setText(textIn);
                    lblDisplay.setVisibility(View.VISIBLE);
                    int returnTest = 0;

                    returnTest = testLetter(Character.toString(textIn.charAt(textIn.length()-1)));
                    if (returnTest == -1) {
                      incorrectGuessCount++;
                        lblDisplay.setText("incorrect guess. try again");
                        ImageView imgHangman = (ImageView) findViewById(R.id.imgHangman);

                        switch(incorrectGuessCount)
                        {
                            case 1:
                                imgHangman.setImageResource(R.drawable.hangman2);
                                break;
                            case 2:
                                imgHangman.setImageResource(R.drawable.hangman3);
                                break;
                            case 3:
                                imgHangman.setImageResource(R.drawable.hangman4);
                                break;
                            case 4:
                                imgHangman.setImageResource(R.drawable.hangman5);
                                break;
                            case 5:
                                imgHangman.setImageResource(R.drawable.hangman6);
                                break;

                            case 6:
                                imgHangman.setImageResource(R.drawable.hangman);
                                lblDisplay.setText("you lose");
                                lblWordMask.setText(wordToGuess);
                                youLose();
                                break;

                        }
                        //need to add code here to put the body on hangman
                    } else {

                        lblDisplay.setText("correct guess! Great job!");
                        String displayText = changeDisplay(returnTest, textIn);
                        if (displayText.equals("win")) {
                            lblDisplay.setText("Win");
                            lblWordMask.setText(wordToGuess);
                            allFinished();

                        }
                        TextView lblMask = (TextView) findViewById(R.id.lblMask);
                        lblMask.setText(displayText);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });



       txtInput = (EditText) findViewById(R.id.txtInput);
        txtInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtSameInputBox = (EditText) findViewById(R.id.txtInput);
                if (!txtSameInputBox.getText().toString().equals("")) {
                    txtSameInputBox.setText("");
                }

            }
        });




    }

    public void chooseWord()
    {
        String str = "computer|radio|calculator|cooper|teacher|church|professor|geometry|president|subject|country|enviroment|classroom|animals|trees|month|flowers|puzzle|instrument|kitchen|language|saxophone|trombone|solution|service|software|peanut|security|chemistry|expert|website|agreement|support|worship|advanced|search|triathlon|immediately|encyclopedia|endurance|distance|nature|history|organization|international|championship|praise|popularity|thousand|feature|exercise|fitness|legendary|variation|equal|approximately|algebra|priority|physics|android|science|mathematics|lightning|dispersion|accelerator|detector|terminology|design|operation|foundation|application|prediction|reference|measurement|concept|perspective|overview|position|airplane|symmetry|dimension|toxic|calculus|illustration|classic|verification|water|unusual|resource|analysis|garden|comedy|screenplay|production|release|emphasis|director|multiply|vehicle|aircraft|experiment";
                        String[] temp;

                       /* delimiter */
                      String delimiter = "\\|";

                        /* given string will be split by the argument delimiter provided. */
                       temp = str.split(delimiter);


       		Random randomGenerator = new Random();

        		/* Generating random number */
        		int randomInt = randomGenerator.nextInt(temp.length);

                        wordToGuess = new String(temp[randomInt]);

    }

    public void addMask() {
    StringBuilder stringMask = new StringBuilder("");
        for(int i=0;i<wordToGuess.length();i++)
        {
            stringMask.append("*");

        }
        currDisplay=stringMask.toString();

    }
    public int testLetter(String s) {


        int returnNegOneIfNotFound=0;
        returnNegOneIfNotFound=wordToGuess.indexOf(s);

        return returnNegOneIfNotFound;
    }

    public String changeDisplay(int indexOfLetter, String textIn)
    {


        StringBuilder newString = new StringBuilder(currDisplay);



        String newStringTestIfDone = new String(currDisplay);
        for (int i=0; i<wordToGuess.length(); i++){
            if (wordToGuess.charAt(i) == textIn.charAt(textIn.length()-1)){
                  newString.setCharAt(i, textIn.charAt(textIn.length()-1));
            }
         }


                       // If all the dots have been filled, you win
        newStringTestIfDone = newString.toString();
          if (newStringTestIfDone.indexOf('*') == -1){
                 newStringTestIfDone="win";
                 return newStringTestIfDone;
           }



 
        currDisplay= newString.toString();
        return newString.toString();
    }


    public void allFinished()
    {
        Toast.makeText(this, "You Win!!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void youLose()
    {
        Toast.makeText(this, "You lose - sorry", Toast.LENGTH_LONG).show();
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
