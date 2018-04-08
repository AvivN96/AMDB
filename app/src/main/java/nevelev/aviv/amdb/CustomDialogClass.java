package nevelev.aviv.amdb;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.List;

/**
 * Created by Android on 13/03/2018.
 */
public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button delete, edit;
    public String NAME;
    public int ID;
    MyDBHandler db;
    List<Movie> names;

    public CustomDialogClass(Activity a,String name,int id) {
        super(a);
        NAME=name;
        ID=id;
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_dialog);
        delete = (Button) findViewById(R.id.btn_delete);
        edit = (Button) findViewById(R.id.btn_edit);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        db= new MyDBHandler(c);
        names=db.getAllMovieList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                db.deleteMovie(ID);
                c.recreate();
                break;
            case R.id.btn_edit:
                int i=0;
                for(i=0;i<names.size();i++){
                    if(NAME.equals(names.get(i).getSubject())){
                        String title =names.get(i).getSubject();
                        String des =names.get(i).getBody();
                        String url =names.get(i).getUrl();
                        int id = names.get(i).get_id();

                        Intent editActivity = new Intent(c,EditActivity.class);
                        editActivity.putExtra("name",title);
                        editActivity.putExtra("des",des);
                        editActivity.putExtra("url",url);
                        editActivity.putExtra("id",id);
                        c.startActivityForResult(editActivity,1);
                        break;
                    }
            }


            default:
                break;
        }
        dismiss();
    }
}