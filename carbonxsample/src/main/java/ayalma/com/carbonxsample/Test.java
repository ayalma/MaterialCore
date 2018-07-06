package ayalma.com.carbonxsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mat.widget.Button;
import mat.widget.LinearLayout;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/



        Button btn = findViewById(R.id.ok);


        LinearLayout view = findViewById(R.id.view);
        view.setClickable(true);

        btn.setOnClickListener(v -> {
            view.animateVisibility( view.isVisible() ? View.INVISIBLE : View.VISIBLE);

        });

        view.setOnClickListener((v)->
                btn.animateVisibility( btn.isVisible() ? View.INVISIBLE : View.VISIBLE));

    }

   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MaterialContextWrapper.wrap(newBase));
    }
*/

}
