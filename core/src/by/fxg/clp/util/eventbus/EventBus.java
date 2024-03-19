package by.fxg.clp.util.eventbus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.Lists;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EventBus {
	private static Map<Class<? extends Event>, List<EventSubscription>> eventRegistry = new HashMap<>();
	
	public static <T extends Event> void register(Class<T> type, EventSubscription<T> subscription) {
		Objects.requireNonNull(type, "Type can't be null");
		Objects.requireNonNull(subscription, "Subscription object can't be null");
		
		if (!eventRegistry.containsKey(type)) {
			eventRegistry.put(type, Lists.newArrayList(subscription));
		} else if (!eventRegistry.get(type).contains(subscription)) {
			eventRegistry.get(type).add(subscription);
		}
	}
	
	public static <T extends Event> T post(T mail) {
		if (mail == null) return null;
		
		List<EventSubscription> subscriptions = eventRegistry.get(mail.getClass());
		if (subscriptions != null) {
			subscriptions.forEach(subscription -> subscription.onEvent(mail));
		}
		return mail;
	}
}
