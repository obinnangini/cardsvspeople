import edu.osu.cardsvspeople.BlackCard;
import junit.framework.TestCase;


public class BlackCardTests extends TestCase {
	
	BlackCard card;

	protected void setUp() throws Exception {
		super.setUp();
		card = new BlackCard("Test text", 3);
	}

	public void testGetText() {
		assertEquals("Test text", card.getText());
	}

	public void testGetCardType() {
		assertEquals("Black", card.getCardType());
	}

	public void testGetCardsNeeded() {
		assertEquals(3, card.getCardsNeeded());
	}

}
