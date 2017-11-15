package com.example.tahaali.realmdatabase;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tahaali.realmdatabase.model.Person;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText name,Email;
    Realm realm;
    RealmAsyncTask realmAsyncTask;// USED WHEN ADDING DATA Asynchronously As if the activity is interrupted(E.g Phone call) the app is not crashed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name= (EditText) findViewById(R.id.Name);
        Email=(EditText)findViewById(R.id.Email);

        // Step 1
        realm=Realm.getDefaultInstance();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(realmAsyncTask!=null && !realmAsyncTask.isCancelled())
        {
            realmAsyncTask.cancel();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); //If not closed memory will be leaked
    }

    public void MainThread(View view) {
        final String id = UUID.randomUUID().toString();     //UID WILL GENERATE A DIFFERENT PRIMARY KEY
        final String Name = name.getText().toString();
        final String email = Email.getText().toString();

//        try
//        {
//    realm.beginTransaction();   WE WILL USE DIFFERENT W
//        Person person=realm.createObject(Person.class);
//
//        person.setEmail(email);
//        person.setName(Name);
//    realm.commitTransaction();
//    } catch (Exception E){
//    realm.cancelTransaction();}
//    }
    realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
            Person person=realm.createObject(Person.class,id);

            person.setEmail(email);
            person.setName(Name);
            Toast.makeText(MainActivity.this,"Sucessfuly Added",Toast.LENGTH_SHORT).show();
        }
    });
    }

    public void WorkerThread(View view) {
        // REMEMBER YOU WILL NEED TO IMPLEMENT ON STOP FOR Async

        final String Name = name.getText().toString();
        final String email = Email.getText().toString();
        final String id = UUID.randomUUID().toString();

        // The SUCESS and ERROR only apper when performing the task Asyncronously,So it will not be availible in

        // RealAsyncTask is created so that if an ac
    realmAsyncTask= realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class, id);
                person.setEmail(email);
                person.setName(Name);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Log.d("taha","Sucessfully Added");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("taha",error.toString());
            }
        });
    }

    public void viewAll(View view) {
        List<Person>list=realm.where(Person.class).findAll();// THIS  realm.where(Person.class) will give you the data you will store it in an Array
  //NOW ALL THE DATA CAN BE EXTRACTED BY siMPLY USING THE list so
         StringBuilder str=new StringBuilder();

        for(Person person:list)
        {
            str.append('\n'+"ID=").append(person.getP_key());
            str.append(" '\n' Email").append(person.getEmail());
            str.append('\n'+"Name").append(person.getName());
        }

        //+++++++ JUST FOR REFERENCE THE SIMPLE CODE BUT ALWAYS "STRINGBUFFER" Bcz string simple string is "IMMUTABLE"(cannot be changed)
//        String str="";
//
//        for (int i=0;i<list.size();i++)
//        {
//            str=str+"'\n' NAME"+list.get(i).getName();
//            str=str+"'\n' Email"+list.get(i).getEmail();
//            str=str+"'\n' Id"+list.get(i).getP_key();
//        }



        Log.d("Result",str.toString());
    }

    public void Update(View view) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) { //begainsWith is a PREDICATE refer to document in drive
                List<Person> personRealm=realm.where(Person.class).beginsWith("name","taha").findAll();// Hardcorded value
                personRealm.get(0).setName("NAME FROM UPDATE METHOOD");// THIS IS how simple it is to update data

            }
        });


    }

    public void Delete(View view) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //RealmResult id a seperate library
                RealmResults<Person> personList=  realm.where(Person.class).findAll();
          //Will delete the first Row      personList.deleteFirstFromRealm();
                //Will delete the last row  personList.deleteLastFromRealm();
           //Will delete from a given location     personList.deleteFromRealm(2);
           //Will delete the entire data present(not of complete DB but in this case THe complete data from)
                // personList.deleteAllFromRealm();

                Person P1=realm.where(Person.class).findFirst();
                P1.deleteFromRealm();
            }
        });
    }
}
