/*  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.simplemagic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * The main activity of the Simple Magic Tracker program.
 * @author Richard Laughlin
 */
public class SimpleMagicCounterActivity extends Activity {
	
	//Constants
	private static final int NEW_GAME = 1;
    private static final int NEW_PLAYER = 2;
    private static final int REMOVE_PLAYER = 3;
    private static final int SELECT_RANDOM_PLAYER = 4;
    private static final int ROLL_D6 = 5;
    private static final int ROLL_D20 = 6;
	
    //Members
	private LinearLayout mainLayout;
	private ArrayList<LinearLayout> players = new ArrayList<LinearLayout>();
    
	/**
	 * Creates a new player's panel
	 * @return the view that contains the new player's panel
	 */
	private View createPlayer() {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout view = (LinearLayout) layoutInflater.inflate(R.layout.row, null);
		players.add(view);
		
		EditText editor = (EditText) view.findViewById(R.id.editText1);
		editor.setText("20");
		
		Button sub5 = (Button) view.findViewById(R.id.sub5);
		sub5.setOnClickListener(new ClickListener(editor, -5));
		
		Button sub1 = (Button) view.findViewById(R.id.sub1);
		sub1.setOnClickListener(new ClickListener(editor, -1));
		
		Button add1 = (Button) view.findViewById(R.id.add1);
		add1.setOnClickListener(new ClickListener(editor, 1));
		
		Button add5 = (Button) view.findViewById(R.id.add5);
		add5.setOnClickListener(new ClickListener(editor, 5));
		
		return view;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ScrollView mainView = new ScrollView(this);
        
        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        
        mainLayout.addView(createPlayer());
        mainLayout.addView(createPlayer());
        
        mainView.addView(mainLayout);
        setContentView(mainView);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, NEW_GAME, Menu.NONE, "New Game");
    	menu.add(Menu.NONE, NEW_PLAYER, Menu.NONE, "New Player");
    	menu.add(Menu.NONE, REMOVE_PLAYER, Menu.NONE, "Remove Player");
    	menu.add(Menu.NONE, SELECT_RANDOM_PLAYER, Menu.NONE, "Random Player");
    	menu.add(Menu.NONE, ROLL_D6, Menu.NONE, "Roll D6");
    	menu.add(Menu.NONE, ROLL_D20, Menu.NONE, "Roll D20");
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case NEW_GAME:                
                for(LinearLayout view : players) {
                	EditText editor = (EditText) view.findViewById(R.id.editText1);
                	editor.setText("20");
                }
                return true;
            case NEW_PLAYER:
            	mainLayout.addView(createPlayer());
            	return true;
            case REMOVE_PLAYER:
            	if(players.size() > 2) {
	            	LinearLayout removed = players.get(players.size()-1);
	            	players.remove(players.size()-1);
	            	removed.setVisibility(View.GONE);
            	}
            	return true;
            case SELECT_RANDOM_PLAYER:
            	Toast.makeText(this, "Player "+Long.toString((System.currentTimeMillis() % players.size()) + 1), Toast.LENGTH_SHORT).show();
            	return true;
            case ROLL_D6:
            	Toast.makeText(this, Long.toString((System.currentTimeMillis() % 6) + 1), Toast.LENGTH_SHORT).show();
            	return true;
            case ROLL_D20:
            	Toast.makeText(this, Long.toString((System.currentTimeMillis() % 20) + 1), Toast.LENGTH_SHORT).show();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
	 * A listener used to handle the pressing of the delta buttons
	 * @author Richard Laughlin
	 */
	private class ClickListener implements OnClickListener {

		private EditText editor;
		private int delta;
		
		public ClickListener(EditText editor, int delta) {
			this.editor = editor;
			this.delta = delta;
		}
		
		public void onClick(View v) {
			try {
				editor.setText(Integer.toString(Integer.parseInt(editor.getText().toString()) + delta));
			} catch(Exception e) {
				editor.setText(Integer.toString(delta));
			}
		}
		
	}
}