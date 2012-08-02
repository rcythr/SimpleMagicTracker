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

import com.simplemagic.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
		
		EditText health = (EditText) view.findViewById(R.id.health);
		health.setText("20");
		
		Button sub5_health = (Button) view.findViewById(R.id.sub5_health);
		sub5_health.setOnClickListener(new ClickListener(health, -5));
		
		Button sub1_health = (Button) view.findViewById(R.id.sub1_health);
		sub1_health.setOnClickListener(new ClickListener(health, -1));
		
		Button add1_health = (Button) view.findViewById(R.id.add1_health);
		add1_health.setOnClickListener(new ClickListener(health, 1));
		
		Button add5_health = (Button) view.findViewById(R.id.add5_health);
		add5_health.setOnClickListener(new ClickListener(health, 5));
		
		EditText poison = (EditText) view.findViewById(R.id.poison);
		poison.setText("0");
		
		Button sub5_poison = (Button) view.findViewById(R.id.sub5_poison);
		sub5_poison.setOnClickListener(new ClickListener(poison, -5));
		
		Button sub1_poison = (Button) view.findViewById(R.id.sub1_poison);
		sub1_poison.setOnClickListener(new ClickListener(poison, -1));
		
		Button add1_poison = (Button) view.findViewById(R.id.add1_poison);
		add1_poison.setOnClickListener(new ClickListener(poison, 1));
		
		Button add5_poison = (Button) view.findViewById(R.id.add5_poison);
		add5_poison.setOnClickListener(new ClickListener(poison, 5));
		
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
    	MenuInflater inflate = getMenuInflater();
    	inflate.inflate(R.layout.menu, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:                
                for(LinearLayout view : players) {
                	EditText health = (EditText) view.findViewById(R.id.health);
                	health.setText("20");
                	
                	EditText poison = (EditText) view.findViewById(R.id.poison);
                	poison.setText("0");
                }
                return true;
            case R.id.new_player:
            	mainLayout.addView(createPlayer());
            	return true;
            case R.id.remove_player:
            	if(players.size() > 2) {
	            	LinearLayout removed = players.get(players.size()-1);
	            	players.remove(players.size()-1);
	            	removed.setVisibility(View.GONE);
            	}
            	return true;
            case R.id.random_player:
            	Toast.makeText(this, getString(R.string.player)+" "+Long.toString((System.currentTimeMillis() % players.size()) + 1), Toast.LENGTH_SHORT).show();
            	return true;
            case R.id.roll_d6:
            	Toast.makeText(this, Long.toString((System.currentTimeMillis() % 6) + 1), Toast.LENGTH_SHORT).show();
            	return true;
            case R.id.roll_d20:
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