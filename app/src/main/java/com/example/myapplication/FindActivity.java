package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class FindActivity extends Activity {
    private NextPos pos = new NextPos();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Call when the button gets clicked
    public void onClickFindPos(View view) {
        //Get a reference to the TextView
        TextView views = (TextView) findViewById(R.id.views);
        //Get a reference to the Spinner
        Spinner choice = (Spinner) findViewById(R.id.choice);
        //Get the selected item in the Spinner
        String posName = String.valueOf(choice.getSelectedItem());
        //Get recommendations from the pos class
        //The strings filled as a list by the getViews method
        List<String> viewsList = pos.getViews(posName);
        //A string array by StringBuilder
        StringBuilder viewsFormatted = new StringBuilder();
        //Loop to append all strings in viewsList.
        for (String aview : viewsList){
            viewsFormatted.append(aview).append('\n');
        }
        //Display the beers
        views.setText(viewsFormatted);
    }

    //Call onSendMessage() when the button is clicked
    public void onSendMessage(View view){
        EditText messageView = (EditText)findViewById(R.id.message);
        //获得信息String
        String messageText = messageView.getText().toString();
        //意图由此活动启动另一活动
        //Intent intent = new Intent(this, ReceiveMessageActivity.class);
        /*EXTRA_MESSAGE for knew the messageText(it's the )*/
        //intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE,messageText);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("Text/Plain");
        intent.putExtra(Intent.EXTRA_TEXT,messageText);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);

    }

    public void onClickTimer(View view){
        Intent intent = new Intent(this, TimerActivity.class);

        startActivity(intent);
    }
}