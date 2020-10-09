package com.example.healthcare;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class CardViewHolder extends RecyclerView.ViewHolder {

    View myView;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);

        myView = itemView;
    }

    public void setDetails(Context ctx, String fullname, String image_url,String email, String Specialization) {
        TextView card_head = (TextView) myView.findViewById(R.id.mgUcName);
        TextView Specialization1 =(TextView)myView.findViewById(R.id.Specialization);
       TextView cemail = (TextView) myView.findViewById(R.id.Email);
       ImageView card_image = (ImageView) myView.findViewById(R.id.profilePic);

        card_head.setText(fullname);
          cemail.setText(image_url);
        Specialization1.setText(Specialization);
        Picasso.get().load(email).into(card_image);

        Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
        myView.setAnimation(animation);

    }


}