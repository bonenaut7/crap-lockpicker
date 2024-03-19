package by.fxg.clp.event;

import by.fxg.clp.registry.quests.Quest;
import by.fxg.clp.session.GameSession;

public abstract class QuestEvent {
	private final GameSession session;
	private final Quest quest;
	
	protected QuestEvent(GameSession session, Quest quest) {
		this.session = session;
		this.quest = quest;
	}
	
	public GameSession getSession() {
		return this.session;
	}
	
	public Quest getQuest() {
		return this.quest;
	}
	
	public static class QuestAcceptedEvent extends QuestEvent {
		public QuestAcceptedEvent(GameSession session, Quest quest) {
			super(session, quest);
		}
	}
	
	public static class QuestDeniedEvent extends QuestEvent {
		public QuestDeniedEvent(GameSession session, Quest quest) {
			super(session, quest);
		}
	}
	
	public static class QuestCompletedEvent extends QuestEvent {
		public QuestCompletedEvent(GameSession session, Quest quest) {
			super(session, quest);
		}
	}
}
