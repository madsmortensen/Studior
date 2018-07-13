package madsmortensen.studior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudio extends AppCompatActivity {

    private Button saveStudio;
    private EditText studioNameTextField, studioInfoTextField, studioAdressTextField, studioLatitudeTextField, studioLongitudeTextField;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataBaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudio);
        saveStudio = findViewById(R.id.saveStudio);
        studioNameTextField = findViewById(R.id.studioNameTextField);
        studioInfoTextField = findViewById(R.id.studioInfoTextField);
        studioAdressTextField = findViewById(R.id.studioAdressTextField);
        studioLatitudeTextField = findViewById(R.id.studioLatitudeTextField);
        studioLongitudeTextField = findViewById(R.id.studioLongitudeTextField);
        mDatabase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDatabase.getReference("Studios");
        saveStudio = findViewById(R.id.saveStudio);

        saveStudio.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(studioNameTextField.getText().toString().equals(""))
                {
                    Toast.makeText(AddStudio.this, "Studio name is empty, please enter your studios name", Toast.LENGTH_SHORT).show();
                }

                else if(studioInfoTextField.getText().toString().equals(""))
                {
                    Toast.makeText(AddStudio.this, "Studio info is empty, please enter your studios info", Toast.LENGTH_SHORT).show();
                }

                else if(studioAdressTextField.getText().toString().equals(""))
                {
                    Toast.makeText(AddStudio.this, "Studio adress is empty, please enter your studios adress", Toast.LENGTH_SHORT).show();
                }

                else if(studioLatitudeTextField.getText().toString().equals(""))
                {
                    Toast.makeText(AddStudio.this, "Studio latitude is empty, please enter your studios latitude coordinate", Toast.LENGTH_SHORT).show();
                }

                else if(studioLongitudeTextField.getText().toString().equals(""))
                {
                    Toast.makeText(AddStudio.this, "Studio longitude is empty, please enter your studios longitude coordinate", Toast.LENGTH_SHORT).show();
                }

                else{

                    FirebaseMarker firebaseMarker = new FirebaseMarker(
                            studioNameTextField.getText().toString(),
                            studioInfoTextField.getText().toString(),
                            studioAdressTextField.getText().toString(),
                            Double.parseDouble(studioLatitudeTextField.getText().toString()),
                            Double.parseDouble(studioLongitudeTextField.getText().toString()));
                    mDataBaseRef.push().setValue(firebaseMarker);

                        Intent intent = new Intent(AddStudio.this, MapsActivity.class);
                        startActivity(intent);

                }
            }
        });

    }
}
