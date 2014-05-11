package uk.ac.tees.amazeballs;

import uk.ac.tees.amazeballs.maze.Maze;
import uk.ac.tees.amazeballs.views.MazeViewport;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


/**
 * The main activity for displaying a game.
 * 
 * @author Alex Mullen (J9858839)
 *
 */
public class MainGameActivity extends Activity implements SensorEventListener {
	
	private static final long GAME_TICK_INTERVAL = 16;
	
	private Sensor accelerometerSensor;
	private SensorManager sensorManager;
	
	private MazeViewport gameView;
	private GameController gameController;
	private GameTickHandler tickHandler;
	
	private boolean running;
	private long lastUpdateTime;
	
	private MediaPlayer mp = new MediaPlayer();
	
	
	private class GameTickHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	update();
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.activity_main_game);
		
		// Load the maze to play
		final Maze loadedMaze = (Maze) getIntent().getExtras().getSerializable("maze");
		
		
		// Check and validate the maze here
		
		
		
		// Get a reference to the inflated MazeViewport 
		gameView = (MazeViewport) findViewById(R.id.main_game_view);

		/* 
		 * Create a GameController for handling the moving, collisions and physics 
		 * for the game.
		 */
		gameController = new GameController(loadedMaze, gameView);
		
		
		tickHandler = new GameTickHandler();
		
		mp = MediaPlayer.create(this, R.raw.maze);
		mp.setLooping(true);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initAccelerometer();
		running = true;
		tickHandler.sendMessageDelayed(tickHandler.obtainMessage(0), 1000);
		mp.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		running = false;
		sensorManager.unregisterListener(this);
		mp.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		running = false;
		sensorManager.unregisterListener(this);
		mp.release();
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do something here if sensor accuracy changes
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Give the GameController the latest accelerometer readings
		gameController.lastAccelerometerReading_x = event.values[0];
		gameController.lastAccelerometerReading_y = event.values[1];
	}
	
	private boolean initAccelerometer() {
		// Check the device has an accelerometer
		sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
			// Register ourselves as a listener so that we can receive accelerometer updates
			accelerometerSensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
			return true;
		} else {
			// Failure! No accelerometer
			Log.e(this.getClass().getName(), "no accelerometer on device, unable to play game");
			//finish(); // No accelerometer on the emulator
			return false;
		}
	}
	
	/**
	 * Performs an update in the game simulation.
	 */
	private void update() {
		// Only perform updates if the game is running
		if (running) {
			long now = System.currentTimeMillis();
			/*
			 * Only perform an update if the specified delay time has elapsed
			 * since the last update.
			 */
			if ((now - lastUpdateTime) >= GAME_TICK_INTERVAL) {
				//Log.d(this.getClass().getName(), "tick");
				if (gameController.isFinished()) {
					Toast.makeText(this, "Level completed", Toast.LENGTH_LONG).show();
					finish();
					return;
				}
				gameController.update();
				lastUpdateTime = System.currentTimeMillis();
				long updateTime = lastUpdateTime - now;
				tickHandler.sleep(GAME_TICK_INTERVAL - updateTime);
			} else {
				// Wait the remaining time
				tickHandler.sleep(GAME_TICK_INTERVAL - (now - lastUpdateTime));
			}
		}
	}

}
