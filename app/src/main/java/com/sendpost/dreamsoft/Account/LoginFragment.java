package com.sendpost.dreamsoft.Account;

import static android.content.Context.MODE_PRIVATE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.model.UserModel;
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.IntroActivity;
import com.sendpost.dreamsoft.R;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LoginFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;
    LoginModel loginmodel = new LoginModel();
    Activity context;
    EditText number_et,otp_et;
    TextView get_otp_btn,submit_otp_btn;

    String verificationId;
    public static PhoneAuthProvider.ForceResendingToken token;
    public static PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    CountryCodePicker ccp;
    TextView tvCountryCode;
    UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        number_et = view.findViewById(R.id.number_et);
        otp_et = view.findViewById(R.id.otp_et);
        get_otp_btn = view.findViewById(R.id.get_otp_btn);
        submit_otp_btn = view.findViewById(R.id.submit_otp_btn);
        tvCountryCode = view.findViewById(R.id.country_code);

        get_otp_btn.setActivated(false);
        submit_otp_btn.setActivated(false);

        mAuth = FirebaseAuth.getInstance();
        LoginManager.getInstance().logOut();
        sharedPreferences = context.getSharedPreferences(Variables.PREF_NAME, MODE_PRIVATE);

        ccp = new CountryCodePicker(view.getContext());
        ccp.setCountryForNameCode(ccp.getDefaultCountryNameCode());
        tvCountryCode.setText(ccp.getDefaultCountryCodeWithPlus());
        tvCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencountry();
            }
        });

//        view.findViewById(R.id.facebook_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginwithfacebook(v);
//            }
//        });

