/*
 * Copyright (C) 2014 IUH €yber$oft Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package vn.cybersoft.obs.android.provider;

import java.io.File;

import vn.cybersoft.obs.android.utilities.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * @author Luan Vu (hlvu.cybersoft@gmail.com)
 *
 */
public class OBSDatabaseHelper extends SQLiteOpenHelper {
	/**
     * Original OBS Database.
     **/
	private static final int VERSION_1 = 1;
	
	private static final int VERSION_2 = 2;
	
    // Database and table names
    static final String DATABASE_NAME = "obs.db";
    static final String TIME_SCHEDULES_TABLE_NAME = "time_schedules";
    static final String POWER_SCHEDULES_TABLE_NAME = "power_schedules";
    static final String OPTIMAL_MODES_TABLE_NAME = "optimal_modes";
    static final String BATTERY_TRACES_TABLE_NAME = "battery_traces";
    
    private static void createTimeSchedulesTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TIME_SCHEDULES_TABLE_NAME + " (" +
				DataProviderApi.TimeSchedulesColumns._ID + " INTEGER PRIMARY KEY, " +
				DataProviderApi.TimeSchedulesColumns.HOUR + " INTEGER NOT NULL, " +
				DataProviderApi.TimeSchedulesColumns.MINUTES + " INTEGER NOT NULL, " +
				DataProviderApi.TimeSchedulesColumns.DAYS_OF_WEEK + " INTEGER NOT NULL, " +
				DataProviderApi.TimeSchedulesColumns.SCHEDULE_TIME + " INTEGER NOT NULL, " +
				DataProviderApi.TimeSchedulesColumns.ENABLED + " INTEGER NOT NULL, " +
				DataProviderApi.TimeSchedulesColumns.MODE_ID + " INTEGER REFERENCES " +
					OPTIMAL_MODES_TABLE_NAME + "(" + DataProviderApi.OptimalModesColumns._ID + ") " +
					"ON UPDATE CASCADE ON DELETE CASCADE" + ");");
		Log.i("Time schedules table created");
    }
    
    private static void createPowerSchedulesTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + POWER_SCHEDULES_TABLE_NAME + " (" +
				DataProviderApi.PowerSchedulesColumns._ID + " INTEGER PRIMARY KEY, " +
				DataProviderApi.PowerSchedulesColumns.BATTERY_LEVEL + " INTEGER NOT NULL, " +
				DataProviderApi.PowerSchedulesColumns.ENABLED + " INTEGER NOT NULL, " +
				DataProviderApi.PowerSchedulesColumns.MODE_ID + " INTEGER REFERENCES " +
					OPTIMAL_MODES_TABLE_NAME + "(" + DataProviderApi.OptimalModesColumns._ID + ") " +
					"ON UPDATE CASCADE ON DELETE CASCADE" + ");");
		Log.i("Power schedules table created");
    }
    
    private static void createOptimalModesTable(SQLiteDatabase db) {
    	db.execSQL("CREATE TABLE " + OPTIMAL_MODES_TABLE_NAME + " (" +
    			DataProviderApi.OptimalModesColumns._ID + " INTEGER PRIMARY KEY, " +
    			DataProviderApi.OptimalModesColumns.NAME + " TEXT NOT NULL, " + 
    			DataProviderApi.OptimalModesColumns.DESC + " TEXT, " + 
    			DataProviderApi.OptimalModesColumns.CAN_EDIT + " INTEGER NOT NULL, " +
    			DataProviderApi.OptimalModesColumns.SCREEN_BRIGHTNESS + " INTEGER NOT NULL, " + 
    			DataProviderApi.OptimalModesColumns.SCREEN_TIMEOUT + " INTEGER NOT NULL, " + 
    			DataProviderApi.OptimalModesColumns.VIBRATE + " INTEGER NOT NULL, " + 
    			DataProviderApi.OptimalModesColumns.WIFI + " INTEGER NOT NULL, " + 
    			DataProviderApi.OptimalModesColumns.BLUETOOTH + " INTEGER NOT NULL, " +
    			DataProviderApi.OptimalModesColumns.SYNC + " INTEGER NOT NULL, " + 
    			DataProviderApi.OptimalModesColumns.HAPTIC_FEEDBACK + " INTEGER NOT NULL" + ");");
    	Log.i("Modes table created");
    }
    
    private static void createBatteryTracesTable(SQLiteDatabase db) {
    	db.execSQL("CREATE TABLE " + BATTERY_TRACES_TABLE_NAME + " (" +
    			DataProviderApi.BatteryTracesColumns._ID + " INTEGER PRIMARY KEY, " +
    			DataProviderApi.BatteryTracesColumns.HOUR + " INTEGER NOT NULL, " +
				DataProviderApi.BatteryTracesColumns.MINUTES + " INTEGER NOT NULL, " +
				DataProviderApi.BatteryTracesColumns.LEVEL + " INTEGER NOT NULL, " +
    			DataProviderApi.BatteryTracesColumns.DATE + " INTEGER NOT NULL" + ");");
    	Log.i("battery traces table created");
    }
    
    private Context mContext;
    
	public OBSDatabaseHelper(Context context) {
		super(context, Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null, VERSION_1);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createOptimalModesTable(db);
		createTimeSchedulesTable(db);
		createPowerSchedulesTable(db);
		createBatteryTracesTable(db);
		
        // insert default modes
        Log.i("Inserting default optimal modes");

        insertDefaultMode(db, "mode_name_short", "mode_desc_short", 0, 255, 600000, 1, 1, 1, 1, 1);
        insertDefaultMode(db, "mode_name_long", "mode_desc_long", 0, 25, 15000, 0, 0, 0, 0, 0);
	}

    private void insertDefaultMode(SQLiteDatabase db, String name, String desc, int canEdit, int screenBrightness,
		int screenTimeout, int vibrate, int wifi, int bluetooth, int sync, int hapticFeedback) {
        ContentValues values = new ContentValues();
        values.put(DataProviderApi.OptimalModesColumns.NAME, name);
        values.put(DataProviderApi.OptimalModesColumns.DESC, desc);
        values.put(DataProviderApi.OptimalModesColumns.CAN_EDIT, canEdit);
        values.put(DataProviderApi.OptimalModesColumns.SCREEN_BRIGHTNESS, screenBrightness);
        values.put(DataProviderApi.OptimalModesColumns.SCREEN_TIMEOUT, screenTimeout);
        values.put(DataProviderApi.OptimalModesColumns.VIBRATE, vibrate);
        values.put(DataProviderApi.OptimalModesColumns.WIFI, wifi);
        values.put(DataProviderApi.OptimalModesColumns.BLUETOOTH, bluetooth);
        values.put(DataProviderApi.OptimalModesColumns.SYNC, sync);
        values.put(DataProviderApi.OptimalModesColumns.HAPTIC_FEEDBACK, hapticFeedback);
        db.insert(OPTIMAL_MODES_TABLE_NAME, null, values);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (Log.LOGV) {
            Log.v("Upgrading alarms database from version " + oldVersion + " to " + newVersion);
        }
	}

}
