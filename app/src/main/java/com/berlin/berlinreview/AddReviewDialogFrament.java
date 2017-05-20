package com.berlin.berlinreview;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.berlin.berlinreview.network.DataFactoryInetntService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by prime on 5/20/17.
 */

public class AddReviewDialogFrament extends DialogFragment {

    private static final String TAG = AddReviewDialogFrament.class.getCanonicalName();

    /**
     * Create a new instance of AddReviewDialogFrament
     */
    static AddReviewDialogFrament newInstance() {
        return new AddReviewDialogFrament();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppCompatAlertDialogStyle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        final Button button = (Button) v.findViewById(R.id.buttonSubmit);
        final EditText edtAuthor = (EditText) v.findViewById(R.id.input_name);
        final EditText edtMessage = (EditText) v.findViewById(R.id.input_message);
        final EditText edtTitle = (EditText) v.findViewById(R.id.input_title);
        final EditText edtCountry = (EditText) v.findViewById(R.id.input_place);
        final RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

        final String rating = String.valueOf(ratingBar.getNumStars());


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    final String author = edtAuthor.getText().toString();
                    final String title = edtTitle.getText().toString();
                    final String message = edtMessage.getText().toString();
                    final String country = edtCountry.getText().toString();


                    JSONObject jRespObj = new JSONObject();
                    jRespObj.put("rating", rating);
                    jRespObj.put("title", title);
                    jRespObj.put("message", message);
                    jRespObj.put("author", author);
                    jRespObj.put("reviewerCountry", country);
                    jRespObj.put("date",new SimpleDateFormat("MMM DD YYYY", Locale.getDefault()).format(new Date()));

                    Log.d(TAG, jRespObj.toString());


                    String url = getResources().getString(R.string.url_part_one);
                    DataFactoryInetntService.startActionUpload(getActivity(), url, jRespObj.toString());

                    Toast.makeText(getActivity(),"Review has been Sent",Toast.LENGTH_LONG).show();

                    getDialog().dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        return v;
    }


}