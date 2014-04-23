package uk.ac.tees.amazeballs;

import uk.ac.tees.amazeballs.maze.Maze;
import uk.ac.tees.amazeballs.maze.MazeFactory;
import uk.ac.tees.amazeballs.maze.TileFactory;
import uk.ac.tees.amazeballs.maze.TileType;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MazeEditorActivity extends Activity {

	private Maze currentMaze;
	private MazeEditor mazeEditorView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maze_editor);
		
		mazeEditorView = (MazeEditor) findViewById(R.id.maze_grid_view);
		
		TileFactory.registerTile(TileType.Floor, this.getResources().getDrawable(R.drawable.floor));
		TileFactory.registerTile(TileType.Wall, this.getResources().getDrawable(R.drawable.wall));
		
		currentMaze = MazeFactory.createBorderedMaze(10, 15);
		
		mazeEditorView.setMaze(currentMaze);
	}

}
