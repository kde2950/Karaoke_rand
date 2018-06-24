package space.jsdn.karaoke_rand;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search_menu extends AppCompatActivity {
    private Retrofit retrofit;
    private TextView textView;
    public String all = "";
    private final String BASE_URL = "https://api.manana.kr/";
    EditText editText;
    Button button;
    public String all2 = "";
    final ArrayList<String> items2 = new ArrayList<String>();
    final ArrayList<String> items = new ArrayList<String>();
    public static int sh = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);
        init();

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);


        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items);

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.singer_result);
        listview.setAdapter(adapter);


        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items2);

        // listview 생성 및 adapter 지정.
        final ListView listview2 = (ListView) findViewById(R.id.title_result);
        listview2.setAdapter(adapter2);
        listview.setOnItemLongClickListener(new ListViewItemLongClickListener());
        listview2.setOnItemLongClickListener(new ListViewItemLongClickListener2());
        textView = findViewById(R.id.textView2);

                /*AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("즐겨찾기");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                return false;*/


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences fav = getSharedPreferences("fav", MODE_PRIVATE);


                String keyw = editText.getText().toString();
                if (keyw.equals("")) {

                } else {
                    GitHub gitHub = retrofit.create(GitHub.class);
                    Karaoke karaoke = retrofit.create(Karaoke.class);
                    Call<List<Contributor>> call = gitHub.contributors(keyw);
                    Call<List<Contributor>> call2 = karaoke.contributors(keyw);

                    call.enqueue(new Callback<List<Contributor>>() {
                        @Override
                        public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                            items.clear();
                            adapter.notifyDataSetChanged();
                            ArrayList<Listviewitem> data = new ArrayList<>();
                            List<Contributor> contributors = response.body();
                            int count;
                            for (Contributor contributor : contributors) {
                                count = adapter.getCount();
                                all = contributor.no + " 제목 : " + contributor.title + " 가수 : " + contributor.singer;
                                items.add(all);
                            }


                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Call<List<Contributor>> call, Throwable t) {
                            Toast.makeText(Search_menu.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });


                    call2.enqueue(new Callback<List<Contributor>>() {
                        @Override
                        public void onResponse(Call<List<Contributor>> call2, Response<List<Contributor>> response) {

                            items2.clear();
                            adapter2.notifyDataSetChanged();
                            ArrayList<Listviewitem> data2 = new ArrayList<>();
                            List<Contributor> contributors2 = response.body();
                            int count2;
                            for (Contributor contributor : contributors2) {
                                count2 = adapter2.getCount();
                                all2 = contributor.no + " 제목 : " + contributor.title + " 가수 : " + contributor.singer;
                                items2.add(all2);
                            }


                            adapter2.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Call<List<Contributor>> call2, Throwable t) {
                            Toast.makeText(Search_menu.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        });
            /*
            GitHub gitHub = retrofit.create(GitHub.class);
            Call<List<Contributor>> call = gitHub.contributors("fripside");

            call.enqueue(new Callback<List<Contributor>>() {
                @Override
                public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                    List<Contributor> contributors = response.body();
                    for (Contributor contributor : contributors) {
                        all += contributor.no + " " + contributor.title +" "+contributor.singer;
                        textView.append("\n");
                    }
                }

                @Override
                public void onFailure(Call<List<Contributor>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                            .show();
                }
            });*/

    }


    public void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private class ListViewItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            int selectedPos = position;
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setTitle("즐겨찾기 추가");

            // '예' 버튼이 클릭되면
            alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences fav = getSharedPreferences("fav", MODE_PRIVATE);

                    SharedPreferences.Editor editor = fav.edit();
                    String sh1 = String.valueOf(sh);
                    sh++;
                    editor.putString(sh1,items.get(position)); //First라는 key값으로 infoFirst 데이터를 저장한다.

                    editor.commit(); //완료한다.

                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            // '아니오' 버튼이 클릭되면
            alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            alertDlg.setMessage(items.get(position));
            alertDlg.show();
            return false;
        }
    }
    private class ListViewItemLongClickListener2 implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            int selectedPos = position;
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setTitle("즐겨찾기 추가");

            // '예' 버튼이 클릭되면
            alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences fav = getSharedPreferences("fav", MODE_PRIVATE);

                    SharedPreferences.Editor editor = fav.edit();
                    String sh1 = String.valueOf(sh);
                    sh++;
                    editor.putString(sh1,items2.get(position)); //First라는 key값으로 infoFirst 데이터를 저장한다.

                    editor.commit(); //완료한다.
                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });

            // '아니오' 버튼이 클릭되면
            alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.dismiss();  // AlertDialog를 닫는다.
                }
            });
            alertDlg.setMessage(items2.get(position));
            alertDlg.show();
            return false;
        }
    }
}



