package gradle.udacity.com.jokedisplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static String ARG_JOKE = "arg_joke";

    private TextView tv_joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        tv_joke = (TextView) findViewById(R.id.tv_joke);

        if (getIntent().getStringExtra(ARG_JOKE) != null) {
            String joke = getIntent().getStringExtra(ARG_JOKE);
            tv_joke.setText(joke);
        }

    }
}
