package space.jsdn.karaoke_rand;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Fav_menu extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> items3 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listv);
        init();


    }
    public void init(){
        final ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items3);

        listView.setAdapter(adapter3);
        ArrayList<Listviewitem> data = new ArrayList<>();
        SharedPreferences test = getSharedPreferences("fav", MODE_PRIVATE);

        for(int i=1;i<Search_menu.sh+1;i++) {
            String sh2= String.valueOf(i);
            items3.add(test.getString(sh2, " "));
        }

        adapter3.notifyDataSetChanged();
    }
}
