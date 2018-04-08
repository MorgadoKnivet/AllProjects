package workay.development.workayparceiros.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import workay.development.workayparceiros.Class.Utilitarios;
import workay.development.workayparceiros.R;

public class LoginSMS extends AppCompatActivity {

    private static final String TAG = "PhoneAuth";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mVerificationId;


    private EditText num;
    private TextView but;
    private TextView aviso;

    Typeface font ;
    Typeface fontRegular;
    Button butLoginSMS;


    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onStart() {
        super.onStart();
     //  mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sms);
        //Note that this will not work on emulator, this requires a real device

        mAuth = FirebaseAuth.getInstance();
        num = (EditText)findViewById(R.id.numLoginSMS);
        but = (TextView)findViewById(R.id.enviarCodigoSMS);
        aviso = (TextView)findViewById(R.id.textViewComentarioTelaSMS);
        butLoginSMS = (Button)findViewById(R.id.butLoginSMS) ;

        font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        fontRegular = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");

        aviso.setTypeface(font);
        num.setTypeface(fontRegular);
        but.setTypeface(fontRegular);
        butLoginSMS.setTypeface(fontRegular);

        ImageView returnTop = (ImageView)findViewById(R.id.butReturnTopLoginSMS);



        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int aux = View.GONE;
                int aux2 = View.VISIBLE;

                if (butLoginSMS.getVisibility() == aux ){
                    aviso.setText("Enviamos um SMS para o seu celular, coloque o código abaixo");

                    but.setText("Reenviar Código");

                    butLoginSMS.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"SendCode",Toast.LENGTH_SHORT).show();
                 //   sendCode();

                }else
                    if (butLoginSMS.getVisibility() == aux2 ){
                    //    resendCode();
                        Toast.makeText(getApplicationContext(),"ReSendCode",Toast.LENGTH_SHORT).show();
                        butLoginSMS.setVisibility(View.GONE);
                        aviso.setText("Qual é o número do seu telefone cadastrado na Workay?");
                        but.setText("Enviar código por SMS");
                }


            }
        });

        butLoginSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //   verifyCode();

            }
        });

        returnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void fecharTeclado(View view){
        Utilitarios utilitarios = new Utilitarios();
        utilitarios.fecharTeclado(LoginSMS.this,view);
    }

    public void onBackPressed(){

        int aux = View.GONE;
        int aux2 = View.VISIBLE;

        if (butLoginSMS.getVisibility() == aux ){
            startActivity(new Intent(getApplicationContext(),TelaInicial.class));
        }

        if (butLoginSMS.getVisibility() == aux2 ){
            butLoginSMS.setVisibility(View.GONE);
            aviso.setText("Qual é o número do seu telefone cadastrado na Workay?");
            but.setText("Enviar código por SMS");


        }

    }


    public void sendCode(){
        String phoneNumber = num.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        Toast.makeText(getApplicationContext(),"Yep",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Toast.makeText(getApplicationContext(),"Nop Invalid",Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Toast.makeText(getApplicationContext(),"Nop muito sms",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        Toast.makeText(getApplicationContext(),"onCodeSent",Toast.LENGTH_SHORT).show();
                      //  verifyButton.setEnabled(true);
                      //  sendButton.setEnabled(false);
                      //  resendButton.setEnabled(true);
                    }
                };
    }

    public void verifyCode() {

        String code = num.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void resendCode() {

        String phoneNumber = num.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }
}
