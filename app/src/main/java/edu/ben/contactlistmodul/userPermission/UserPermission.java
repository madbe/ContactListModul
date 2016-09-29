package edu.ben.contactlistmodul.userPermission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * User Permission as three permission method to get access to the contact phone
 * list. also we need to add in the manifest.xml the
 * "android.permission.READ_CONTACTS" in the user-permission.
 *
 */
public class UserPermission {

    private static final int REQUEST_PERMISSION_CODE = 100;

    public static boolean checkContactsPermission(View pView) {
        Context context = pView.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                //if we have permission we call the method who need permission
                return true;
            } else {
                //need to request the permission
                return false;
            }
        } else {
            //We already have the permission:
            return true;
        }
    }


    /**
     * this method will be called only if the user have sdk above M
     * @param activity we need to pass activity to use the requestPermission
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestContactsPermission(Activity activity) {
        requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                REQUEST_PERMISSION_CODE);
    }


    /**
     * This Method called from the onRequestPermissionsResult in the Activity or the
     * Fragment. passing this parameters:
     * @param pView         the View/ Activity/ Fragment in Which we want to get the result
     * @param pMessage      the message for the user
     * @param requestCode   the request code from the onRequestPermissionsResult
     * @param grantResults  the grant Result from the onRequestPermissionsResult
     * @return              true if we get permission false if we don't
     */
    public static boolean getResultPermission(final View pView, String pMessage, int requestCode, @NonNull int[] grantResults) {
        //View view = activity.getCurrentFocus();
        Context context =  pView.getContext();
        final Activity activity = (Activity) context;
        //Get the result:
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //we have permission. call the method who need permission
                return true;
            } else {
                //we don't have permission so we alert the user that we need is permission
                Snackbar.make(pView, pMessage, Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestContactsPermission(activity);
                    }
                }).show();
            }
        }
        return false;
    }
}
