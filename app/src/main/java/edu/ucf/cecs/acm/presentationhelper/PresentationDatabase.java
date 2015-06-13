package edu.ucf.cecs.acm.presentationhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by kishoredebnath on 12/06/15.
 */
public class PresentationDatabase {

    //SQLite Helper class
    private class SQLiteHelper extends SQLiteOpenHelper{

        private Context SQLContext;
        private String tableName;
        private String tableCreateQuery;

        public SQLiteHelper(Context context, String DB_NAME, int DB_VERSION, String tableName, String tableCreateQuery)
        {
            super(context, DB_NAME, null, DB_VERSION);
            this.SQLContext = context;
            this.tableName = tableName;
            this.tableCreateQuery = tableCreateQuery;
        }

        //On Create method
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(this.tableCreateQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table" + this.tableName);
            onCreate(db);
        }

    }

    private static final String TAG = "PresentationDatabase";

    //Database specifications
    private static final Uri dbLocator = Uri.parse("content://edu.ucf.cecs.acm.presentationhelper/presentation_database.db");
    private static final String dbName = "presentation_database.db";
    private static int dbVersion = 1;

    //Database table specification
    private final static String dbTableName = "presentation_table";
    private final static String dbTablePrimaryKeyId = "presentation_id";
    private final static String dbTablePresentationName = "presentation_name";
    private final static String dbTablePresentationContent = "presentation_content";

    //Command to create new Presentation table
    private final static String dbCreateTableQuery = "create table " +
                                                        dbTableName +
                                                        " (" + dbTablePrimaryKeyId + " integer primary key autoincrement not null, " +
                                                               dbTablePresentationName + " text not null, " +
                                                               dbTablePresentationContent + " text not null);";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    public PresentationDatabase(Context context){
        this.sqLiteHelper = new SQLiteHelper(context, dbName, dbVersion, dbTableName, dbCreateTableQuery);
    }

    private void open() throws SQLException{
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
    }

    private void close(){
        sqLiteHelper.close();
    }

    //Add Presentation into the Database
    public boolean insertPresentationDatabase(String presentationName, String presentationInfoString){

        ContentValues contentValues = new ContentValues();
        contentValues.put(dbTablePresentationName, presentationName);
        contentValues.put(dbTablePresentationContent, presentationInfoString);

        try{

            this.open();

            this.sqLiteDatabase.insert(dbTableName, null, contentValues);

            this.close();

            return true;

        }catch (Exception e){

            this.close();
            Log.e(TAG, e.toString());
            e.printStackTrace();

            return false;
        }

    }

    //Edit Presentation content 1: Edit Presentation Name 2: Edit Presentation slide duration
    public boolean editPresentationDatabase(int index, int column, String updatedPresentationInfoString){

        try{

            this.open();

            switch (column){
                case 1:
                    sqLiteDatabase.execSQL("UPDATE "+ dbTableName + " SET " + dbTablePresentationName + " = " + updatedPresentationInfoString + " WHERE " + dbTablePrimaryKeyId + " = " + index);
                    break;
                case 2:
                    sqLiteDatabase.execSQL("UPDATE "+ dbTableName + " SET " + dbTablePresentationContent + " = " + updatedPresentationInfoString + " WHERE " + dbTablePrimaryKeyId + " = " + index);
                    break;
            }

            this.close();

            return true;

        }catch (Exception e){

            this.close();
            Log.e(TAG, e.toString());
            e.printStackTrace();

            return false;
        }

    }

    //Delete Presentation entry
    public boolean deletePresentationDatabase(int index){

        try{

            this.open();

            sqLiteDatabase.delete(dbTableName, dbTablePrimaryKeyId + " = "+ Integer.toString(index), null);

            this.close();

            return true;

        }catch(Exception e){

            this.close();
            Log.e(TAG, e.toString());
            e.printStackTrace();

            return false;
        }
    }

    //get presentation on basis of index, example index = * gets all the table entries separated by #
    public ArrayList<String> getPresentationDatabase(String index){

        try{

            String selectQueryCursor, selectQueryResult;
            ArrayList<String> selectQueryResultArrayList = new ArrayList<String>();
            Cursor cursor;

            this.open();

           if(index.equals("*")){

               selectQueryCursor = "SELECT " + dbTablePresentationName + ", " + dbTablePresentationContent + " from " + dbTableName;

                cursor = sqLiteDatabase.rawQuery(selectQueryCursor, null);

               if(cursor.moveToFirst()){
                   do{

                       selectQueryResult = "";
                       selectQueryResult += cursor.getString(cursor.getColumnIndexOrThrow(dbTablePresentationName));
                       selectQueryResult += "#"+cursor.getString(cursor.getColumnIndexOrThrow(dbTablePresentationContent));

                       selectQueryResultArrayList.add(selectQueryResult);


                   }while(cursor.moveToNext());
               }
               cursor.close();


           }else{

               selectQueryCursor = "SELECT " + dbTablePresentationName + ", " + dbTablePresentationContent + " from " + dbTableName + " WHERE " + dbTablePrimaryKeyId + " = ?";

               cursor = sqLiteDatabase.rawQuery(selectQueryCursor, new String[]{index});

               if(cursor.moveToFirst()) {

                   selectQueryResult = cursor.getString(cursor.getColumnIndexOrThrow(dbTablePresentationName));
                   selectQueryResult += "#"+cursor.getString(cursor.getColumnIndexOrThrow(dbTablePresentationContent));

                   selectQueryResultArrayList.add(selectQueryResult);


               }else{
                   Log.e(TAG, "Cannot retrieve Presentation data from DB.");

               }

           }

            this.close();

            return selectQueryResultArrayList;

        }catch(Exception e){

            this.close();

            Log.e(TAG, e.toString());
            e.printStackTrace();

            return null;
        }
    }
}


