package mainpackage.traderevtest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import mainpackage.traderevtest.R;
import mainpackage.traderevtest.iview.UnsplashPhotoDetailView;
import mainpackage.traderevtest.model.UnsplashPhoto;
import mainpackage.traderevtest.utils.GlideApp;

/**
 * Created by deeppandya on 2017-11-28.
 */

public class UnsplashPhotoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<UnsplashPhoto> unsplashPhotos;
    private LayoutInflater mLayoutInflater;
    private UnsplashPhotoDetailView unsplashPhotoDetailView;

    public UnsplashPhotoViewPagerAdapter(Context context, ArrayList<UnsplashPhoto> unsplashPhotos,UnsplashPhotoDetailView unsplashPhotoDetailView) {
        this.context = context;
        this.unsplashPhotos = unsplashPhotos;
        this.unsplashPhotoDetailView=unsplashPhotoDetailView;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.unsplash_photo_pager_item, container, false);

        UnsplashPhoto unsplashPhoto=unsplashPhotos.get(position);

        ImageView imgMain = itemView.findViewById(R.id.img_main);

        TextView tvDimension=itemView.findViewById(R.id.tv_photo_dimension);
        TextView tvCreatedDate=itemView.findViewById(R.id.tv_created_date);
        TextView tvDescription=itemView.findViewById(R.id.tv_photo_description);

        GlideApp.with(context)
                .load(unsplashPhoto.getUrls().getFull())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .fallback(R.drawable.ic_error)
                .into(imgMain);

        tvDimension.setText(String.format(context.getString(R.string.dimension_text),unsplashPhoto.getWidth(),unsplashPhoto.getHeight()));
        tvCreatedDate.setText(String.format(context.getString(R.string.created_date_text),unsplashPhoto.getCreatedAt()!=null?unsplashPhoto.getCreatedAt():""));
        tvDescription.setText(unsplashPhoto.getDescription()!=null?unsplashPhoto.getDescription():"");

        unsplashPhotoDetailView.setActionBarForPhoto(unsplashPhoto);

        container.addView(itemView);

        return itemView;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
