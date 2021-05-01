package com.example.task2bysiddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private Button snackbarBtn;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button customDialog;
    Dialog dialog;
    private TextView textView;
    private TextView pbtextView;
    ProgressBar progressBar;
    int counter = 0; //For progress bar
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initDatePicker();
        customDialog = findViewById(R.id.button2);
        dateButton = findViewById(R.id.button);
        dateButton.setText(getTodaysDate());
        textView = findViewById(R.id.textView);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialogbck);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button ret = dialog.findViewById(R.id.button3);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Returned!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        customDialog = findViewById(R.id.button2);
        customDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //textView.setText(getTodaysDate());
        pbtextView = (TextView) findViewById(R.id.pbtextView);

    }

    private void snaB()
    {
            snackbarBtn = findViewById(R.id.snackbarBtn);
            //snackbarBtn.setVisibility(View.VISIBLE);
            snackbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Snackbar snackbar = Snackbar.make(v,"You just clicked SnackBar!",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });

    }


    //Progress Bar Method
    private void prog()
    {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pbtextView.setVisibility(View.VISIBLE);
        /*final Timer t = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run()
            {
                counter++;
                progressBar.setProgress(counter);
                pbtextView.setText(counter + "/100");
                if(counter == 100)
                {
                    t.cancel();
                }
            }


        };

        //pbtextView.setText(counter + "/100");

        t.schedule(tt,0,200); //each percent is increased at 0.2s
        */
        new Thread(new Runnable()
        {
            public void run()
            {
                while (counter < 100)
                {
                    counter += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run()
                        {
                            progressBar.setProgress(counter);
                            pbtextView.setText(counter+"/"+progressBar.getMax());
                            //To display the button after completion of progressBar Also rem to access this button only after it is declared(find view and this)
                            if(counter == 99)
                            {
                                snackbarBtn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                pbtextView.setVisibility(View.INVISIBLE);
                                customDialog.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    try
                    {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    //Function to set the text of 'DatePickerButton' to Current date of operation
    private String  getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;                                                                          //Month By default starts from zero
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth,month,year);
    }


    //Function to Provoke CalendarInstance DialogBox and set Date of the 'DatePickerButton'
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                 month = month + 1;
                 String date = makeDateString(dayOfMonth, month, year);
                 dateButton.setText(date);                                                          // This will update the text in button to the date(from calendar instance)
                                                                                                    //textView.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,dayOfMonth);
    }


    //Function to convert Selected Date to a string to display
    private String makeDateString(int dayOfMonth, int month, int year)
    {
        return dayOfMonth + " " + getMonthFormat(month) + " " + year;
    }


    //
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";
        //Default
        return "Jan";
    }


    //Method to call the dialogBox related functions when clicked on the 'DatePickerButton'
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
        Toast.makeText(this, "Select the Date from the Dialog.", Toast.LENGTH_LONG).show();
        snaB();
        prog();
    }
    //End of the program
}