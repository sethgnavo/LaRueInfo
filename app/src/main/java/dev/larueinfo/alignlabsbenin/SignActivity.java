package dev.larueinfo.alignlabsbenin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignActivity extends AppCompatActivity {
    private EditText eNom, eMail;
    private Button btnSubmit;
    private String MyPREFERENCES = "MyPrefs";
    private String sNom = "nameKey";
    private String sMail = "mailKey";
    private TextView txtxUser, txtMail;
    private String txtnom, txtmail;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences retrieve = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        txtnom = retrieve.getString("nameKey", null);
        txtmail = retrieve.getString("mailKey", null);

        eNom = (EditText) findViewById(R.id.usernameField);
        eNom.setText(txtnom);
        eMail = (EditText) findViewById(R.id.mailorphoneField);
        eMail.setText(txtmail);
        btnSubmit = (Button) findViewById(R.id.submitSign);

        sNom = eNom.getText().toString();
        sMail = eMail.getText().toString();
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sNom != null) {
                    String n = eNom.getText().toString();
                    String e = eMail.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("nameKey", n);
                    editor.putString("mailKey", e);
                    editor.commit();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignActivity.this);
                    builder.setTitle("Title");
                    // Set up the input
                    TextView input = new EditText(SignActivity.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setText("Félicitation ! Vous avez bien été enregistré");
                    builder.setView(input);
                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent o = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(o);
                        }
                    });
                    builder.show();
                }
            }
        });
    }
}

