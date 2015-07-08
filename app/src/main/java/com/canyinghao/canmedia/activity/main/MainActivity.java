package com.canyinghao.canmedia.activity.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.activity.music.MusicActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;


public class MainActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSwipe=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_list_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                finish();
                IntentHelper.getInstance().showIntent(context, MusicActivity.class);

            }
        }, null);

      final TextView tv= (TextView) findViewById(R.id.tv);

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );


       
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) { tv.setText(s); }

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }
        };


        myObservable.subscribe(mySubscriber);

        ViewObservable.clicks(tv).subscribe(new Action1<OnClickEvent>() {
            @Override
            public void call(OnClickEvent onClickEvent) {
                Toast.makeText(getApplicationContext(),"fdfdf",Toast.LENGTH_LONG).show();;
            }
        });

        AppObservable.bindActivity(this, myObservable).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });

    }




}
