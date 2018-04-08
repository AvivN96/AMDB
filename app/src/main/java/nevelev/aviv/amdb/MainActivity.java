package nevelev.aviv.amdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyDBHandler db;
    int i;
    LinearLayout l;
    boolean next;
    List<Movie> names;
    String tempTitle;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(this);
        db = new MyDBHandler(this);
        next = true;
        l = (LinearLayout) findViewById(R.id.linearLayoutMain);
        loadMovie();
    }

    public void deleteAll(){
        db.clear();
        restart();
    }

    public void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }

    public void loadMovie(){
        names =db.getAllMovieList();
        i =0;
        if (names.size() == i) {
            TextView empty = (TextView) findViewById(R.id.showAll);
            empty.setVisibility(View.VISIBLE);
        } else {
            while(i<names.size()) {
                String s = names.get(i).getSubject();
                tempTitle =s;
                String u = names.get(i).getUrl();
                String d = names.get(i).getBody();
                int id =names.get(i).get_id();
                makeMovie(s,d,u,id);

                i++;
            }
        }
    }

    public void makeMovie(String name,String description , String url,int id) {

        ImageView image = new ImageView(this);
        TextView title = new TextView(this);
        TextView des = new TextView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout linealayoutInside = new LinearLayout(this);
       // Button button = new Button(this);

       // setButton(button);
        setLinearLayoutInside(linealayoutInside);
        setTextDes(des);
        setLinearLayout(linearLayout);
        setImageView(image);
        setTextView(title);

        addPicture(image, url);

        image.setTag(name);
       // button.setTag(name);

        //button.setText(R.string.gotopage);
        //button.setTextSize(13);
        title.setText(name);
        des.setText(description);

        final CustomDialogClass cdd = new CustomDialogClass(MainActivity.this, name, id);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEdit(view);
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                cdd.show();
                return false;
            }
        });

        linealayoutInside.addView(title);
        linealayoutInside.addView(des);
       // linealayoutInside.addView(button);

        linearLayout.addView(image);
        linearLayout.addView(linealayoutInside);

        l.addView(linearLayout);
    }
    public void setButton(Button sv){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(positionRules);
        positionRules.setMargins(10, 10, 10, 10);
    }

    public void setLinearLayoutInside(LinearLayout linearLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(positionRules);
        linearLayout.getLayoutParams().height = 880;
        positionRules.setMargins(25,0, 0, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
    }

    public void setTextDes(TextView des){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        des.setLayoutParams(positionRules);
        des.setTextColor(Color.BLACK);
        des.setTextSize(13);
        positionRules.setMargins(5,5, 5, 5);
        des.getLayoutParams().height = 500;
    }

    public  void setLinearLayout(LinearLayout ll){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(positionRules);
        positionRules.setMargins(25, 25, 25, 25);
        ll.setLayoutParams(positionRules);
        ll.getLayoutParams().height = 900;
        ll.setBackgroundResource(R.drawable.layoutstyle);
    }

    public void setTextView(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextColor(Color.BLACK);
        b.setTextSize(25);
        positionRules.setMargins(15,0, 15, 15);
    }

    public void setImageView(ImageView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        positionRules.setMargins(15, 15, 25, 0);
        b.getLayoutParams().height = 800;
        b.getLayoutParams().width = 400;
    }

    public void addPicture(ImageView b,String u) {
        if (u.equals("")) {
            b.setBackgroundResource(R.drawable.pic);
            b.getBackground().setAlpha(150);
        } else {
            new DownloadImageTask(this,l,this, b, u).execute();
        }
    }



    public void goToEdit(View v){
        String movieTitle = v.getTag().toString();

        for(i=0;i<names.size();i++){
            if(movieTitle.equals(names.get(i).getSubject())){
                String title =names.get(i).getSubject();
                String des =names.get(i).getBody();
                String url =names.get(i).getUrl();
                int id = names.get(i).get_id();

                Intent editActivity = new Intent(this,EditActivity.class);
                editActivity.putExtra("name",title);
                editActivity.putExtra("des",des);
                editActivity.putExtra("url",url);
                editActivity.putExtra("id",id);
                this.startActivityForResult(editActivity,1);

            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.menuItemSettings:

                return true;
            case R.id.addInternet:
                Intent net = new Intent(this,AddFromInternet.class);
                startActivity(net);
                return true;
            case R.id.addManuall:
                Intent add = new Intent(this,EditActivity.class);
                add.putExtra("id",-1);
                startActivityForResult(add,1);
                return true;
            case R.id.exit:
                finish();
                return true;
            case R.id.deleteAll:
                deleteAll();
                return true;

        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                String name=data.getStringExtra("name");
                String des=data.getStringExtra("des");
                String url1=data.getStringExtra("url");

                if(des.equals("")) {
                    if ( url1.equals("")) {
                        Movie movie = new Movie(name,"","");
                        db.addMovie(movie);
                    } else {
                        Movie movie = new Movie(name, "", url1);
                        db.addMovie(movie);
                    }
                }else if(url1.equals("")){
                    Movie movie = new Movie(name,des,"");
                    db.addMovie(movie);
                }else{
                    Movie movie = new Movie(name,des,url1);
                    db.addMovie(movie);
                }

                this.recreate();



            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}