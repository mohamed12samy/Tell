package com.example.tell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private static final int RC_PHOTO_PICKER =2 ;

    private ImageButton imgB ;
    private String Username;


    private void DisplayPosts()
    {
        ArrayList<Post> pInfo = new ArrayList<>();

        pInfo.add(new Post("Mohamed" , "Fuck you all " ,R.drawable.logo));
        pInfo.add(new Post("Mamdouh" , "skglsbglbisbgbhgbrsigyusbhbfvvvvfgges bgthgdgdgdgtgv fg stgt",R.drawable.logo ));
        pInfo.add(new Post("Ebrahim" , "skglsbglbisbgbhgb dvdrg tryt6ru  7u 67i86i  rsigyusbhbfvvvvfgges bgthgdgdgdgtgv fg stgth" ,R.drawable.logo));
        pInfo.add(new Post("Karim" ,   "skglsfdrth ttiy  obglbisbgbhgb dvdrg tryt6ru  7u  8otyi 67i86i  rsigyusb 7ii  7ihbfv 8itvvvfgges bgthgdgdgdgtgv fg stgth" ,R.drawable.logo));
        pInfo.add(new Post("Mostafa" , "frfrgsdssbglbisbgbhgb dvdrg tryt6ru  7u 67i86i  rthrtrsigyusbhjjyt  bfvvvvfgges  fg stgth",R.drawable.logo ));
        pInfo.add(new Post("Kamel" ,   "moahmfh y ioewniug nuerguhb ryt6ru  7u 67i86i  rsigyusbhbfvvvvfgges bgthgdgdgdgtgv fg stgth" ,R.drawable.logo));
        pInfo.add(new Post("Salem" ,   "poiuyte wasv mkm jij uhu ygy tttt gggggqwtrd ",R.drawable.logo ));
        pInfo.add(new Post("Saleh" ,   "zzzzzzzzzzzzzzaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaazzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" ,R.drawable.logo));
        pInfo.add(new Post("Ali" ,     "aaaaaaaaaaaaaaaaaaaaaaaaaassssssssssssssssssssssssssssssssssssssss",R.drawable.logo));
        pInfo.add(new Post("Ahmed" ,   "ddddddddddddddddddddddddddddddddddddddddddddddddddd" ,R.drawable.logo));
        pInfo.add(new Post("Samy" ,    "ddddddddddddddddfffffffffffffffffffffffffffffffffffffffffffffff" ,R.drawable.logo));

        ListView listView = (ListView) findViewById(R.id.postList);
        listView.setAdapter(new Post_Adapter(this,0,pInfo));
    }

    private void OpenImageDialogue()
    {
        imgB  = (ImageButton) findViewById(R.id.imageButton);
        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                //here we use startActivityForResult instead of startActivity because here ther is an returning value (result) which is the photo and will use it in onActivityResult
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        DisplayPosts();

        OpenImageDialogue();

    }

}
