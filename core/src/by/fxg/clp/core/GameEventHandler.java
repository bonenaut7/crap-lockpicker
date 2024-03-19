package by.fxg.clp.core;

import by.fxg.clp.event.QuestEvent.QuestAcceptedEvent;
import by.fxg.clp.session.GameSession;

public class GameEventHandler {
	public GameEventHandler() {
		
	}
	
	// Makes quest active
	public void onQuestAccepted(QuestAcceptedEvent event) {
		final GameSession session = event.getSession();
		if (session.activeQuest == null) return; // what the hell?
		
		session.activeQuest = event.getQuest();
		session.save();
	}
	
	// Removes quest from the active list, charges penalty
	public void onQuestDenied(QuestAcceptedEvent event) {
		final GameSession session = event.getSession();
		if (session.activeQuest == null) return; // what the hell?
		
		session.availableQuests.removeValue(session.activeQuest, true);
		session.activeQuest = null;
		
		if (event.getQuest().hasBeenAccepted()) {
			session.gameInventory.money -= Math.max(-100, event.getQuest().getRevenue() * 0.5f); // max debt cap
		}
		
		session.save();
	}
	
	// Removes quest from the active list, gives money
	public void onQuestCompleted(QuestAcceptedEvent event) {
		final GameSession session = event.getSession();
		if (session.activeQuest == null) return; // what the hell?
		
		// FIXME
		
		session.save();
	}
}
