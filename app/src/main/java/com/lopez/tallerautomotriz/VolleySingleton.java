package com.lopez.tallerautomotriz;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by ALEJANDRO on 20/10/2016.
 */
public class VolleySingleton {
    private static VolleySingleton instancia=null;
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;




    private VolleySingleton(){
        requestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        mImageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }
    public static VolleySingleton getInstance(){
    if (instancia==null){
        instancia=new VolleySingleton();
    }
    return instancia;
    }
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }
}
