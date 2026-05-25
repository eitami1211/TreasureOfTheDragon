package com.example.treasureofthedragon;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.graphics.Color.TRANSPARENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton boardButtons[][];
    private GameManager manager;
    private Dialog dialog;
    private Button endGameBtn;
    private ArrayList<TextView> names;
    private ArrayList<TextView> scores;
    private ArrayList<EditText> namesEditText;
    private int numOfPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);
        names = new ArrayList<>();
        scores = new ArrayList<>();
        namesEditText = new ArrayList<>();
        boardButtons = new ImageButton[7][7];
        endGameBtn = findViewById(R.id.button);
        endGameBtn.setOnClickListener(this);

        openNamesDialog();

        for (int i = 0; i < 3; i ++)
        {
            String idStrHeader = "header" + i;
            int resIdHeader = getResources().getIdentifier(idStrHeader, "id", getPackageName());
            names.add(findViewById(resIdHeader));

            String idStrScore = "score" + i;
            int resIdScore = getResources().getIdentifier(idStrScore, "id", getPackageName());
            scores.add(findViewById(resIdScore));
        }
    }

    @Override
    public void onClick(View v) {

        for (int i  = 0; i < 7 ; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                if (boardButtons[i][j].getId() == v.getId() && !manager.getBoardTiles()[i][j].isSelected())
                {
                    if (manager.getFirstTile().getType() == -2) // first
                    {
                        manager.setFirstTile(manager.getBoardTiles()[i][j]);
                    }


                    if (manager.getFirstTile().getType() == 1) // dragon
                    {
                        if (manager.getBoardTiles()[i][j].getType() == 1)//another dragon
                        {
                            manager.getBoardTiles()[i][j].setSelected(true);
                            showImage(manager.getBoardTiles()[i][j].getType(),i,j);
                            manager.addTileToList(manager.getBoardTiles()[i][j]);
                        }
                        else
                        {
                            manager.getBoardTiles()[i][j].setSelected(true);
                            Toast.makeText(this, "" + manager.getBoardTiles()[i][j].getType(), Toast.LENGTH_SHORT).show();
                            manager.cutTurn();
                            //runLosingThread(i,j); TODO-> remove
                            showLosingReason(i,j);

                        }
                    }
                    else // regular turn
                    {
                        if (manager.getBoardTiles()[i][j].getType() == 1 || manager.getBoardTiles()[i][j].getType() == 0)
                        {

                            manager.getBoardTiles()[i][j].setSelected(true);
                            Toast.makeText(this, "" + manager.getBoardTiles()[i][j].getType(), Toast.LENGTH_SHORT).show();
                            manager.cutTurn();
                            showLosingReason(i,j);
                        }
                        else
                        {
                            manager.getBoardTiles()[i][j].setSelected(true);
                            showImage(manager.getBoardTiles()[i][j].getType(),i,j);
                            manager.addTileToList(manager.getBoardTiles()[i][j]);
                        }
                    }
                }

            }
        }

        if (endGameBtn.getId() == v.getId())
        {
            collectCards();
        }

        if(isGameEnded())
        {
            openWinDialog();
        }

    }

    public void showImage(int num, int i, int j)
    {

        /*if (num == 0)
            boardButtons[i][j].setImageResource(R.drawable.spider);
        if (num == 1)
            boardButtons[i][j].setImageResource(R.drawable.dragon);
        if (num == 2)
            boardButtons[i][j].setImageResource(R.drawable.diamond);
        if (num == 3)
            boardButtons[i][j].setImageResource(R.drawable.princess);
        if (num == 4)
            boardButtons[i][j].setImageResource(R.drawable.train);
        if (num == 5)
            boardButtons[i][j].setImageResource(R.drawable.ball);
        if (num == 6)
            boardButtons[i][j].setImageResource(R.drawable.robot);
        if (num == 7)
            boardButtons[i][j].setImageResource(R.drawable.treasure);*/

        if (num > -1 && num < 8)
        {
            final ObjectAnimator oa1 = ObjectAnimator.ofFloat(boardButtons[i][j], "scaleX", 1f, 0f);
            final ObjectAnimator oa2 = ObjectAnimator.ofFloat(boardButtons[i][j], "scaleX", 0f, 1f);
            oa1.setInterpolator(new DecelerateInterpolator());
            oa2.setInterpolator(new AccelerateDecelerateInterpolator());
            ImageButton imageButton = boardButtons[i][j];
            oa1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (num == 0)
                        boardButtons[i][j].setImageResource(R.drawable.spider);
                    if (num == 1)
                        boardButtons[i][j].setImageResource(R.drawable.dragon);
                    if (num == 2)
                        boardButtons[i][j].setImageResource(R.drawable.diamond);
                    if (num == 3)
                        boardButtons[i][j].setImageResource(R.drawable.princess);
                    if (num == 4)
                        boardButtons[i][j].setImageResource(R.drawable.train);
                    if (num == 5)
                        boardButtons[i][j].setImageResource(R.drawable.ball);
                    if (num == 6)
                        boardButtons[i][j].setImageResource(R.drawable.robot);
                    if (num == 7)
                        boardButtons[i][j].setImageResource(R.drawable.treasure);
                    oa2.start();
                }
            });
            oa1.start();
        }
    }

    public void collectCards()
    {
        if(manager.getFirstTile().getType() != 1)
            manager.collectCards(false);
        else
            manager.collectCards(true);

        updateInvisibles();
        updateTexts();
    }

    public void updateInvisibles()
    {
        for (int i  = 0; i < 7 ; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                if (manager.getBoardTiles()[i][j].getType() == -2)
                {
                    boardButtons[i][j].setVisibility(View.INVISIBLE);
                }
                else
                {
                    //boardButtons[i][j].setImageResource(R.drawable.back);
                    if (manager.getCardsToFlip().contains(manager.getBoardTiles()[i][j]))
                    {
                        // flip
                        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(boardButtons[i][j], "scaleX", 1f, 0f);
                        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(boardButtons[i][j], "scaleX", 0f, 1f);
                        oa1.setInterpolator(new DecelerateInterpolator());
                        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                        ImageButton imageButton = boardButtons[i][j];
                        oa1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            imageButton.setImageResource(R.drawable.back);
                            oa2.start();
                        }
                    });
                    oa1.start();

                    }
                }
            }
        }
    }

    public void updateTexts()
    {
        for (int i = 0; i < numOfPlayers; i ++)
        {
            if (i == manager.getCurrTurn())
            {
                names.get(i).setTextColor(getResources().getColor(R.color.theme));
                scores.get(i).setTextColor(getResources().getColor(R.color.theme));
            }
            else
            {
                names.get(i).setTextColor(getResources().getColor(R.color.white));
                scores.get(i).setTextColor(getResources().getColor(R.color.white));
            }

            scores.get(i).setText("" + manager.getPlayers().get(i).getCardsCollected());
        }
    }

    public void runLosingThread(int i, int j)
    {

        showImage(manager.getBoardTiles()[i][j].getType(),i,j);


        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    synchronized (this) {
                        wait(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                boardButtons[i][j].setImageResource(R.drawable.back);

                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };
        };
        thread.start();


    }

    public void showLosingReason(int i, int j)
    {
        clickableAll(false);
        showImage(manager.getBoardTiles()[i][j].getType(),i,j);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_anim);
        boardButtons[i][j].startAnimation(animation);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boardButtons[i][j].clearAnimation();
                boardButtons[i][j].setImageResource(R.drawable.back);
                //Toast.makeText(getApplicationContext(), "change made" , Toast.LENGTH_SHORT).show();
                updateInvisibles();
                updateTexts();
                clickableAll(true);

            }
        }, 1700);
    }

    public boolean isGameEnded()
    {

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i  = 0; i < 7 ; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                if (manager.getBoardTiles()[i][j].getType() != -2 ) {
                    if (manager.getBoardTiles()[i][j].getType() == 0)// spider
                    {
                        indexes.add(i);
                        indexes.add(j);
                    }
                    else {
                        return false;
                    }
                }

            }
        }

        for (int i = 0; i < 3; i ++)
        {
            boardButtons[indexes.get(2*i)][indexes.get(2*i+1)].setImageResource(R.drawable.spider);
        }

        return true;
    }

    public void clickableAll (boolean clickable)
    {
        for (int i  = 0; i < 7 ; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                boardButtons[i][j].setClickable(clickable);

            }
        }
    }

    private void openWinDialog()
    {
        this.dialog.setContentView(R.layout.win_layout_dialog);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

        ImageButton imageButton = this.dialog.findViewById(R.id.close);
        Button button = this.dialog.findViewById(R.id.btnOk);
        TextView text = this.dialog.findViewById(R.id.textView2);
        text.setText("congratulations " + names.get(getWinner()).getText().toString() + ", you cracked my code!");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private int getWinner()
    {
        int winner = 0;
        for (int i = 1; i < numOfPlayers ; i ++)
        {
            if (manager.getPlayers().get(winner).getCardsCollected() < manager.getPlayers().get(i).getCardsCollected())
                winner = i;
        }

        return winner;
    }

    private void openNamesDialog()
    {
        this.dialog.setContentView(R.layout.names_layout_dialog);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

        ImageButton closeButton = this.dialog.findViewById(R.id.close);
        Button continueButton = this.dialog.findViewById(R.id.continueBtn);

        for (int i = 0; i < 3; i ++) {
            String idStr = "name" + i;
            int resId = getResources().getIdentifier(idStr, "id", getPackageName());
            namesEditText.add(dialog.findViewById(resId));
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLegal = true;

                for (int i = 0; i < 3; i ++)
                {
                    if (namesEditText.get(i).getText().toString().equals(""))
                    {
                        if (i == 2)
                        {
                            names.get(2).setVisibility(View.INVISIBLE);
                            scores.get(2).setVisibility(View.INVISIBLE);
                            numOfPlayers = 2;
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "missing names", Toast.LENGTH_SHORT).show();
                            isLegal = false;

                        }
                    }
                    else
                    {
                        if (i == 2)
                        {
                            names.get(2).setVisibility(View.VISIBLE);
                            scores.get(2).setVisibility(View.VISIBLE);
                            numOfPlayers = 3;
                        }

                        names.get(i).setText(namesEditText.get(i).getText().toString());
                    }
                }
                if (isLegal)
                {
                    dialog.dismiss();
                    manager = new GameManager(numOfPlayers);
                    identifyBoard();
                }
            }
        });

        dialog.show();
    }

    private void identifyBoard()
    {
        for (int i  = 0; i < 7 ; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                String idStr1 = "imageButton" + i + j;
                int resId1 = getResources().getIdentifier(idStr1, "id", getPackageName());

                boardButtons[i][j] = (ImageButton) findViewById(resId1);
                boardButtons[i][j].setOnClickListener(this);

                int num = manager.getBoardTiles()[i][j].getType();


                //setImage(num,i,j);


            }
        }
    }
}