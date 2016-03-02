package me.fmon.android.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class _ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public _ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}