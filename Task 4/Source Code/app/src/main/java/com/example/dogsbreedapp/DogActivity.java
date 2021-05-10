package com.example.dogsbreedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DogActivity extends AppCompatActivity {

    //Image Fetch-->> https://dog.ceo/api/breed/{breedName}/images/random -->> API where link to image exists!!

    List<String> resultList;
    TextView breedName;
    ImageView breedPhoto;
    String linkImage;


    public void refresh(View view)
    {

        String apiResponse = "{\"message\":{\"affenpinscher\":[\"affenpinscher\"],\"african\":[\"african\"],\"airedale\":[\"airedale\"],\"akita\":[\"akita\"],\"appenzeller\":[\"appenzeller\"],\"australian\":[\"australian\"],\"basenji\":[\"basenji\"],\"beagle\":[\"beagle\"],\"bluetick\":[\"bluetick\"],\"borzoi\":[\"borzoi\"],\"bouvier\":[\"bouvier\"],\"boxer\":[\"boxer\"],\"brabancon\":[\"brabancon\"],\"briard\":[\"briard\"],\"buhund\":[\"buhund\"],\"bulldog\":[\"bulldog\"],\"bullterrier\":[\"bullterrier\"],\"cairn\":[\"cairn\"],\"cattledog\":[\"cattledog\"],\"chihuahua\":[\"chihuahua\"],\"chow\":[\"chow\"],\"clumber\":[\"clumber\"],\"cockapoo\":[\"cockapoo\"],\"collie\":[\"collie\"],\"coonhound\":[\"coonhound\"],\"corgi\":[\"corgi\"],\"cotondetulear\":[\"cotondetulear\"],\"dachshund\":[\"dachshund\"],\"dalmatian\":[\"dalmatian\"],\"dane\":[\"dane\"],\"deerhound\":[\"deerhound\"],\"dhole\":[\"dhole\"],\"dingo\":[\"dingo\"],\"doberman\":[\"doberman\"],\"elkhound\":[\"elkhound\"],\"entlebucher\":[\"entlebucher\"],\"eskimo\":[\"eskimo\"],\"finnish\":[\"finnish\"],\"frise\":[\"frise\"],\"germanshepherd\":[\"germanshepherd\"],\"greyhound\":[\"greyhound\"],\"groenendael\":[\"groenendael\"],\"havanese\":[\"havanese\"],\"hound\":[\"hound\"],\"husky\":[\"husky\"],\"keeshond\":[\"keeshond\"],\"kelpie\":[\"kelpie\"],\"komondor\":[\"komondor\"],\"kuvasz\":[\"kuvasz\"],\"labradoodle\":[\"labradoodle\"],\"labrador\":[\"labrador\"],\"leonberg\":[\"leonberg\"],\"lhasa\":[\"lhasa\"],\"malamute\":[\"malamute\"],\"malinois\":[\"malinois\"],\"maltese\":[\"maltese\"],\"mastiff\":[\"mastiff\"],\"mexicanhairless\":[\"mexicanhairless\"],\"mix\":[\"mix\"],\"mountain\":[\"mountain\"],\"newfoundland\":[\"newfoundland\"],\"otterhound\":[\"otterhound\"],\"ovcharka\":[\"ovcharka\"],\"papillon\":[\"papillon\"],\"pekinese\":[\"pekinese\"],\"pembroke\":[\"pembroke\"],\"pinscher\":[\"pinscher\"],\"pitbull\":[\"pitbull\"],\"pointer\":[\"pointer\"],\"pomeranian\":[\"pomeranian\"],\"poodle\":[\"poodle\"],\"pug\":[\"pug\"],\"puggle\":[\"puggle\"],\"pyrenees\":[\"pyrenees\"],\"redbone\":[\"redbone\"],\"retriever\":[\"retriever\"],\"ridgeback\":[\"ridgeback\"],\"rottweiler\":[\"rottweiler\"],\"saluki\":[\"saluki\"],\"samoyed\":[\"samoyed\"],\"schipperke\":[\"schipperke\"],\"schnauzer\":[\"schnauzer\"],\"sheepdog\":[\"sheepdog\"],\"shiba\":[\"shiba\"],\"shihtzu\":[\"shihtzu\"],\"spaniel\":[\"spaniel\"],\"springer\":[\"springer\"],\"stbernard\":[\"stbernard\"],\"terrier\":[\"terrier\"],\"vizsla\":[\"vizsla\"],\"waterdog\":[\"waterdog\"],\"weimaraner\":[\"weimaraner\"],\"whippet\":[\"whippet\"],\"wolfhound\":[\"wolfhound\"]},\"status\":\"success\"}";

        try {

            JSONObject jsonObject = new JSONObject(apiResponse);

            JSONObject breedInfo = jsonObject.getJSONObject("message");
            Log.i("BREED INFO !-->>",breedInfo.toString());
            resultList = new LinkedList<String>();
            Iterator<String> breedKeys = breedInfo.keys();
            while (breedKeys.hasNext()){
                JSONArray breedArray = breedInfo.getJSONArray(breedKeys.next());
                for(int i = 0; i<breedArray.length();i++ ){
                    resultList.add(breedArray.getString(i));
                }
            }
            Log.i("Result!-->>",resultList.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        Random rand = new Random();
        int randBreed = rand.nextInt(90);

        Log.i("SOMETHING-->>",resultList.get(randBreed)); //Get breed at specific position
        breedName.setText(resultList.get(randBreed).toUpperCase());

        DownloadTask taskImApi = new DownloadTask();
        //taskImApi.execute("https://dog.ceo/api/breed/husky/images/random");       //Send Api to Fetch Image Link
        taskImApi.execute("https://dog.ceo/api/breed/" + resultList.get(randBreed) +"/images/random");

    }

    public void getImage(View view)
    {
        Log.i("Button is tapped!!","YESS!!!");

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {
            Log.i("Link format::-->>",linkImage);
            myImage = task.execute(linkImage).get();
            breedPhoto.setImageBitmap(myImage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data!=-1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("IMAGE API-->>",s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                linkImage = jsonObject.getString("message");
                Log.i("IMAGELINK-->>",linkImage);                      //Get ImageLink Here!!!
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);

        breedName = findViewById(R.id.breedName);
        breedPhoto = findViewById(R.id.breedPhoto);

        //String apiResponse = "{\"message\":{\"affenpinscher\":[],\"african\":[],\"airedale\":[],\"akita\":[],\"appenzeller\":[],\"australian\":[\"shepherd\"],\"basenji\":[],\"beagle\":[],\"bluetick\":[],\"borzoi\":[],\"bouvier\":[],\"boxer\":[],\"brabancon\":[],\"briard\":[],\"buhund\":[\"norwegian\"],\"bulldog\":[\"boston\",\"english\",\"french\"],\"bullterrier\":[\"staffordshire\"],\"cairn\":[],\"cattledog\":[\"australian\"],\"chihuahua\":[],\"chow\":[],\"clumber\":[],\"cockapoo\":[],\"collie\":[\"border\"],\"coonhound\":[],\"corgi\":[\"cardigan\"],\"cotondetulear\":[],\"dachshund\":[],\"dalmatian\":[],\"dane\":[\"great\"],\"deerhound\":[\"scottish\"],\"dhole\":[],\"dingo\":[],\"doberman\":[],\"elkhound\":[\"norwegian\"],\"entlebucher\":[],\"eskimo\":[],\"finnish\":[\"lapphund\"],\"frise\":[\"bichon\"],\"germanshepherd\":[]," +
               // "\"greyhound\":[\"italian\"],\"groenendael\":[],\"havanese\":[],\"hound\":[\"afghan\",\"basset\",\"blood\",\"english\",\"ibizan\",\"plott\",\"walker\"],\"husky\":[],\"keeshond\":[],\"kelpie\":[],\"komondor\":[],\"kuvasz\":[],\"labradoodle\":[],\"labrador\":[],\"leonberg\":[],\"lhasa\":[],\"malamute\":[],\"malinois\":[],\"maltese\":[],\"mastiff\":[\"bull\",\"english\",\"tibetan\"],\"mexicanhairless\":[],\"mix\":[],\"mountain\":[\"bernese\",\"swiss\"],\"newfoundland\":[],\"otterhound\":[],\"ovcharka\":[\"caucasian\"],\"papillon\":[],\"pekinese\":[],\"pembroke\":[],\"pinscher\":[\"miniature\"],\"pitbull\":[],\"pointer\":[\"german\",\"germanlonghair\"],\"pomeranian\":[],\"poodle\":[\"miniature\",\"standard\",\"toy\"],\"pug\":[],\"puggle\":[],\"pyrenees\":[],\"redbone\":[],\"retriever\":[\"chesapeake\",\"curly\",\"flatcoated\"," +
                //"\"golden\"],\"ridgeback\":[\"rhodesian\"],\"rottweiler\":[],\"saluki\":[],\"samoyed\":[],\"schipperke\":[],\"schnauzer\":[\"giant\",\"miniature\"],\"setter\":[\"english\",\"gordon\",\"irish\"],\"sheepdog\":[\"english\",\"shetland\"],\"shiba\":[],\"shihtzu\":[],\"spaniel\":[\"blenheim\",\"brittany\",\"cocker\",\"irish\",\"japanese\",\"sussex\",\"welsh\"],\"springer\":[\"english\"],\"stbernard\":[],\"terrier\":[\"american\",\"australian\",\"bedlington\",\"border\",\"dandie\",\"fox\",\"irish\",\"kerryblue\",\"lakeland\",\"norfolk\",\"norwich\",\"patterdale\",\"russell\",\"scottish\",\"sealyham\",\"silky\",\"tibetan\",\"toy\",\"westhighland\",\"wheaten\",\"yorkshire\"],\"vizsla\":[],\"waterdog\":[\"spanish\"],\"weimaraner\":[],\"whippet\":[],\"wolfhound\":[\"irish\"]},\"status\":\"success\"}";

        String apiResponse = "{\"message\":{\"affenpinscher\":[\"affenpinscher\"],\"african\":[\"african\"],\"airedale\":[\"airedale\"],\"akita\":[\"akita\"],\"appenzeller\":[\"appenzeller\"],\"australian\":[\"australian\"],\"basenji\":[\"basenji\"],\"beagle\":[\"beagle\"],\"bluetick\":[\"bluetick\"],\"borzoi\":[\"borzoi\"],\"bouvier\":[\"bouvier\"],\"boxer\":[\"boxer\"],\"brabancon\":[\"brabancon\"],\"briard\":[\"briard\"],\"buhund\":[\"buhund\"],\"bulldog\":[\"bulldog\"],\"bullterrier\":[\"bullterrier\"],\"cairn\":[\"cairn\"],\"cattledog\":[\"cattledog\"],\"chihuahua\":[\"chihuahua\"],\"chow\":[\"chow\"],\"clumber\":[\"clumber\"],\"cockapoo\":[\"cockapoo\"],\"collie\":[\"collie\"],\"coonhound\":[\"coonhound\"],\"corgi\":[\"corgi\"],\"cotondetulear\":[\"cotondetulear\"],\"dachshund\":[\"dachshund\"],\"dalmatian\":[\"dalmatian\"],\"dane\":[\"dane\"],\"deerhound\":[\"deerhound\"],\"dhole\":[\"dhole\"],\"dingo\":[\"dingo\"],\"doberman\":[\"doberman\"],\"elkhound\":[\"elkhound\"],\"entlebucher\":[\"entlebucher\"],\"eskimo\":[\"eskimo\"],\"finnish\":[\"finnish\"],\"frise\":[\"frise\"],\"germanshepherd\":[\"germanshepherd\"],\"greyhound\":[\"greyhound\"],\"groenendael\":[\"groenendael\"],\"havanese\":[\"havanese\"],\"hound\":[\"hound\"],\"husky\":[\"husky\"],\"keeshond\":[\"keeshond\"],\"kelpie\":[\"kelpie\"],\"komondor\":[\"komondor\"],\"kuvasz\":[\"kuvasz\"],\"labradoodle\":[\"labradoodle\"],\"labrador\":[\"labrador\"],\"leonberg\":[\"leonberg\"],\"lhasa\":[\"lhasa\"],\"malamute\":[\"malamute\"],\"malinois\":[\"malinois\"],\"maltese\":[\"maltese\"],\"mastiff\":[\"mastiff\"],\"mexicanhairless\":[\"mexicanhairless\"],\"mix\":[\"mix\"],\"mountain\":[\"mountain\"],\"newfoundland\":[\"newfoundland\"],\"otterhound\":[\"otterhound\"],\"ovcharka\":[\"ovcharka\"],\"papillon\":[\"papillon\"],\"pekinese\":[\"pekinese\"],\"pembroke\":[\"pembroke\"],\"pinscher\":[\"pinscher\"],\"pitbull\":[\"pitbull\"],\"pointer\":[\"pointer\"],\"pomeranian\":[\"pomeranian\"],\"poodle\":[\"poodle\"],\"pug\":[\"pug\"],\"puggle\":[\"puggle\"],\"pyrenees\":[\"pyrenees\"],\"redbone\":[\"redbone\"],\"retriever\":[\"retriever\"],\"ridgeback\":[\"ridgeback\"],\"rottweiler\":[\"rottweiler\"],\"saluki\":[\"saluki\"],\"samoyed\":[\"samoyed\"],\"schipperke\":[\"schipperke\"],\"schnauzer\":[\"schnauzer\"],\"sheepdog\":[\"sheepdog\"],\"shiba\":[\"shiba\"],\"shihtzu\":[\"shihtzu\"],\"spaniel\":[\"spaniel\"],\"springer\":[\"springer\"],\"stbernard\":[\"stbernard\"],\"terrier\":[\"terrier\"],\"vizsla\":[\"vizsla\"],\"waterdog\":[\"waterdog\"],\"weimaraner\":[\"weimaraner\"],\"whippet\":[\"whippet\"],\"wolfhound\":[\"wolfhound\"]},\"status\":\"success\"}";

        try {

            JSONObject jsonObject = new JSONObject(apiResponse);

            JSONObject breedInfo = jsonObject.getJSONObject("message");
            Log.i("BREED INFO !-->>",breedInfo.toString());
            resultList = new LinkedList<String>();
            Iterator<String> breedKeys = breedInfo.keys();
            while (breedKeys.hasNext()){
                JSONArray breedArray = breedInfo.getJSONArray(breedKeys.next());
                for(int i = 0; i<breedArray.length();i++ ){
                    resultList.add(breedArray.getString(i));
                }
            }
            Log.i("Result!-->>",resultList.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        Random rand = new Random();
        int randBreed = rand.nextInt(90);

        Log.i("SOMETHING-->>",resultList.get(randBreed)); //Get breed at specific position
        breedName.setText(resultList.get(randBreed).toUpperCase());

        DownloadTask taskImApi = new DownloadTask();
        //taskImApi.execute("https://dog.ceo/api/breed/husky/images/random");       //Send Api to Fetch Image Link
        taskImApi.execute("https://dog.ceo/api/breed/" + resultList.get(randBreed) +"/images/random");

    }



    public class ImageDownloader extends AsyncTask<String,Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;

            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
