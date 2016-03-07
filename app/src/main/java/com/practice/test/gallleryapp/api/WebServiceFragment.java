package com.practice.test.gallleryapp.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.practice.test.gallleryapp.R;
import com.practice.test.gallleryapp.model.Images;
import com.practice.test.gallleryapp.model.ImagesList;
import com.practice.test.gallleryapp.network.IOExceptionWithContent;
import com.practice.test.gallleryapp.network.NetworkUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * WebServiceFragment manages background task and retains itself across
 * configuration changes.
 */


public class WebServiceFragment extends Fragment {
    private static final String TAG = WebServiceFragment.class.getSimpleName();
    private static final boolean DEBUG = false; // Set this to false to disable logs.

    private WebServiceCallbacks parentCallbacks;
    private WebServiceTask webServiceTask;
    private boolean taskRunning;

    public WebServiceFragment() {
        super();
    }


    @Override
    public void onAttach(Activity activity) {
        if (DEBUG) Log.i(TAG, "onAttach(Activity)");
        super.onAttach(activity);
        if (!(activity instanceof WebServiceCallbacks)) {
            throw new IllegalStateException("Activity must implement the TaskCallbacks interface.");
        }
        // Hold a reference to the parent Activity so we can report back the task's
        // current progress and results.
        parentCallbacks = (WebServiceCallbacks) activity;

    }


    /**
     * This method is called once when the Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (DEBUG) Log.i(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Note that this method is not called when the Fragment is being
     * retained across Activity instances. It will only be called when its
     * parent Activity is being destroyed for good (such as when the user clicks
     * the back button, etc.).
     */
    @Override
    public void onDestroy() {
        if (DEBUG) Log.i(TAG, "onDestroy()");
        super.onDestroy();
        cancel();
    }

    /***The Functions of Web Service Fragment *****/
    /**
     * Start the background task.
     */
    public void start() {
        if (!taskRunning) {
            webServiceTask = new WebServiceTask();
            webServiceTask.execute();
            taskRunning = true;
        }
    }

    /**
     * Cancel the background task.
     */
    public void cancel() {
        if (taskRunning) {
            webServiceTask.cancel(false);
            webServiceTask = null;
            taskRunning = false;
        }
    }

    /**
     * Returns the current state of the background task.
     */
    public boolean isRunning() {
        return taskRunning;
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        parentCallbacks = null;
    }



    /***** BACKGROUND WEBSERVICE TASK *****/

    /**
     * A Background  task that performs work and sends across progress
     * updates along with results back to the Activity.
     */
    private class WebServiceTask extends AsyncTask<Void, Void, ImagesList> {

        @Override
        protected void onPreExecute() {
            // Proxy the call to the Activity.
            if (parentCallbacks != null){
                parentCallbacks.onPreExecute();
                taskRunning = true;
            }
        }

         /**
         * We can-not call any activity objects from the method given below
         */
        @Override
        protected ImagesList doInBackground(Void... ignore) {
            ImagesList imagesList= new ImagesList();
            JSONArray imagesResponse;
            // HTTP Get
            try {
                String response;
                List<NameValuePair> headers = new ArrayList<NameValuePair>();
                headers.add(new BasicNameValuePair("Content-Type", "application/json"));
                response = NetworkUtils.get(Constants.URL_LOADING_IMAGES, headers, null);


                // parse the json
                if (response != null)
                {
                    /*Initialize arrayList if null*/
                    imagesList.imagesList = new ArrayList<Images>();
                    imagesResponse = new JSONArray(response);
                    for (int i = 0; i < imagesResponse.length(); i++) {
                        JSONObject imagesJsonObject = imagesResponse.optJSONObject(i);
                        // Currently Populating the values for the Images whose albumID is 1
                        if (imagesJsonObject.optInt(getString(R.string.tag_album_id))==1){
                            Images image = new Images(imagesJsonObject.optInt(getString(R.string.tag_album_id)),imagesJsonObject.optInt(getString(R.string.tag_id)),imagesJsonObject.optString(getString(R.string.tag_title)),imagesJsonObject.optString(getString(R.string.tag_url)),imagesJsonObject.optString(getString(R.string.tag_thumbnail)));
                            imagesList.imagesList.add(image);
                        }
                    }

                    imagesList.returncode = 1;
                    imagesList.action = Constants.ACTION_LOAD_IMAGES;
                    imagesList.message = "Successful";
                }
                else {
                    imagesList.returncode = -1;
                    imagesList.message = "Invalid Response.Please try again.";
                }
            }
            // In case of
            catch(JSONException jsonException){
                Log.w(TAG,jsonException);
                imagesList.returncode = -1;
                if (jsonException.getMessage()!= null){
                    imagesList.message = jsonException.getMessage();
                }
                else if (jsonException.getLocalizedMessage()!= null){
                    imagesList.message = jsonException.getLocalizedMessage();
                }
                else{
                    imagesList.message = jsonException.getCause().toString();
                }
            }
            catch(IOExceptionWithContent ioExceptionWithContent){
                Log.w(TAG,ioExceptionWithContent);
                imagesList.returncode = -1;
                imagesList.message = ioExceptionWithContent.mContent;
            }
            catch (Exception e) {
                Log.w(TAG, e);
                imagesList.returncode = -1;
                imagesList.message = e.getMessage();
            }

            return imagesList;
        }

        @Override
        protected void onCancelled() {
            // Proxy the call to the Activity.
            if (parentCallbacks != null) {
                parentCallbacks.onCancelled();
                taskRunning = false;
            }
        }

        @Override
        protected void onPostExecute(ImagesList result) {
            // Proxy the call to the Activity.
            if (parentCallbacks != null){
                parentCallbacks.onPostExecute(result);
                taskRunning = false;
            }
        }
    }

}
