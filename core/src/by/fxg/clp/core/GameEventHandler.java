package by.fxg.clp.core;

import by.fxg.clp.event.QuestEvent.QuestAcceptedEvent;
import by.fxg.clp.registry.quests.Quest;
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
		if (event.getQuest() == null) return; // what the hell?
		
		session.availableQuests.removeValue(event.getQuest(), true);
		if (session.activeQuest == event.getQuest()) {
			session.activeQuest = null;
		}
		
		if (event.getQuest().hasBeenAccepted()) {
			session.gameInventory.money -= Math.max(-100, event.getQuest().getRevenue() * 0.5f); // max debt cap
			session.failedQuests++;
		}
		
		session.refillQuests();
		session.save();
	}
	
	// Removes quest from the active list, gives money
	public void onQuestCompleted(QuestAcceptedEvent event) {
		final GameSession session = event.getSession();
		final Quest quest = event.getQuest();
		if (quest == null) return; // what the hell?
		
		session.availableQuests.removeValue(quest, true);
		session.activeQuest = null;
		
		if (session.gameInventory.removeItem(quest.getRequiredItem())) {
			final float revenue = quest.getQuestData().requiredItemFound ? quest.getRevenue() : quest.getRevenue() * Quest.REVENUE_MODIFIER_ITEM_NOT_FOUND;
			session.gameInventory.money += revenue;
			session.totalEarned += revenue;
			session.completedQuests++;
		}

		session.refillQuests();
		session.save();
	}
}
