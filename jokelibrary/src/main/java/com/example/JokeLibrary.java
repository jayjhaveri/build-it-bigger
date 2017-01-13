package com.example;

import java.util.Random;

public class JokeLibrary {

//    private String mJoke;
    private int mJokePosition=0;

    private String[] mJokesArray = {
            "Two bytes meet.  The first byte asks, \"Are you ill?\"  \n" +
                    "The second byte replies, \"No, just feeling a bit off.\"",
            "Q. How did the programmer die in the shower?\n" +
                    "A. He read the shampoo bottle instructions: Lather. Rinse. Repeat.",
            "How many programmers does it take to change a light bulb?\n" +
                    "None - It \'s a hardware problem",
            "There are only 10 kinds of people in this world: those who know binary and those who don’t."
    };

    public String getJoke(){
        radomJoke();
        return mJokesArray[mJokePosition];
    }

    public void radomJoke(){
        int size = mJokesArray.length;
        mJokePosition = new Random().nextInt(size-1);
    }

}
