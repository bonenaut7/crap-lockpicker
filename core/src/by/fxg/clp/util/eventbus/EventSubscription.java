package by.fxg.clp.util.eventbus;

@FunctionalInterface
public interface EventSubscription<T extends Event> {
	void onEvent(T event);
}
