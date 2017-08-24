package com.pyeongchang.conch.conch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<TorchCommunity> torchCommunityArrayList;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;
    private Button changePictureBtn;
    private ImageView userPicture;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absoultePath;
    private FirebaseStorage storage;
    private FirebaseDatabase mDatabase;
    private StorageReference storageRef;
    private StorageReference userImageRef;
    private User user;

//    private DB_Manger dbmanger;

    private LinearLayout mypageScrollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addCommunityListInMypage(TorchCommunity addTorchCommunity, int index){
        TextView addTextView=new TextView(this);
        addTextView.setText(addTorchCommunity.getCommunityName());
        addTextView.setTextSize(30);
        addTextView.setTextColor(Color.WHITE);
        addTextView.setGravity(Gravity.CENTER);
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        Log.i("(테스트)커뮤니티 크기: ",String.valueOf(index));
        switch (index) {
            case 1:
                addTextView.setBackgroundResource(R.color.comColor1);
                Log.i("(테스트)1 ",String.valueOf(index));
                break;
            case 2:
                addTextView.setBackgroundResource(R.color.comColor2);
                Log.i("(테스트)2",String.valueOf(index));
                break;
            case 3:
                addTextView.setBackgroundResource(R.color.comColor3);
                Log.i("(테스트)3 ",String.valueOf(index));
                break;
            case 4:
                addTextView.setBackgroundResource(R.color.comColor4);
                Log.i("(테스트)4 ",String.valueOf(index));
                break;
            default:
                addTextView.setBackgroundResource(R.color.comColor5);
                Log.i("(테스트)5 ",String.valueOf(index));
        }
        mypageScrollLayout.addView(addTextView,p);
    }




    /**
     * 카메라에서 사진 촬영
     */
    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/SmartWheel/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    userPicture.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    storeImageInFirebase(photo); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;

                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }


        @Override
        public void onClick(View v) {
            id_view = v.getId();
            if(v.getId() == R.id.mypage_saveDescription) {
                EditText description=(EditText) findViewById(R.id.mypage_description);
                String changeText=description.getText().toString();
                user.setInfo(changeText);
                mDatabase=FirebaseDatabase.getInstance();
                mDatabase.getReference().child("Users").child(user.getId()).child("info").setValue(changeText);
                Toast.makeText(this, "자기소개 수정완료", Toast.LENGTH_SHORT).show();

            }else if(v.getId() == R.id.mypage_changeImage) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(this)
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
            }

        }

    /*
     * Bitmap을 저장하는 부분
     */
    private void storeImageInFirebase(Bitmap bitmap) {
        try {
            userImageRef=storageRef.child(user.getId()+".jpg");
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] data=baos.toByteArray();
            UploadTask uploadTask=userImageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("(테스트) 파이어베이스 전송실패: ",e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    Glide.with(MypageActivity.this).load(downloadUrl).into(userPicture);
                    Log.e("(테스트) 파이어베이스 전송성공!!","!!!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
        int index=1;
        mypageScrollLayout= (LinearLayout) findViewById(R.id.mypage_communityList);

        torchCommunityArrayList=((MainActivity)MainActivity.mContext).getCommunityList();
        for (TorchCommunity torchCommunity:torchCommunityArrayList) {
            addCommunityListInMypage(torchCommunity,index++);
        }
        changePictureBtn = (Button) this.findViewById(R.id.mypage_changeImage);
        userPicture=(ImageView)this.findViewById(R.id.mypage_userPicture);
        changePictureBtn.setOnClickListener(this);
        userPicture.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                initMypageInfo();
            }
        });

        initMypageInfo();
    }
    private void initMypageInfo(){
        TextView userId=(TextView)findViewById(R.id.mypage_userID);
        ImageView userNation= (ImageView) findViewById(R.id.mypage_userNation);
        TextView userScore= (TextView) findViewById(R.id.mypage_userScore);
        EditText userDescription= (EditText) findViewById(R.id.mypage_description);

        user=((MainActivity)MainActivity.mContext).getUser();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        downloadImageFromFirebase(user.getId()+".jpg");

        userId.setText(user.getUserName());
        userNation.setImageResource(R.drawable.bear);//나라별 이미지 필요
        userScore.setText(String.valueOf(user.getScore()));
        userDescription.setText(user.getInfo());
    }
    private void downloadImageFromFirebase(String index){
        storageRef.child(index).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MypageActivity.this).load(uri).into(userPicture);
            }
        });
    }
}
