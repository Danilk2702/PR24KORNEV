package com.example.pr24kornev;


import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;

    private int mCatSound;
    private int mChickenSound;
    private int mCowSound;
    private int mDogSound;
    private int mDuckSound;
    private int mSheepSound;

    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAssetManager = getAssets();

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        mCatSound = loadSound("cat.mp3");
        mChickenSound = loadSound("chicken.mp3");
        mCowSound = loadSound("cow.mp3");
        mDogSound = loadSound("dog.mp3");
        mDuckSound = loadSound("duck.mp3");
        mSheepSound = loadSound("sheep.mp3");

        findViewById(R.id.image_cat).setOnClickListener(this);
        findViewById(R.id.image_chicken).setOnClickListener(this);
        findViewById(R.id.image_cow).setOnClickListener(this);
        findViewById(R.id.image_dog).setOnClickListener(this);
        findViewById(R.id.image_duck).setOnClickListener(this);
        findViewById(R.id.image_sheep).setOnClickListener(this);
    }

    private int loadSound(String fileName) {
        try {
            AssetFileDescriptor afd = mAssetManager.openFd(fileName);
            return mSoundPool.load(afd, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.image_cat) {
            playSound(mCatSound);
        } else if (id == R.id.image_chicken) {
            playSound(mChickenSound);
        } else if (id == R.id.image_cow) {
            playSound(mCowSound);
        } else if (id == R.id.image_dog) {
            playSound(mDogSound);
        } else if (id == R.id.image_duck) {
            playSound(mDuckSound);
        } else if (id == R.id.image_sheep) {
            playSound(mSheepSound);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSoundPool != null) {
            mSoundPool.autoPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSoundPool != null) {
            mSoundPool.autoResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }
}