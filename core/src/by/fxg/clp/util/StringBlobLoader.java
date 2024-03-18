package by.fxg.clp.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class StringBlobLoader extends AsynchronousAssetLoader<StringBlob, AssetLoaderParameters<StringBlob>> {
	private final Array<AssetDescriptor> dependencies = new Array<>();
	private String[] array = null;
	
	public StringBlobLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AssetLoaderParameters<StringBlob> parameter) {
		return this.dependencies;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<StringBlob> parameter) {
		try {
			final LineNumberReader reader = new LineNumberReader(new InputStreamReader(file.read(), StandardCharsets.UTF_8));
			this.array = (String[])reader.lines().toArray(); // TODO allow comments and read manually?
			reader.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	@Override
	public StringBlob loadSync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<StringBlob> parameter) {
		final StringBlob blob = new StringBlob(this.array);
		this.array = null;
		return blob;
	}
}
