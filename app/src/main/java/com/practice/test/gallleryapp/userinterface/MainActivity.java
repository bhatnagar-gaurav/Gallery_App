package com.practice.test.gallleryapp.userinterface;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.test.gallleryapp.GalleryApplication;
import com.practice.test.gallleryapp.R;
import com.practice.test.gallleryapp.api.GalleryImagesUtility;
import com.practice.test.gallleryapp.api.WebServiceCallbacks;
import com.practice.test.gallleryapp.api.WebServiceFragment;
import com.practice.test.gallleryapp.model.Images;
import com.practice.test.gallleryapp.model.ImagesList;
import com.practice.test.gallleryapp.utils.Utils;
import java.util.ArrayList;

public class MainActivity extends RecyclerActivity implements WebServiceCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = false; // Set this to false to disable logs.
    private GalleryApplication mApplication ;
    private static final String KEY_DATA_SET = "DATA_SET";
    private static final String TAG_WEB_SERVICE_FRAGMENT = "web_service_fragment";
    private WebServiceFragment mWebServiceFragment;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ArrayList<Images> thumbnailList;
    private GalleryAdapter galleryAdapter;
    private TextView textViewTopBar;
    private GalleryImagesUtility galleryImagesDatabaseUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DEBUG)
            Log.i(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        mApplication = GalleryApplication.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        recyclerView = getRecyclerView();
        setLayoutManager(new LinearLayoutManager(this));
        textViewTopBar = (TextView)findViewById(R.id.text_view_recycler_view_title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mWebServiceFragment = (WebServiceFragment) fragmentManager.findFragmentByTag(TAG_WEB_SERVICE_FRAGMENT);

        // If the Fragment is non-null, then it is being retained
        // over a configuration change.
        if (mWebServiceFragment == null) {
            mWebServiceFragment = new WebServiceFragment();
            fragmentManager.beginTransaction().add(mWebServiceFragment, TAG_WEB_SERVICE_FRAGMENT).commit();
        }
        if (null == savedInstanceState){
            if (mApplication.getPrefsLaunch()!= null){
                // Retrieving the Data-set from the database
                galleryImagesDatabaseUtility = new GalleryImagesUtility(mApplication);
                // Reading all the Image Values from the Database
                this.thumbnailList = galleryImagesDatabaseUtility.getAllImages();
                this.populateRecyclerView();
                Toast.makeText(mApplication,"Populated From Database",Toast.LENGTH_LONG).show();
            }
            else{
                mWebServiceFragment.start();
            }
        }

        // Restore saved state.
        if (savedInstanceState != null) {
            this.thumbnailList = savedInstanceState.getParcelableArrayList(KEY_DATA_SET);
            if (this.thumbnailList != null){
                populateRecyclerView();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (DEBUG) Log.i(TAG, "onSaveInstanceState(Bundle)");
        super.onSaveInstanceState(outState);
        if (this.thumbnailList != null){
            outState.putParcelableArrayList(KEY_DATA_SET,thumbnailList);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //-------- CALLBACK METHODS FROM ACTIVITY --------//

    @Override
    protected void onStart() {
        if (DEBUG) Log.i(TAG, "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (DEBUG) Log.i(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (DEBUG) Log.i(TAG, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (DEBUG) Log.i(TAG, "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (DEBUG) Log.i(TAG, "onDestroy()");
        super.onDestroy();
    }


    //------ CallBack methods Through Web Service Call Backs ------ //
    @Override
    public void onPreExecute() {
        // show spinner
        progressDialog = ProgressDialog.show(mApplication,"","Loading Images");

    }

    @Override
    public void onCancelled() {

    }
    // This methods is called once the DataModel is populated from the Net.
    @Override
    public void onPostExecute(Object response) {
        // hide spinner
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        // In case of Null response.
        if (response == null){
            Utils.showErrorDialog(mApplication,getString(R.string.error_unexpected));
        }
        else{
            ImagesList imagesList = (ImagesList)response;

            if(imagesList.returncode == 1){
                // Initializing the Data-set
                this.thumbnailList = imagesList.imagesList;
                // For Rendering the RecyclerView
                populateRecyclerView();
                // Initializing the Database
                this.initializeGalleryImagesUtility(mApplication);
                // Populating the Database
                for (int i = 0;i<thumbnailList.size();i++){
                    galleryImagesDatabaseUtility.addImage(thumbnailList.get(i));
                }
                galleryImagesDatabaseUtility.close();
                mApplication.setPrefsLaunch("firstTime");
            }
            else{
                Utils.showErrorDialog(mApplication, imagesList.message);
            }
        }
    }

    // Helper Method For Creating the Database.
    private void initializeGalleryImagesUtility(Context context){
            galleryImagesDatabaseUtility = new GalleryImagesUtility(context);
            galleryImagesDatabaseUtility.open();
    }

    //Helper Method for Populating the Recycler View.
    private void populateRecyclerView(){
        // Setting the Album Top Bar as per the Album ID of the Data Model.
        int albumId = this.thumbnailList.get(0).albumId;
        textViewTopBar.setText(getString(R.string.album_details).concat(" " + albumId));
        // Implementing the Listener provided in the adapter which will navigate accordingly once the item is clicked.
        galleryAdapter = new GalleryAdapter(mApplication,this.thumbnailList,new GalleryAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(Images item) {
                String imageUrl = item.getUrl();
                Intent intent = new Intent(mApplication,DetailsActivity.class);
                intent.putExtra(getString(R.string.tag_url),imageUrl);
                startActivity(intent);
            }
        });
        this.setAdapter(galleryAdapter);
    }
}
