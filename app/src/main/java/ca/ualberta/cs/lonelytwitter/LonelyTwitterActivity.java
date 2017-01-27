package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;


public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file9.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
    public Date date;
    // creating the different mood objects
    CurrentMood MehMood = new CurrentMood();
    HappyMood HappyMood = new HappyMood();
    SadMood SadMood = new SadMood();
	ArrayList<String> tweetsLib = new ArrayList<String>();
	Gson gson = new Gson();
	String[] empty = {""};



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
        //added the mood buttons
		Button saveButton = (Button) findViewById(R.id.save);
        Button MehMoodButton = (Button) findViewById(R.id.button4);
        Button HappyMoodButton = (Button) findViewById(R.id.button2);
        Button SadMoodButton = (Button) findViewById(R.id.button3);
        Button ClearButton = (Button) findViewById(R.id.clear);

		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		String[] tweets = loadFromFile();
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, tweets);
		final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, empty);
		oldTweetsList.setAdapter(adapter);



        //Tweet tweet = new Tweet(date,"First tweet!");



		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				tweetsLib.add(gson.toJson(text));
				saveInFile(text, new Date(System.currentTimeMillis()));
				finish();

			}
		});


        ClearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
//                try {
//                    PrintWriter writer = new PrintWriter(FILENAME);
//                    writer.print("");
//                    writer.close();
//                }catch (FileNotFoundException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }


				oldTweetsList.setAdapter(adapter2);
				adapter.notifyDataSetChanged();
                finish();
            }
        });

        MehMoodButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString() + " [feeling" + MehMood.getMood();
                saveInFile(text, new Date(System.currentTimeMillis()));
                finish();

            }
        });

        HappyMoodButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString() + " [feeling" + HappyMood.getHappyMood();
                saveInFile(text, new Date(System.currentTimeMillis()));
                finish();

            }
        });

        SadMoodButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString() + " (feeling" + SadMood.getSadMood();
                saveInFile(text, new Date(System.currentTimeMillis()));
                finish();

            }
        });
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();



	}

	private String[] loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				tweets.add(line);
				line = in.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweets.toArray(new String[tweets.size()]);
	}

	private void saveInFile(String text, Date date) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND);
			fos.write(new String(date.toString() + " | " + text)
					.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private void clearFile() {
//		try {
//			FileOutputStream fos = openFileOutput(FILENAME,Context.MODE_APPEND);
//			fos.write(new String("")
//					.getBytes());
//			fos.close();
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}