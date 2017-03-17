package com.nikki.bbli.r2_animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by Ideal on 3/9/2017.
 */

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    Context mContext;
    OnItemClickListener mItemClickListener;

    // 2
    public TravelListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Place place = new PlaceData().placeList().get(position);
        holder.placeName.setText(place.name);
        //Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);
        //Glide.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);
        new ImageLoader().executeOnExecutor(holder,place,mContext);
    }

    @Override
    public int getItemCount() {
        return new PlaceData().placeList().size();
    }

    private class ImageLoader extends AsyncTask<Object, void, Bitmap){
            private ViewHolder viewholder;
            private Place place;
            private Context context;

    @Override
    protected Bitmap doInBackground(Object... params) {
            viewholder = (ViewHolder) params[0];
            place = (Place) params[1];
            context = (Context) params[2];
            return place.getImageResourceId(context);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (viewholder.position == position) {
                viewholder.placeImage.setImageResource(result);
                Bitmap photo = BitmapFactory.decodeResource(context.getResources(), result);
                Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        int bgColor = palette.getMutedColor(context.getResources().getColor(android.R.color.black));
                        viewHolder.placeNameHolder.setBackgroundColor(bgColor);
                    }
                });
            }
          }
    }
    // 3
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
