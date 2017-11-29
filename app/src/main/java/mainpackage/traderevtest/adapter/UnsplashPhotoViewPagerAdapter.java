package mainpackage.traderevtest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mainpackage.traderevtest.R;
import mainpackage.traderevtest.model.UnsplashPhoto;

/**
 * Created by deeppandya on 2017-11-28.
 */

public class UnsplashPhotoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<UnsplashPhoto> unsplashPhotos;
    private LayoutInflater mLayoutInflater;

    public UnsplashPhotoViewPagerAdapter(Context context, ArrayList<UnsplashPhoto> unsplashPhotos) {
        this.context = context;
        this.unsplashPhotos = unsplashPhotos;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        ImageView img = new ImageView(activity);
//        ((ViewPager) container).addView(img);
//        Glide.with(activity)
//                .load(unsplashPhotos.get(position).getUrls().full)
//                .into(img);
//
////        if (pos >= image.length - 1)
////            pos = 0;
////        else
////            ++pos;
//
//        return img;

        View itemView = mLayoutInflater.inflate(R.layout.unsplash_photo_pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        Glide.with(context)
                .load(unsplashPhotos.get(position).getUrls().full)
                .into(imageView);

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
