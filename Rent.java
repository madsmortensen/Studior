package madsmortensen.studior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;



public class Rent extends AppCompatActivity {

    Button saveStudio = findViewById(R.id.saveStudio);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

    }
}