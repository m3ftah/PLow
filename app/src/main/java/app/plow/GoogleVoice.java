package app.plow;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;

/**
 * Created by Meftah on 9/17/2015.
 */
public class GoogleVoice {
    Activity context = null;
    public static final int check=1111;
    GoogleVoice(Activity context){
        this.context = context;
    }
    public void openMicrophone(){
        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak up son");
        context.startActivityForResult(i, check);

    }
    public String onActivityForResult(Intent data){
        return data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
    }
}
