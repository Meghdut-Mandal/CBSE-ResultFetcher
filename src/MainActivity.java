package fetcher.newton.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {



    Button  mButton;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextView = (TextView)findViewById(R.id.textView);

        mButton = findViewById(R.id.startButton);
        final EditText rollInput=findViewById(R.id.rollinput);
        String roll=rollInput.getText().toString();
        final EditText sch=findViewById(R.id.schInput);
        final EditText cntr= findViewById(R.id.centerInput);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Document post = Jsoup.connect("http://cbseresults.nic.in/class12zpq/class12th18.htm")
                            .data("regno", "6625902")
                            .data("sch", "08423")
                            .data("cno", "6219").post();

                    mTextView.setText(Html.escapeHtml(post.html()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
