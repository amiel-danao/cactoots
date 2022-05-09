package com.thesis.cactoots.java.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.thesis.cactoots.java.R;
import com.thesis.cactoots.java.models.Item;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Currency;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private final NumberFormat format;
    private TextView txtItemName;
    private ImageView itemImage;
    private TextView itemPrice;
    private TextView itemDescription;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtItemName = itemView.findViewById(R.id.textViewTitle);
        itemImage = itemView.findViewById(R.id.imageView);
        itemDescription = itemView.findViewById(R.id.textViewShortDesc);
        itemPrice = itemView.findViewById(R.id.textViewPrice);

        format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("PHP"));


    }

    public void bindItem(Item item, Context context){
        txtItemName.setText(item.getName());

        if(item.getPrice() != null){
            String key1 = "";
            for (String key :
                    item.getPrice().keySet()) {
                key1 = key;
                if(!key1.isEmpty()){
                    break;
                }
            }

            itemPrice.setText(format.format(item.getPrice().get(key1)));
        }

        fetchImage(item, context);
    }

    private void fetchImage(Item item, Context context){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child(item.getId());

        listRef.listAll()
            .addOnSuccessListener(listResult -> Glide.with(context)
                    .load(listResult.getItems().get(0))
                    .into(itemImage))
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Uh-oh, an error occurred!
                }
            });
    }
}
