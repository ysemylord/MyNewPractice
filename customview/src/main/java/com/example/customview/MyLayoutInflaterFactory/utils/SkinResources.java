package com.example.customview.MyLayoutInflaterFactory.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 原理
 * <p>
 * App包
 * <p>
 * 名称         id                值
 * black     R.color.black        #000000
 * <p>
 * skin包
 * 名称         id                值
 * black     R.color.black        #1f000000
 * <p>
 * (注意两个包中的 R.color.black的值也是不一样的)
 * <p>
 * 将App包中名为black的资源替换为skin包中名为black的资源
 */
public class SkinResources {

    private static SkinResources skinResource = new SkinResources();
    private String mSkinPkgName;
    private boolean mIsDefaultSkin = false;
    private Resources mAppResources;
    private Resources mSkinResources;

    public void init(Context context) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        mAppResources = context.getResources();
        AssetManager assetManager = AssetManager.class.newInstance();
        Method method = assetManager.getClass().getMethod("addAssetPath",String.class);
        method.invoke(assetManager,"");
        mSkinResources = new Resources(assetManager,mAppResources.getDisplayMetrics(),mAppResources.getConfiguration());
    }

    private SkinResources() {

    }

    public static SkinResources getInstance() {
        return skinResource;
    }




    /**
     * 将App包中的资源替换为skin包中的资源
     */
    public int getIdentifier(int resId) {
        String resourceName = mAppResources.getResourceName(resId);
        int resourcesIdentifier = mSkinResources.getIdentifier(resourceName, null, null);
        return resourcesIdentifier;
    }

    public int getColor(int resId) {
        if (mIsDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int identifier = getIdentifier(resId);
        if (identifier == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(identifier);
    }

    public Drawable getDrawable(int resId) {
        if (mIsDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int identifier = getIdentifier(resId);
        if (identifier == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(identifier);
    }

    public Object getBackground(int resId) {
        //background可能是颜色，也可能是drawable

        String resType = mAppResources.getResourceTypeName(resId);
        if ("color".equals(resType)) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }

    }

    public Object getSrc(int resId) {
        //src可能是颜色，也可能是drawable
        String resType = mAppResources.getResourceTypeName(resId);
        if ("color".equals(resType)) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }

    }


}
