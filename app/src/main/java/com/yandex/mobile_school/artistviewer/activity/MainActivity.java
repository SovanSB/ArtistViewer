package com.yandex.mobile_school.artistviewer.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yandex.mobile_school.artistviewer.R;
import com.yandex.mobile_school.artistviewer.adapter.ArtistAdapter;
import com.yandex.mobile_school.artistviewer.art.ArtistItem;
import com.yandex.mobile_school.artistviewer.loader.ArtistListLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{

    private final String BASE_URI = "http://download.cdn.yandex.net";
    private final String ENDPOINT = "/mobilization-2016/artists.json";

    ArtistAdapter mArtistAdapter;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_main_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mArtistAdapter = new ArtistAdapter(this, null);
        mListView.setAdapter(mArtistAdapter);

        Cursor cursor = getContentResolver().query(ArtistItem.URI, null, null, null, null);
        if (cursor.getCount() == 0) {
            getLoaderManager().initLoader(R.id.artist_loader, Bundle.EMPTY, this);
        }
        else {
            mArtistAdapter.swapCursor(cursor);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().initLoader(R.id.artist_loader, Bundle.EMPTY, MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        mListView.setOnItemClickListener(null);
        super.onPause();
    }

    @Override
    protected void onResume() {
        mListView.setOnItemClickListener(this);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_refresh) {
            getLoaderManager().initLoader(R.id.artist_loader, Bundle.EMPTY, this);
            return true;
        }
        if (id == R.id.menu_drop) {
            getContentResolver().delete(ArtistItem.URI, null, null);
            mArtistAdapter.swapCursor(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.artist_loader) {
            return new ArtistListLoader(getApplicationContext(), BASE_URI, ENDPOINT);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.artist_loader) {
            mArtistAdapter.swapCursor(data);
            Toast.makeText(MainActivity.this, R.string.data_refreshed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.artist_loader) {
            mArtistAdapter.swapCursor(null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ArtistActivity.class);
        Cursor cursor = mArtistAdapter.getCursor();
        long artist_id = cursor.getLong(cursor.getColumnIndex(ArtistItem.Columns.ARTIST_ID));
        intent.putExtra(ArtistItem.Columns.ARTIST_ID, artist_id);
        this.startActivity(intent);
    }
}
