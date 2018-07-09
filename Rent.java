package madsmortensen.studior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Rent extends AppCompatActivity {

    private Button saveStudio;
    private EditText studioNameTextField;
    private EditText studioInfoTextField;
    private EditText studioAdressTextField;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataBaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        saveStudio = findViewById(R.id.saveStudio);
        studioNameTextField = findViewById(R.id.studioNameTextField);
        studioInfoTextField = findViewById(R.id.studioInfoTextField);
        studioAdressTextField = findViewById(R.id.studioAdressTextField);
        mDatabase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDatabase.getReference("Studios");
        saveStudio = findViewById(R.id.saveStudio);


        saveStudio.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                    Map<String, FirebaseMarker> newStudioAdd = new HashMap<>();

                    newStudioAdd.put(studioNameTextField.getText().toString(),
                            new FirebaseMarker(
                                    studioNameTextField.getText().toString(),
                                    studioInfoTextField.getText().toString(),
                                    studioAdressTextField.getText().toString(),
                                    24, 265
                            )
                    );

                    mDataBaseRef.setValue(newStudioAdd);

              Intent intent = new Intent(Rent.this, MapsActivity.class);
              startActivity(intent);

            }
        });

    }
}