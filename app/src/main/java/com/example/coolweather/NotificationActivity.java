package com.example.coolweather;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.coolweather.notification.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    private TimePicker timePick1;
    private Button buttone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        TextView showTime = (TextView)findViewById(R.id.show_time);
        timePick1 = (TimePicker)findViewById(R.id.timePic1);
        buttone1 = (Button)findViewById(R.id.buttone_time);//设置通知时间按钮


        //页面显示时间

      /*  Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();


        // Now we display formattedDate value in TextView
        TextView txtView = new TextView(this);
        txtView.setText("Current Date and Time : "+formattedDate);
        txtView.setGravity(Gravity.CENTER);
        txtView.setTextSize(20);
        setContentView(txtView);*/



        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();


        // Now we display formattedDate value in TextView

        showTime.setText("当前时间: "+formattedDate);
        showTime.setGravity(Gravity.CENTER);
        showTime.setTextSize(20);




        //设定两种通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "one";
            String channelName = "重要消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "two";
            channelName = "一般消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }



        buttone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("设置的时间为"+timePick1.getHour()+"时"+timePick1.getMinute()+"分");
                String time = timePick1.getHour()+"时"+timePick1.getMinute()+"分通知查看天气";
                Toast.makeText(NotificationActivity.this,time,Toast.LENGTH_SHORT).show();


/*                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new NotificationCompat.Builder(NotificationActivity.this, "one")
                            .setContentTitle("收到一条聊天消息")
                            .setContentText("今天中午吃什么？")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.notification_img)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_img))
                            .setAutoCancel(true)
                            .build();
                    manager.notify(1, notification);*/





                //广播形式发起通知
                Intent intent = new Intent(NotificationActivity.this,AlarmReceiver.class);
                intent.setAction("NOTIFICATION");
                PendingIntent pi = PendingIntent.getBroadcast(NotificationActivity.this, 0, intent, 0);
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                int type = AlarmManager.RTC_WAKEUP;
                //new Date()：表示当前日期，可以根据项目需求替换成所求日期
                //getTime()：日期的该方法同样可以表示从1970年1月1日0点至今所经历的毫秒数
                long triggerAtMillis = new Date().getTime();
                long intervalMillis = 1000 * 5;
                manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, pi);
                Toast.makeText(NotificationActivity.this,"将在五秒后发送通知",Toast.LENGTH_SHORT).show();

            }
        });


 /*       OnChangeListener  buc=new OnChangeListener();
        buttone1.setOnClickListener(buc);

        //是否使用24小时制
        timePick1.setIs24HourView(true);
        TimeListener times=new TimeListener();
        timePick1.setOnTimeChangedListener(times);

        System.out.println("当前时间为"+timePick1.getMinute());*/
    }



    //创建通知渠道
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }



    class OnChangeListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int h=timePick1.getCurrentHour();
            int m=timePick1.getCurrentMinute();
            System.out.println("h:"+h+"   m:"+m);
        }
    }

    class TimeListener implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            System.out.println("h:"+ hourOfDay +" m:"+minute);
        }

    }


}


