package by.fxg.clp.util.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import by.fxg.clp.registry.GameRegistry;
import by.fxg.clp.registry.merchant.locks.LockState;

public class LockStateSerializer extends Serializer<LockState<?>> {
	@Override
	public void write(Kryo kryo, Output output, LockState<?> object) {
		final int lockIndex = GameRegistry.REGISTERED_LOCKS.indexOf(object.getLock(), true);
		if (lockIndex < 0 || lockIndex >= GameRegistry.REGISTERED_LOCKS.size) { // is lock is undefined or is it opened lock
			output.writeBoolean(true);
			return;
		} else {
			output.writeBoolean(false);
			output.writeInt(lockIndex);
			output.writeBoolean(object.isLocked());
			output.writeLong(object.getSeed0());
			output.writeLong(object.getSeed1());
		}
	}

	@Override
	public LockState<?> read(Kryo kryo, Input input, Class<? extends LockState<?>> type) {
		if (input.readBoolean()) {
			return GameRegistry.lockOpened.createLockState();
		}
		
		return new LockState<>(GameRegistry.REGISTERED_LOCKS.get(input.readInt()), input.readBoolean(), input.readLong(), input.readLong());
	}
}
