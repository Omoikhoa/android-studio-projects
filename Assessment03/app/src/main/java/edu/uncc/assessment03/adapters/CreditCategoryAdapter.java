// CreditCategoryAdapter.java
package edu.uncc.assessment03.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.uncc.assessment03.R;
import edu.uncc.assessment03.models.CreditCategory;

public class CreditCategoryAdapter extends ArrayAdapter<CreditCategory> {
    public CreditCategoryAdapter(@NonNull Context context, @NonNull List<CreditCategory> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.credit_category_item, parent, false);
        }

        CreditCategory category = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageViewCategory);
        TextView textView = convertView.findViewById(R.id.textViewCategory);

        if (category != null) {
            imageView.setImageResource(category.getImageResourceId());
            textView.setText(category.getName());
        }

        return convertView;
    }
}