package com.example.ahn.StudyBoard;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ahn.studyviewer.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ahn on 2017-03-07.
 */

public class BoardPopupLtn extends Activity {
    private static String TAG = "MainActivity";
    private EditText inputAddress;
    private ListView showLtn;
    private ArrayAdapter<String> adapter;
    private Geocoder gc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.board_popup_ltn);

        inputAddress = (EditText) findViewById(R.id.inputAddress);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        showLtn = (ListView) findViewById(R.id.LView01);
        showLtn.setAdapter(adapter);
        showLtn.setOnItemClickListener(onClickListItem);

        //UI 객체생성
        //txtText = (TextView)findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        //txtText.setText(data);
        // 지오코더 객체 생성
        gc = new Geocoder(this, Locale.KOREAN);
    }

    //위도,경도 검색하기 버튼 클릭시
    public void searchLtn(View view){
        String searchStr = inputAddress.getText().toString();

        // 주소 정보를 이용해 위치 좌표 찾기 메소드 호출
        searchLocation(searchStr);
    }

    /**
     * 주소를 이용해 위치 좌표를 찾는 메소드 정의
     */
    private void searchLocation(String searchStr) {
        // 결과값이 들어갈 리스트 선언
        List<Address> addressList = null;
        adapter.clear();
        try {
            addressList = gc.getFromLocationName(searchStr, 3);

            if (addressList != null) {
                //adapter.add("\nCount of Addresses for [" + searchStr + "] : " + addressList.size());
                for (int i = 0; i < addressList.size(); i++) {
                    Address outAddr = addressList.get(i);
                    int addrCount = outAddr.getMaxAddressLineIndex() + 1;
                    StringBuffer outAddrStr = new StringBuffer();
                    for (int k = 0; k < addrCount; k++) {
                        outAddrStr.append(outAddr.getAddressLine(k));
                    }
                    //outAddrStr.append("\n\tLatitude : " + outAddr.getLatitude());
                    //outAddrStr.append("\n\tLongitude : " + outAddr.getLongitude());

                    adapter.add(":Address:" + outAddrStr.toString()+ ":Latitude:" +  outAddr.getLatitude() + ":Longitude:" + outAddr.getLongitude());
                }
            }

        } catch(IOException ex) {
            Log.d(TAG, "예외 : " + ex.toString());
        }

    }

    /*
       리스트뷰 클릭시 실행되는 함수
     */
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Intent intent = new Intent();
            intent.putExtra("LTN", adapter.getItem(arg2));
            setResult(RESULT_OK, intent);
            finish();
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            //Toast.makeText(getApplicationContext(), adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
        }
    };
}
