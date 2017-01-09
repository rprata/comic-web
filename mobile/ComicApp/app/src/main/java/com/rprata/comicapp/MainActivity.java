package com.rprata.comicapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rprata.comicapp.activities.ComicActivity;
import com.rprata.comicapp.activities.ComicAdapter;
import com.rprata.comicapp.activities.listeners.RecyclerTouchListener;
import com.rprata.comicapp.core.Comic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Comic> comicList = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private HTTPConsumer httpConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Updating List", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (httpConsumer != null)
                    httpConsumer.doGET();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mAdapter = new ComicAdapter(comicList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Comic comic = comicList.get(position);
                Intent i = new Intent(MainActivity.this, ComicActivity.class);
                i.putExtra(Comic.MESSAGE_COMIC, comic);
                MainActivity.this.startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        httpConsumer = new HTTPConsumer();
        httpConsumer.initVolley(this);

        httpConsumer.doGET();


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
            Toast.makeText(getApplicationContext(), "This is a comic shop", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HTTPConsumer {

        private static final String SERVER = "http://192.168.0.212:8080/api/comics";

        public void initVolley(Context applicationContext) {
            mRequestQueue = Volley.newRequestQueue(applicationContext);
        }

        public void doGET() {
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    SERVER,
                    null,
                    new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            comicList.clear();
                            try {
                                JSONArray jsonArray = new JSONArray(response.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Comic comic = new Comic();
                                    comic.setTitle(obj.get("title").toString());
                                    comic.setPicture_url(obj.get("picture_url").toString());
                                    comic.setDate_of_publication(obj.get("date_of_publication").toString());
                                    comic.setShort_description(obj.getString("short_description").toString());
                                    comic.setEdition(obj.getInt("edition_number"));
                                    comic.setPrice(obj.getDouble("price"));
                                    comicList.add(comic);
                                }
                                mAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "HTTP Error. Please, press refresh button.", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "HTTP Error. Please, check your connection.", Toast.LENGTH_SHORT).show();
                        }
                    });
            mRequestQueue.add(request);
        }
    }
}