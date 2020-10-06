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

    public void setDetails(Context ctx, String name, String image,String email) {
        TextView card_head = (TextView) myView.findViewById(R.id.mgUcName);

        TextView cemail = (TextView) myView.findViewById(R.id.mgUcEmail);
        ImageView card_image = (ImageView) myView.findViewById(R.id.mgUcImg);
        card_head.setText(name);
       cemail.setText(email);
        Picasso.get().load(image).into(card_image);

        Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
        myView.setAnimation(animation);

    }


}