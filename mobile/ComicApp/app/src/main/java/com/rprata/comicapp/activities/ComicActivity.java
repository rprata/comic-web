package com.rprata.comicapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.rprata.comicapp.R;
import com.rprata.comicapp.core.Comic;
import com.squareup.picasso.Picasso;

/**
 * Created by rprata on 09/01/17.
 */
public class ComicActivity extends Activity {

    private ImageView picture;
    private TextView title, short_description, date_of_publication, edition_number, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic_info);

        picture = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        short_description = (TextView) findViewById(R.id.short_description);
        date_of_publication = (TextView) findViewById(R.id.date_of_publication);
        edition_number = (TextView) findViewById(R.id.edition_number);
        price = (TextView) findViewById(R.id.price);

        Intent i = getIntent();
        Comic comic = (Comic) i.getParcelableExtra(Comic.MESSAGE_COMIC);

        Picasso.with(this)
                .load(comic.getPicture_url())
                .into(picture);
        title.setText(comic.getTitle());
        short_description.setText(comic.getShort_description());
        date_of_publication.setText("Publication date: " + comic.getDate_of_publication().substring(8,10)
                                                         + "/"
                                                         + comic.getDate_of_publication().substring(5,7)
                                                         + "/"
                                                         + comic.getDate_of_publication().substring(0, 4));
        edition_number.setText("Edition: " + comic.getEdition());
        price.setText("Price: $" + String.format("%.2f", comic.getPrice()).replace(',', '.'));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
