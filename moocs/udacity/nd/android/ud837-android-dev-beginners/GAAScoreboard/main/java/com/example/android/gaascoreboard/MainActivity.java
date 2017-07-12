package com.example.android.gaascoreboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    static final String GOAL_SCORE_TEAM_A = "teamAGoalScore";
    static final String GOAL_SCORE_TEAM_B = "teamBGoalScore";
    static final String POINT_SCORE_TEAM_A = "teamAPointScore";
    static final String POINT_SCORE_TEAM_B = "teamBPointScore";
    static final String TOTAL_SCORE_TEAM_A = "teamATotalScore";
    static final String TOTAL_SCORE_TEAM_B = "teamBTotalScore";

    int goalScoreTeamA = 0;
    int goalScoreTeamB = 0;
    int pointScoreTeamA = 0;
    int pointScoreTeamB = 0;
    int totalScoreTeamA = 0;
    int totalScoreTeamB = 0;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(GOAL_SCORE_TEAM_A, goalScoreTeamA);
        savedInstanceState.putInt(GOAL_SCORE_TEAM_B, goalScoreTeamB);
        savedInstanceState.putInt(POINT_SCORE_TEAM_A, pointScoreTeamA);
        savedInstanceState.putInt(POINT_SCORE_TEAM_B, pointScoreTeamB);
        savedInstanceState.putInt(TOTAL_SCORE_TEAM_A, totalScoreTeamA);
        savedInstanceState.putInt(TOTAL_SCORE_TEAM_B, totalScoreTeamB);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            goalScoreTeamA = savedInstanceState.getInt(GOAL_SCORE_TEAM_A);
            goalScoreTeamB = savedInstanceState.getInt(GOAL_SCORE_TEAM_B);
            pointScoreTeamA = savedInstanceState.getInt(POINT_SCORE_TEAM_A);
            pointScoreTeamB = savedInstanceState.getInt(POINT_SCORE_TEAM_B);
            totalScoreTeamA = savedInstanceState.getInt(TOTAL_SCORE_TEAM_A);
            totalScoreTeamB = savedInstanceState.getInt(TOTAL_SCORE_TEAM_B);
            setContentView(R.layout.activity_main);
            displayGoalForTeamA(goalScoreTeamA);
            displayGoalForTeamB(goalScoreTeamB);
            displayPointForTeamA(pointScoreTeamA);
            displayPointForTeamB(pointScoreTeamB);
            displayTotalForTeamA(totalScoreTeamA);
            displayTotalForTeamB(totalScoreTeamB);
        }
    }

    /**
     * This method is called when the 'Goal' button is clicked for TeamA.
     */
    public void addGoalForTeamA(View view) {
        goalScoreTeamA = goalScoreTeamA + 1;
        displayGoalForTeamA(goalScoreTeamA);
        increaseTotalForTeamA(3);
    }

    /**
     * This method is called when the 'Point' button is clicked for TeamA.
     */
    public void addPointForTeamA(View view) {
        pointScoreTeamA = pointScoreTeamA + 1;
        displayPointForTeamA(pointScoreTeamA);
        increaseTotalForTeamA(1);
    }

    /**
     * This method is called when the total needs updating for Team A.
     */
    public void increaseTotalForTeamA(int score) {
        totalScoreTeamA = totalScoreTeamA + score;
        displayTotalForTeamA(totalScoreTeamA);
    }

    /**
     * Displays the given goal score for Team A.
     */
    public void displayGoalForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_goal_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given point score for Team A.
     */
    public void displayPointForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_point_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the total score for Team A.
     */
    public void displayTotalForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_total_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * This method is called when the 'Goal' button is clicked for TeamB.
     */
    public void addGoalForTeamB(View view) {
        goalScoreTeamB = goalScoreTeamB + 1;
        displayGoalForTeamB(goalScoreTeamB);
        increaseTotalForTeamB(3);
    }

    /**
     * This method is called when the 'Point' button is clicked for TeamB.
     */
    public void addPointForTeamB(View view) {
        pointScoreTeamB = pointScoreTeamB + 1;
        displayPointForTeamB(pointScoreTeamB);
        increaseTotalForTeamB(1);
    }

    /**
     * This method is called when the total needs updating for Team B.
     */
    public void increaseTotalForTeamB(int score) {
        totalScoreTeamB = totalScoreTeamB + score;
        displayTotalForTeamB(totalScoreTeamB);
    }

    /**
     * Displays the given goal score for Team B.
     */
    public void displayGoalForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_goal_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given point score for Team B.
     */
    public void displayPointForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_point_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the total score for Team B.
     */
    public void displayTotalForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_total_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * This method is called when the 'Reset' button is clicked.
     */
    public void reset(View view) {
        goalScoreTeamA = 0;
        goalScoreTeamB = 0;
        pointScoreTeamA = 0;
        pointScoreTeamB = 0;
        totalScoreTeamA = 0;
        totalScoreTeamB = 0;
        displayGoalForTeamA(goalScoreTeamA);
        displayGoalForTeamB(goalScoreTeamB);
        displayPointForTeamA(pointScoreTeamA);
        displayPointForTeamB(pointScoreTeamB);
        displayTotalForTeamA(totalScoreTeamA);
        displayTotalForTeamB(totalScoreTeamB);
    }

}