//        view.findViewById(R.id.google_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginwithgoogle(v);
//            }
//        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                if (getContext() != null) {
                    Toast.makeText(getContext(), "OTP Timeout, Please Re-generate the OTP Again.", Toast.LENGTH_SHORT).show();
                }
                get_otp_btn.setActivated(true);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                get_otp_btn.setActivated(false);
                verificationId = s;
                token = forceResendingToken;
                Toast.makeText(context, context.getString(R.string.otp_send_success), Toast.LENGTH_SHORT).show();
                Functions.cancelLoader();
                startCountdown();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
                otp_et.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Functions.cancelLoader();
            }
        };

        number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 9 && !isTimerRunning){
                    get_otp_btn.setActivated(true);
                }else{
                    get_otp_btn.setActivated(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 5){
                    submit_otp_btn.setActivated(true);
                }else {
                    submit_otp_btn.setActivated(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        get_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (get_otp_btn.isActivated()){
                    Functions.showLoader(context);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            ccp.getSelectedCountryCodeWithPlus()+number_et.getText().toString(),        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            getActivity(),               // Activity (for callback binding)
                            mCallbacks);
                }
            }
        });

        submit_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submit_otp_btn.isActivated()){
                    verifyAuth(PhoneAuthProvider.getCredential(verificationId, otp_et.getText().toString()));
                }
            }
        });
    }

    int sec = 30;
    boolean isTimerRunning = false;
    private void startCountdown() {
        sec = 30;
        isTimerRunning = true;
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sec--;
                get_otp_btn.setText("("+sec+")");
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                if (getContext() != null){
                    get_otp_btn.setText(getContext().getString(R.string.get_otp));
                    get_otp_btn.setActivated(true);
                }
            }
        }.start();
    }


    @SuppressLint("WrongConstant")
    public void opencountry() {
        ccp.showCountryCodePickerDialog();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                tvCountryCode.setText(ccp.getSelectedCountryCodeWithPlus());
            }
        });
    }


    private void verifyAuth(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                loginmodel.phoneNo = ccp.getSelectedCountryCodeWithPlus()+number_et.getText().toString();
                firebaseUser = mAuth.getCurrentUser();
                assert firebaseUser != null;
                insertLoginData(firebaseUser.getUid(), "phone","");
            } else {
                Toast.makeText(getActivity(), "The verification code you have been entered incorrect !", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private CallbackManager mCallbackManager;
    public void loginwithfacebook(View view) {
        try {
            LoginManager.getInstance().logOut();
        }catch (Exception e){}
        LoginManager.getInstance()
                .logInWithReadPermissions(context,
                        Arrays.asList("public_profile", "email"));


        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("fbnjvdbndgfb0",loginResult.toString());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("fbnjvdbndgfb20","loginResult.toString()");
                Functions.showToast(context, getString(R.string.login_cancel));
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("fbnjvdbndgfb10",error.toString());
                Functions.showToast(context, getString(R.string.login_error) + error.toString());
            }

        });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        // if user is login then this method will call and
        // facebook will return us a token which will user for get the info of user
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Functions.showLoader(context);
                            final String id = Profile.getCurrentProfile().getId();
                            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                                    Functions.cancelLoader();

                                    String fname = "" + user.optString("first_name");
                                    String lname = "" + user.optString("last_name");
                                    String email = "" + user.optString("email");
                                    String auth_token = token.getToken();
                                    String image = "https://graph.facebook.com/"+id+"/picture?width=500";

                                    loginmodel = new LoginModel();

                                    loginmodel.fname = Functions.removeSpecialChar(fname);
                                    loginmodel.email = email;
                                    loginmodel.lname = Functions.removeSpecialChar(lname);
                                    loginmodel.socailId = id;
                                    loginmodel.picture = image;
                                    loginmodel.authTokon = auth_token;

                                    insertLoginData("" + id, "facebook", auth_token);

                                }
                            });

                            // here is the request to facebook sdk for which type of info we have required
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "last_name,first_name,email");
                            request.setParameters(parameters);
                            request.executeAsync();
                        } else {
                            Functions.cancelLoader();
                            Functions.showToast(context, getString(R.string.authentication_failed));
                        }

                    }
                });
    }

    GoogleSignInClient mGoogleSignInClient;
    public void loginwithgoogle(View view) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);


        try {
            mGoogleSignInClient.signOut();
        }catch (Exception e){}

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        if (account != null) {

            String id = ""+account.getId();
            String fname = "" + account.getGivenName();
            String lname = "" + account.getFamilyName();
            String email = ""+account.getEmail();
            String auth_tokon = ""+account.getIdToken();
            String image=""+account.getPhotoUrl();

            loginmodel = new LoginModel();
            loginmodel.fname = Functions.removeSpecialChar(fname);
            loginmodel.email = email;
            loginmodel.lname = Functions.removeSpecialChar(lname);
            loginmodel.socailId = id;
            loginmodel.authTokon = auth_tokon;
            loginmodel.picture=image;

            String auth_token = "" + account.getIdToken();
            insertLoginData("" + id, "google", auth_token);
        } else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            resultCallbackForGoogle.launch(signInIntent);
        }
    }

    ActivityResultLauncher<Intent> resultCallbackForGoogle = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                    }
                }
            });

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String id = ""+account.getId();
                String fname = "" + account.getGivenName();
                String lname = "" + account.getFamilyName();
                String auth_token = ""+account.getIdToken();
                String email = ""+account.getEmail();
                String image=""+account.getPhotoUrl();

                // if we do not get the picture of user then we will use default profile picture


                loginmodel = new LoginModel();

                loginmodel.fname = fname;
                loginmodel.email = email;
                loginmodel.lname = lname;
                loginmodel.socailId = id;
                loginmodel.picture=image;
                loginmodel.authTokon = account.getIdToken();

                insertLoginData("" + id, "google", auth_token);

            }
        } catch (ApiException e) {
            Toast.makeText(context, "E -> "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertLoginData(String socialId, String social, final String authtoken) {
        if (!loginmodel.lname.equals("")){
            loginmodel.fname = loginmodel.fname+" "+loginmodel.lname;
        }

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("social_id", socialId);
            parameters.put("social", "" + social);
            parameters.put("auth_token", "" + authtoken);
            parameters.put("email", "" + loginmodel.email);
            parameters.put("number", "" + loginmodel.phoneNo);
            parameters.put("profile_pic", "" + loginmodel.picture);
            parameters.put("name", "" + loginmodel.fname);
            parameters.put("device_token", Variables.DEVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userViewModel.login(parameters).observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse != null){
                    Functions.cancelLoader();
                    if (userResponse.code == Constants.SUCCESS){
                        parseLoginData(userResponse.userModel);
                    }else {
                        Functions.showToast(context,userResponse.message);
                    }
                }
            }
        });
    }

    public void parseLoginData(UserModel userModel) {
        Functions.saveUserData(userModel, context);
        if (userModel.getName() == null || userModel.getName().equals("") || userModel.getName().equals("null")){
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
            transaction.addToBackStack(null);
            transaction.replace(R.id.login_fragment, new EditProfileFragment()).commit();
        }else {
            Intent intent=new Intent(context, IntroActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}