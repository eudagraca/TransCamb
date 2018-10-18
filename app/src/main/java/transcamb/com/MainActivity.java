package transcamb.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText mConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CodigoPorPais.countryNames));

        mConfirmacao = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CodigoPorPais.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = mConfirmacao.getText().toString().trim();

                if (number.isEmpty() || number.length() != 9) {
                    mConfirmacao.setError("Número inválido");
                    mConfirmacao.requestFocus();
                    return;
                }

                String numero = "+" + code + number;

                Intent intent = new Intent(MainActivity.this, VericicarTelefoneActivity.class);
                intent.putExtra("numero", numero);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
