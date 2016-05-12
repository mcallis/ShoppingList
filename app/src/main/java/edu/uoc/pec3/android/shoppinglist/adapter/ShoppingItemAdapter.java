package edu.uoc.pec3.android.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uoc.pec3.android.shoppinglist.R;
import edu.uoc.pec3.android.shoppinglist.database.model.ShoppingItem;

/**
 * Created by Marc on 18/08/2015.
 */
public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {

    private Context context;
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> shoppingItems) {
        super(context, R.layout.list_shopping_item);
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    @Override
    public int getCount() {
        return shoppingItems.size();
    }

    @Override
    public ShoppingItem getItem(int position) {
        return  shoppingItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shoppingItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_shopping_item, parent, false);
            viewHolder.mItemName = (TextView) view.findViewById(R.id.textViewItemName);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        // Set text with the item name
        viewHolder.mItemName.setText(shoppingItems.get(position).getName());

        return view;
    }

    static class ViewHolder {
        protected TextView mItemName;
    }
}
