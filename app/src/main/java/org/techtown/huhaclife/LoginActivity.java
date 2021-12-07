package org.techtown.huhaclife;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class LoginActivity extends AppCompatActivity {
    public ViewPager viewPager;
    private BottomSheetBehavior sheetBehavior;
    private ConstraintLayout layoutBottomSheet;
    private TextView logo;
    private TabLayout tabLayout;
    private LottieAnimationView earth;
    private ImageView fb,google,kakao;
    private FirebaseAuth root;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private float v=0; //애니메이션 투명도 지정
    final TablelayoutAdapter adapter=new TablelayoutAdapter(getSupportFragmentManager());
    private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutBottomSheet=(ConstraintLayout) findViewById(R.id.bottomsheet);
        sheetBehavior=BottomSheetBehavior.from(layoutBottomSheet);
        logo=(TextView)findViewById(R.id.text);
        tabLayout=findViewById(R.id.tab_layout1);
        viewPager=findViewById(R.id.view_pager);
        earth=(LottieAnimationView)findViewById(R.id.img_earth);
        kakao=(ImageView)findViewById(R.id.fab_kakao);
        google=(ImageView)findViewById(R.id.fab_google);
        fb=(ImageView)findViewById(R.id.fab_feacebook);
        firebaseDatabase=FirebaseDatabase.getInstance();
        root=FirebaseAuth.getInstance();
        reference=firebaseDatabase.getReference();


        Function2<OAuthToken,Throwable, Unit> callback=new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                //token이 전달 되면 성공한 것
                if(oAuthToken!=null){
                    updateSignIn(1,null);
                    Toast.makeText(getApplicationContext(),"token값 전달 받음. ",Toast.LENGTH_SHORT).show();
                    System.out.println(oAuthToken);
                }
                if(throwable!=null){
                    Toast.makeText(getApplicationContext(),"token값 전달 받지 못함.",Toast.LENGTH_SHORT).show();
                    System.out.println(throwable);
                }
                return null;
            }
        };


        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSingnClient= GoogleSignIn.getClient(this,gso);

        //test
        mGoogleSingnClient.revokeAccess().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });


        //액티비티 콜백함수
        resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if(result.getResultCode()==RESULT_OK){
                        Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try{
                            GoogleSignInAccount account=task.getResult(ApiException.class);
                            System.out.println(account);
                            firebaseAuthWithGoogle(account.getIdToken());
                            updateSignIn(0, result);
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }
                }

                else{
                    System.out.println(result.getResultCode());
                    System.out.println("GoogleSingIn3");
                }

            }
        });

        //kakao로그인
        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카카오톡이 설치되어 있는지 유무 파악
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())){
                    UserApiClient.getInstance().loginWithKakaoTalk(getApplicationContext(),callback);
                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(getApplicationContext(),callback);
                }
            }
        });


        //google로그인

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent =mGoogleSingnClient.getSignInIntent();
                signInIntent.putExtra("callType",1001);
                System.out.println(signInIntent.getIntExtra("callType",11));
                resultLauncher.launch(signInIntent);

            }
        });

        //어댑터에 뷰 객체 추가하고 뷰페이지에 어댑터 달기
        setViewPager(viewPager);

        //tablayout에 항목 추가가
        tabLayout.addTab(tabLayout.newTab().setText("로그인"));
        tabLayout.addTab(tabLayout.newTab().setText("회원가입"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        //뷰페이저에 tablayout달기
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //애니메이션 효과 셋팅
        tabLayout.setTranslationY(300);
        logo.setTranslationY(-700);
        tabLayout.setAlpha(v);
        logo.setAlpha(v);

        //애니메이션 실행
        logo.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        //persistent bottom sheet 상태에따라 동작주기
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        System.out.println("다 보여줬다.");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        System.out.println("드래깅되고 있는 상태.");
                        ObjectAnimator animation4 = ObjectAnimator.ofFloat(logo, "translationY", -350f);
                        ObjectAnimator animation5 = ObjectAnimator.ofFloat(earth, "translationY", -400f);
                        ObjectAnimator animation6 = ObjectAnimator.ofFloat(earth,  View.SCALE_X , 1f);
                        ObjectAnimator animation7 = ObjectAnimator.ofFloat(earth,  View.SCALE_Y , 1f);
                        //animation.setDuration(00);
                        animation4.start();
                        animation5.start();
                        animation6.start();
                        earth.getLayoutParams().height=250;
                        earth.getLayoutParams().width=250;
                        earth.requestLayout();
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        System.out.println("다 안보여줌.");
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(logo, "translationY", 0f);
                        ObjectAnimator animation2 = ObjectAnimator.ofFloat(earth, "translationY", 0f);
                        earth.getLayoutParams().height=400;
                        earth.getLayoutParams().width=400;
                        earth.requestLayout();
                        //animation.setDuration(00);
                        animation1.start();
                        animation2.start();
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    public void setViewPager(ViewPager viewPager){
        adapter.addFragment(new LoginTabFragment(),"로그인");
        adapter.addFragment(new RegisterTabFragment(),"회원가입");
        viewPager.setAdapter(adapter);
    }

    //[google]파이어베이스에 값 전달
    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        root.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=root.getCurrentUser();
                    //updateUI(firebaseUser);
                }
                else{
                    Toast.makeText(getApplicationContext(),"구글연동 로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //[kakao]파이어 베이스에 값 전달
    private void updateSignIn(int a, ActivityResult result){
        if (a == 0) {
            Intent data=result.getData();
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acct=task.getResult(ApiException.class);
                if(acct!=null){
                    UserInfo userInfo=new UserInfo();
                    userInfo.setName(acct.getDisplayName());
                    userInfo.setEmail(acct.getEmail().trim());
                    userInfo.setPoint(0);
                    userInfo.setPw("google");
                    userInfo.setPlant("000000000000000");
                    String email=acct.getEmail().trim();
                    String temp_pw="google1123!";
                    FirebaseUser user = root.getCurrentUser();
                    String uid = user.getUid();
                    System.out.println(uid);
                    reference.child("User").child(uid).setValue(userInfo);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            } catch (ApiException e) {
                e.printStackTrace();
            }


        } else if (a == 1) {
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    String temp_pw = "kakao1123!";
                    UserInfo userInfo = new UserInfo();
                    userInfo.setEmail(user.getKakaoAccount().getEmail().trim());
                    //일단 이렇게 설정...이유는 연동 로그인은  Resource Owner에게 AccessToken만 받으면 되는데, null값도 쫌 그래서 구분짓는게 필요할거 같음or user.getId()
                    userInfo.setPw(temp_pw);
                    userInfo.setName(user.getKakaoAccount().getProfile().getNickname().trim());
                    userInfo.setPoint(0);
                    userInfo.setPlant("000000000000000");
                    String email = user.getKakaoAccount().getEmail();
                    root.createUserWithEmailAndPassword(email, temp_pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = root.getCurrentUser();
                                String uid = user.getUid();
                                reference.child("User").child(uid).setValue(userInfo);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                System.out.println("빠른로그인에 실패하였습니다.");
                            }
                        }
                    });
                    return null;
                }
            });
        }
    }



}