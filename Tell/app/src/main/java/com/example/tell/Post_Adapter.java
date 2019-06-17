package com.example.tell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Post_Adapter  extends ArrayAdapter<Post> {
    public Post_Adapter (Context context, int resource, ArrayList<Post> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.post, parent, false);
        }

        ImageView userPhoto = (ImageView) listItemView.findViewById(R.id.userphoto);
        TextView username = (TextView) listItemView.findViewById(R.id.username);
        TextView postT = (TextView) listItemView.findViewById(R.id.posttext);

        Post post = getItem(position);

        /*Glide.with(userPhoto.getContext())
                .load(post.getUserphotoUrl())
                .into(userPhoto);*/
        //userPhoto.setVisibility(View.GONE);
        userPhoto.setImageResource(post.getUserphotoUrl());
        username.setText(post.getmUserName());
        postT.setText(post.getmPostText());

        return listItemView;
    }
}
