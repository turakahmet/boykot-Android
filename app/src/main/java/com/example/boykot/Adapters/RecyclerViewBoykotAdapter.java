package com.example.boykot.Adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boykot.R;
import com.example.boykot.pojo.BoykotMarka;

import java.util.List;

public class RecyclerViewBoykotAdapter extends RecyclerView.Adapter<RecyclerViewBoykotAdapter.ViewHolder> {
    private final Context context;
    private final List<BoykotMarka> boykotMarkaArrayList;

    // Constructor
    public RecyclerViewBoykotAdapter(Context context, List<BoykotMarka> boykotMarkaArrayList) {
        this.context = context;
        this.boykotMarkaArrayList = boykotMarkaArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewBoykotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBoykotAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        BoykotMarka boykotMarka = boykotMarkaArrayList.get(position);
        holder.markaAdi.setText(boykotMarka.getUrunAdi());
        holder.markaAciklama.setText(boykotMarka.getUrunAciklamasi());
        if (boykotMarka.getUrunLogo() != null) {
            String imageDataString = boykotMarka.getUrunLogo(); // Resmi bir dize olarak alın
            // Base64 kodlamasını kullanarak dizeyi byte dizisine dönüştür
            byte[] imageData = Base64.decode(imageDataString, Base64.DEFAULT);
            // Byte dizisini Bitmap'e dönüştür
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            // Bitmap'i ImageView'de göstermek için Drawable'a dönüştür
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            // ImageView'e dönüştürülmüş resmi atama
            holder.urunLogo.setImageDrawable(drawable);
        }
        holder.setIsExpanded(boykotMarka.isExpanded());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return boykotMarkaArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView markaAdi;
        private TextView markaAciklama;
        private CardView cardView;
        private boolean isExpanded = false;
        private RelativeLayout relativeLayout;
        private ImageView urunLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            markaAdi = itemView.findViewById(R.id.idMarkaAdi);
            markaAciklama = itemView.findViewById(R.id.idMarkaAciklama);
            cardView = itemView.findViewById(R.id.idCardView);
            relativeLayout = itemView.findViewById(R.id.idCardViewRelativeLayout);
            urunLogo = itemView.findViewById(R.id.idMarkaResmi);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleExpansion();
                }
            });
        }

        public void setIsExpanded(boolean expanded) {
            isExpanded = expanded;
            if (isExpanded) {
                expandView();
            } else {
                collapseView();
            }
        }

        private void toggleExpansion() {
            isExpanded = !isExpanded;
            if (isExpanded) {
                expandView();
            } else {
                collapseView();
            }
        }

        private void expandView() {
            markaAciklama.setMaxLines(Integer.MAX_VALUE);
            ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            relativeLayout.setLayoutParams(layoutParams);
            ValueAnimator anim = ValueAnimator.ofInt(cardView.getHeight(), cardView.getHeight() + measureTextHeight(markaAciklama));
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                    layoutParams.height = val;
                    cardView.setLayoutParams(layoutParams);
                }
            });
            anim.setDuration(300);
            anim.start();

        }

        private void collapseView() {
            ValueAnimator anim = ValueAnimator.ofInt(cardView.getHeight(), cardView.getHeight() - measureTextHeight(markaAciklama));
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                    layoutParams.height = val;
                    cardView.setLayoutParams(layoutParams);
                }
            });
            anim.setDuration(300);
            anim.start();
        }

        private int measureTextHeight(TextView textView) {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(textView.getWidth(), View.MeasureSpec.EXACTLY);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            textView.measure(widthMeasureSpec, heightMeasureSpec);
            if(textView.length()>150)
                return textView.getMeasuredHeight()-200;
            else
                return textView.getMeasuredHeight();
        }
    }
}
