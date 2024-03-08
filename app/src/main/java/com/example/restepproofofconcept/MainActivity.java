package com.example.restepproofofconcept;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout parentLayout;
    private LinearLayout[] menuItemLayouts;
    private LinearLayout[] replyViewLayouts;
    private TextView notificationLength;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(R.id.parentLayout);

        // Example data (replace with your own)
        String[] names = {"taylor swift", "steteh dream", "source"};
        String[] questions = {"sleepy overrated singer?", "to be like me in maheshvem", "abo alrock alghale"};

        menuItemLayouts = new LinearLayout[names.length];
        replyViewLayouts = new LinearLayout[names.length]; // Initialize replyViewLayouts with the same length

        for (int i = 0; i < names.length; i++) {
            addMenuItem(names[i], questions[i], i);
        }

        // Add the "Do a post" button after dynamically created views
        doAPostButton();

        notificationLength = findViewById(R.id.notificationLength);
        notificationLength.setText(String.valueOf(menuItemLayouts.length));
        x = menuItemLayouts.length;

        ImageButton visibilityButton = findViewById(R.id.visibility_button);
        visibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility();
            }
        });
    }

    private void addMenuItem(String name, String question, int index) {
        // Inflate menu item layout and add to parent layout
        View menuItemView = LayoutInflater.from(this).inflate(R.layout.menu_item, parentLayout, false);
        TextView textName = menuItemView.findViewById(R.id.textName);
        TextView textQuestion = menuItemView.findViewById(R.id.textQuestion);
        Button buttonReply = menuItemView.findViewById(R.id.buttonReply);

        textName.setText(name);
        textQuestion.setText(question);

        LinearLayout menuItemLayout = (LinearLayout) menuItemView;
        menuItemLayouts[index] = menuItemLayout; // Store the menuItemLayout

        parentLayout.addView(menuItemView);

        buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle reply button click
                View replyView = LayoutInflater.from(MainActivity.this).inflate(R.layout.reply_layout, parentLayout, false);
                EditText editTextReply = replyView.findViewById(R.id.editTextReply);
                Button buttonSubmit = replyView.findViewById(R.id.buttonSubmit);

                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle submit button click
                        String reply = editTextReply.getText().toString();
                        Toast.makeText(MainActivity.this, "You replied with: " + reply, Toast.LENGTH_SHORT).show();
                        notificationLength.setText(String.valueOf(x - 1));
                        x = x - 1;
                        if (x == 0) notificationLength.setVisibility(View.INVISIBLE);
                        else notificationLength.setVisibility(View.VISIBLE);
                        // Remove reply view and corresponding question view
                        parentLayout.removeView(replyView);
                        parentLayout.removeView(menuItemLayout);
                        replyViewLayouts[index] = null; // Clear the corresponding entry in replyViewLayouts
                    }
                });

                // Add reply view below the clicked menu item
                parentLayout.addView(replyView, parentLayout.indexOfChild(menuItemLayout) + 1);
                replyViewLayouts[index] = (LinearLayout) replyView; // Store the replyViewLayout

                // Disable the reply button after it's been clicked once
                buttonReply.setEnabled(false);
            }
        });
    }

    private void toggleVisibility() {
        for (LinearLayout layout : menuItemLayouts) {
            if (layout != null) {
                layout.setVisibility(Math.abs(layout.getVisibility() - View.GONE));
            }
        }
        for (LinearLayout layout : replyViewLayouts) {
            if (layout != null) {
                layout.setVisibility(Math.abs(layout.getVisibility() - View.GONE));
            }
        }
    }

    private void doAPostButton() {
        // Add "Do a post" button to the parent layout
        View doAPostView = LayoutInflater.from(MainActivity.this).inflate(R.layout.doapostbutton, parentLayout, false);
        parentLayout.addView(doAPostView);

        doAPostView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"you cant do 2 posts a time",Toast.LENGTH_LONG).show();
                doAPostView.setEnabled(false);
                View postingView=LayoutInflater.from(MainActivity.this).inflate(R.layout.post_text,parentLayout,false);
                EditText post=postingView.findViewById(R.id.postText);
                Button submitButton=postingView.findViewById(R.id.postPost);
                parentLayout.addView(postingView);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post.setTextSize(30);
                        post.setTextColor(Color.parseColor("#000000"));
                        post.setBackgroundColor(Color.GRAY);
                        post.setEnabled(false);
                        parentLayout.removeView(submitButton);
                        submitButton.setVisibility(View.GONE);
                        parentLayout.removeView(doAPostView);
                        doAPostButton();

                    }
                });
            }
        });
    }
}
