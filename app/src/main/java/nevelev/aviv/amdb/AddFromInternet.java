package nevelev.aviv.amdb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddFromInternet extends AppCompatActivity {

    public ListView listViewMovies;
    private Dialog insertOrUpdateDialog;
    private AlertDialog deleteDialog;
    public List<Movie> tempMovies;
    public MoviesReaderController moviesReaderController;
    public Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_internet);

        mContext = App.getContext();

        listViewMovies = (ListView) findViewById(R.id.listView);
        moviesReaderController = new MoviesReaderController(this);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                send(i);
            }
        });
    }

    public void send(int i){
        String tempName = listViewMovies.getAdapter().getItem(i).toString();
//        String tempPlaceInList = listViewMovies.getAdapter().getItem(i).
        String baseImageUrl = "http://image.tmdb.org/t/p/w185";

        tempMovies = moviesReaderController.giveMovies();

        for(int j =0;j<tempMovies.size();j++){
            if(tempName.equals(tempMovies.get(j).getSubject())){
                if(i==j){
                    String description = tempMovies.get(j).getBody();
                    String imageUrl = baseImageUrl + tempMovies.get(j).getUrl();

                    Intent editActivity = new Intent(this,EditActivity.class);
                    editActivity.putExtra("title",tempName);
                    editActivity.putExtra("des",description);
                    editActivity.putExtra("url",imageUrl);
                    editActivity.putExtra("id",1);
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).startActivityForResult(editActivity, 1);
                        finish();
                    }
                }
            }
        }
    }

    public void showMovies(View v){
        EditText nameText = (EditText) findViewById(R.id.name);
        String name = nameText.getText().toString();
        moviesReaderController.readAllMovies(name);
    }

    public void cancel(View v){
        finish();
    }
}