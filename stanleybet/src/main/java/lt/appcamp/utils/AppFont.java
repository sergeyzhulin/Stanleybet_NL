
package lt.appcamp.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AppFont {
    private static Typeface normal;

    private static Typeface bold;

    private static Typeface condensed;

    private static Typeface condensedBold;

    private static Typeface light;

    private static Typeface thin;

    private static Typeface medium;

    private static Typeface regular;


    /**
     * @param topView
     * @param assetsManager
     */
    public static void setCustomFont(View topView, AssetManager assetsManager) {
        initIfNeeded(assetsManager);

        if (topView instanceof ViewGroup) {
            setCustomFont((ViewGroup) topView);
        } else if (topView instanceof TextView) {
            setCustomFont((TextView) topView);
        }
    }

    /**
     * @param topView
     * @param assetsManager
     */
    public static void setCustomFont(View topView, AssetManager assetsManager, String font) {
        initIfNeeded(assetsManager);

        if (topView instanceof ViewGroup) {
            setCustomFont((ViewGroup) topView,font);
        } else if (topView instanceof TextView) {
            setCustomFont((TextView) topView,font);
        }
    }

    /**
     * @param assetsManager
     */
    private static void initIfNeeded(AssetManager assetsManager) {
        if (normal == null || bold == null || condensed == null || light == null || thin == null
                || condensedBold == null || medium == null || regular == null) {

            normal = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Regular.ttf");
            bold = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Bold.ttf");
            condensed = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Condensed.ttf");
            condensedBold = Typeface.createFromAsset(assetsManager, "fonts/Roboto-BoldCondensed.ttf");
            light = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Light.ttf");
            thin = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Thin.ttf");
            medium = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Medium.ttf");
            regular = Typeface.createFromAsset(assetsManager, "fonts/Roboto-Regular.ttf");
        }
    }

    /**
     * @param v
     * @param len
     */
    private static void processsViewGroup(ViewGroup v, final int len) {

        for (int i = 0; i < len; i++) {
            final View c = v.getChildAt(i);
            if (c instanceof TextView) {
                setCustomFont((TextView) c);
            } else if (c instanceof ViewGroup) {
                setCustomFont((ViewGroup) c);
            }
        }
    }

    /**
     * @param v
     * @param len
     */
    private static void processsViewGroup(ViewGroup v, final int len,String font) {

        for (int i = 0; i < len; i++) {
            final View c = v.getChildAt(i);
            if (c instanceof TextView) {
                setCustomFont((TextView) c,font);
            } else if (c instanceof ViewGroup) {
                setCustomFont((ViewGroup) c,font);
            }
        }
    }

    /**
     * @param v
     */
    private static void setCustomFont(ViewGroup v) {
        final int len = v.getChildCount();
        processsViewGroup(v, len);
    }

    /**
     * @param v
     */
    private static void setCustomFont(ViewGroup v,String font) {
        final int len = v.getChildCount();
        processsViewGroup(v, len,font);
    }

    /**
     * @param c
     */
    private static void setCustomFont(TextView c) {
        Object tag = c.getTag();

        if (tag == null) {
            AppLog.e("TextView don't have tag - " + c.getText());
        }

        if (tag instanceof String) {

            // More specific in front
            if (((String) tag).contains("condensedBold")) {
                c.setTypeface(condensedBold);
                return;
            }

            // Less specific
            if (((String) tag).contains("bold")) {
                c.setTypeface(bold);
                return;
            }

            if (((String) tag).contains("condensed")) {
                c.setTypeface(condensed);
                return;
            }

            if (((String) tag).contains("light")) {
                c.setTypeface(light);
                return;
            }
            if (((String) tag).contains("thin")) {
                c.setTypeface(thin);
                return;
            }
            if (((String) tag).contains("medium")) {
                c.setTypeface(medium);
                return;
            }

            if (((String) tag).contains("regular")) {
                c.setTypeface(regular);
                return;
            }
        }
        c.setTypeface(regular);
    }

    /**
     * @param c
     */
    private static void setCustomFont(TextView c, String font) {

        // More specific in front
        if (((String) font).contains("condensedBold")) {
            c.setTypeface(condensedBold);
            return;
        }

        // Less specific
        if (((String) font).contains("bold")) {
            c.setTypeface(bold);
            AppLog.d("setting:"+font);
            return;
        }

        if (((String) font).contains("condensed")) {
            c.setTypeface(condensed);
            return;
        }

        if (((String) font).contains("light")) {
            c.setTypeface(light);
            return;
        }
        if (((String) font).contains("thin")) {
            c.setTypeface(thin);
            return;
        }
        if (((String) font).contains("medium")) {
            c.setTypeface(medium);
            return;
        }

        if (((String) font).contains("regular")) {
            c.setTypeface(regular);
            return;
        }

    }
}
