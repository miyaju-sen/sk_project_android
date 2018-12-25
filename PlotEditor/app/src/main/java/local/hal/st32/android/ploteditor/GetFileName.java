package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;

public class GetFileName {

    public static String getFileNameFromUri(@NonNull Context context, Uri uri) {
        // is null
        if (null == uri) {
            return null;
        }

        // get scheme
        String scheme = uri.getScheme();

        // get file name
        String fileName = null;
        switch (scheme) {
            case "content":
                String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
                Cursor cursor = context.getContentResolver()
                        .query(uri, projection, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        fileName = cursor.getString(
                                cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
                    }
                    cursor.close();
                }
                break;

            case "file":
                fileName = new File(uri.getPath()).getName();
                break;

            default:
                break;
        }
        return fileName;
    }
}
