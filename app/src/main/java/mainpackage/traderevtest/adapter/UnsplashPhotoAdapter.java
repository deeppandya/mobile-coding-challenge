package mainpackage.traderevtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import mainpackage.traderevtest.R;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.utils.GlideApp;

/**
 * Created by deeppandya on 2017-11-28.
 */

public class UnsplashPhotoAdapter extends RecyclerView.Adapter<UnsplashPhotoAdapter.ViewHolder> {
    private List<UnsplashPhoto> unsplashPhotos;
    private OnItemClickListener onItemClickListener;

    public UnsplashPhotoAdapter(List<UnsplashPhoto> unsplashPhotos, OnItemClickListener onItemClickListener) {
        this.unsplashPhotos = unsplashPhotos;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unsplash_photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UnsplashPhoto unsplashPhoto = unsplashPhotos.get(position);
        holder.bind(unsplashPhoto, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return unsplashPhotos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }

        void bind(UnsplashPhoto unsplashPhoto, OnItemClickListener onItemClickListener) {
            GlideApp.with(itemView.getContext())
                    .load(unsplashPhoto.getUrls().getThumb())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .fallback(R.drawable.ic_error)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(imgPhoto);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(itemView, unsplashPhoto, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, UnsplashPhoto unsplashPhoto, int position);
    }
}
