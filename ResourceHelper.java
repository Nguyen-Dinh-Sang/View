package android.support.v4.app.utils;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;

public class ResourceHelper {
    public static final String TYPE_ID = "id";
    public static final String TYPE_DRAWABLE = "drawable";
    public static final String TYPE_RAW = "raw";

    public static Drawable getDrawable(Context context, String name, Drawable def) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        if (resourceId == 0)
            return def;
        return context.getResources().getDrawable(resourceId);
    }

    public static int getColor(Context context, String name, int def) {
        int resourceId = context.getResources().getIdentifier(name, "color", context.getPackageName());
        if (resourceId == 0)
            return def;
        return context.getResources().getColor(resourceId);
    }

    public static Drawable getMipmap(Context context, String name, Drawable def) {
        int resourceId = context.getResources().getIdentifier(name, "mipmap", context.getPackageName());
        if (resourceId == 0)
            return def;
        return context.getResources().getDrawable(resourceId);
    }

    public static String getString(Context context, String name, String def) {
        int resourceId = getIdFromName(context, name, "string", -1);
        if (resourceId == -1)
            return def;
        return context.getResources().getString(resourceId);
    }

    public static int getIdFromName(Context context, String name, int def) {
        int resourceId = context.getResources().getIdentifier(name, "id", context.getPackageName());
        if (resourceId == 0)
            return def;
        return resourceId;
    }

    public static int getIdFromName(Context context, String name, String type, int def) {
        int resourceId = context.getResources().getIdentifier(name, type, context.getPackageName());
        if (resourceId == 0)
            return def;
        return resourceId;
    }

    public static String getTypeId(Context context, int id) {
        try {
            return context.getResources().getResourceTypeName(id);
        } catch (NotFoundException e) {
            return null;
        }


    }

    public static boolean checkTypeId(Context context, int id, String type) {
        try {
            return context.getResources().getResourceTypeName(id).equals(type);
        } catch (NotFoundException e) {
            return false;
        }
    }
}
