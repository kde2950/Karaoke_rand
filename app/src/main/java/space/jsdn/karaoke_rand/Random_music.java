package space.jsdn.karaoke_rand;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

public class Random_music extends AppCompatActivity {
    Button jpopb, kpopb, popb,favrand;
    public boolean jpopbo = false, kpopbo = false, popbo = false,favbo = false;
    TextView resultView;
    Elements items;
    private String phtmlPageUrl = "http://www.tjmedia.co.kr/tjsong/song_monthPopular.asp?strType=2&SYY=2018&SMM=06&SDD=01&EYY=2018&EMM=06&EDD=19";
    private String khtmlPageUrl = "http://www.tjmedia.co.kr/tjsong/song_monthPopular.asp";
    private String jhtmlPageUrl = "http://www.tjmedia.co.kr/tjsong/song_monthPopular.asp?strType=3&SYY=2018&SMM=06&SDD=01&EYY=2018&EMM=06&EDD=19";
    private TextView textviewHtmlDocument;
    public String htmlContentInStringFormat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_music);
        jpopb = findViewById(R.id.button);
        kpopb = findViewById(R.id.button2);
        popb = findViewById(R.id.button3);
        resultView = findViewById(R.id.textView3);
        favrand = findViewById(R.id.favrand);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        kpopb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kpopbo = true;
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });

        jpopb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jpopbo = true;
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });
        popb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popbo = true;
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });
        favrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favbo = true;
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (kpopbo) {
                try {
                    Document doc = Jsoup.connect(khtmlPageUrl).get();
                    Random rand = new Random();
                    int randomNumber = new Random().nextInt(100) + 2;
                    String code = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td:nth-child(2)").text();
                    String title = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td.left").text();
                    String singer = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td:nth-child(4)").text();
                    htmlContentInStringFormat = code + "\n제목 : " + title + " \n가수 : " + singer;
                    kpopbo = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (jpopbo) {
                try {
                    Document doc = Jsoup.connect(jhtmlPageUrl).get();
                    Random rand = new Random();
                    int randomNumber = new Random().nextInt(100) + 2;
                    String code = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td:nth-child(2)").text();
                    String title = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td.left").text();
                    String singer = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td:nth-child(4)").text();
                    htmlContentInStringFormat = code + "\n제목 : " + title + " \n가수 : " + singer;
                    jpopbo = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (popbo) {
                try {
                    Document doc = Jsoup.connect(phtmlPageUrl).get();
                    Random rand = new Random();
                    int randomNumber = new Random().nextInt(100) + 2;
                    String code = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td:nth-child(2)").text();
                    String title = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td.left").text();
                    String singer = doc.select("#BoardType1 > table > tbody > tr:nth-child(" + randomNumber + ") > td:nth-child(4)").text();
                    htmlContentInStringFormat = code + "\n제목 : " + title + " \n가수 : " + singer;
                    popbo = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(favbo){
                try {
                    Random rand = new Random();
                    int randomNumber = new Random().nextInt(Search_menu.sh+1);
                    String randm = String.valueOf(randomNumber);
                    SharedPreferences test = getSharedPreferences("fav", MODE_PRIVATE);
                    String s = test.getString(randm,"");
                    htmlContentInStringFormat = s;
                    favbo = false;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            resultView.setText(htmlContentInStringFormat);
        }
    }


}


