package edu.osu.cardsvspeople.test;

import edu.osu.cardsvspeople.MenuActivity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.Button;


public class MenuActivityTest extends ActivityUnitTestCase<MenuActivity>
{
	private int buttonid;
	private MenuActivity activity;
	
	public MenuActivityTest () 
	{
		super(MenuActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception 
	{
		
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), MenuActivity.class);
		intent.putExtra("username", "obinnangini");
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);
		startActivity(intent, null, null);
		activity = getActivity();
		//activity = (MenuActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
	}
	
	public void testNewGameButtonText()
	{
		buttonid = edu.osu.cardsvspeople.R.id.game_start_button;
		assertNotNull(activity.findViewById(buttonid));
		Button view = (Button) activity.findViewById(buttonid);
		assertEquals("Incorrect label of button", "New Game!", view.getText());
	}
	public void testHelpButtonText()
	{
		buttonid = edu.osu.cardsvspeople.R.id.help_button;
		assertNotNull(activity.findViewById(buttonid));
		Button view = (Button) activity.findViewById(buttonid);
		assertEquals("Incorrect label of button", "Help", view.getText());
	}
	
	public void testNewGameIntentTrigger()
	{
		buttonid = edu.osu.cardsvspeople.R.id.game_start_button;
		Button view = (Button) activity.findViewById(buttonid);
		assertNotNull("Button not allowed to be null", view);
		
		view.performClick();
		
		Intent triggered = getStartedActivityIntent();
		assertNotNull("Intent was null", triggered);
		String username = null;
		username = triggered.getExtras().getString("username");
		assertNotNull("Username is not null", username);
		
		
	}
}
