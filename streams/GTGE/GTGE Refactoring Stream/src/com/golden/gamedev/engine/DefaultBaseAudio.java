/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.golden.gamedev.engine;

// GTGE
import java.net.URL;

import com.golden.gamedev.util.Utility;

/**
 * Audio manager that manages playing, stopping, looping of multiple audio
 * sounds (<code>BaseAudioRenderer</code>s).
 * <p>
 * 
 * Audio manager takes up a single base renderer parameter. The base is used to
 * create new instance of <code>BaseAudioRenderer</code> to play new audio
 * sound.
 * <p>
 * 
 * Audio manager also take care any idle renderer and looping audio renderer.
 * <p>
 * 
 * This class is using {@link DefaultBaseIO} to get the external resources.
 */
public class DefaultBaseAudio implements BaseAudio, Runnable {
	
	/** *************************** AUDIO POLICY ******************************** */
	
	private int audioPolicy = BaseAudio.MULTIPLE; // default audio policy
	
	private int maxSimultaneous; // max simultaneous audio sound
	// played at a time
	
	/** ************************** AUDIO RENDERER ******************************* */
	
	private BaseAudioRenderer baseRenderer;
	
	private BaseAudioRenderer[] renderers;
	
	private String[] rendererFile; // store the filename of
	// the rendered audio
	
	private String lastAudioFile; // last played audio
	
	/** ************************* MANAGER PROPERTIES **************************** */
	
	private BaseIO base;
	
	private boolean exclusive; // only one clip can be played at a time
	
	private boolean loop; // ALL clips are played continously or not
	
	private float volume;
	
	private boolean active;
	
	private int buffer; // total audio renderer instances before
	
	// attempting to replace idle renderer
	
	/** ************************************************************************* */
	/** ************************** CONSTRUCTOR ********************************** */
	/** ************************************************************************* */
	
	/**
	 * Creates new audio manager with specified renderer as the base renderer of
	 * all audio sounds created by this audio manager.
	 * <p>
	 * 
	 * @param base the BaseIO to get audio resources
	 * @param baseRenderer the base renderer of this audio manager
	 */
	public DefaultBaseAudio(BaseIO base, BaseAudioRenderer baseRenderer) {
		this.setBase(base);
		this.setBaseRenderer(baseRenderer);
		
		this.setActive(baseRenderer.isAvailable());
		this.setVolume(1.0f);
		this.setBuffer(10);
		this.setMaxSimultaneous(6);
		
		this.setRenderers(new BaseAudioRenderer[0]);
		this.setRendererFile(new String[0]);
		
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(100L);
			}
			catch (InterruptedException e) {
			}
			
			for (int i = 0; i < this.getRenderers().length; i++) {
				if (this.getRenderers()[i].isLoop()
				        && this.getRenderers()[i].getStatus() == BaseAudioRenderer.END_OF_SOUND) {
					this.getRenderers()[i].play();
				}
			}
		}
	}
	
	/** ************************************************************************* */
	/** ********************** PLAYING AUDIO OPERATION ************************** */
	/** ************************************************************************* */
	
	public int play(String audiofile) {
		return this.play(audiofile, this.getAudioPolicy());
	}
	
	public int play(String audiofile, int policy) {
		this.setLastAudioFile(audiofile);
		
		if (!this.isActive()) {
			return -1;
		}
		
		// -2 means attempt to replace idle renderer
		// since total renderer has exceed buffer size
		int emptyslot = (this.getRenderers().length <= this.getBuffer()) ? -1
		        : -2;
		
		// to count simultaneous playing sound
		int playedSound = 0;
		
		for (int i = 0; i < this.getRenderers().length; i++) {
			if (this.getRendererFile()[i].equals(audiofile)) {
				if (this.getRenderers()[i].getStatus() == BaseAudioRenderer.PLAYING) {
					playedSound++;
				}
				
				if (policy == BaseAudio.MULTIPLE && !this.isExclusive()) {
					if (this.getRenderers()[i].getStatus() != BaseAudioRenderer.PLAYING) {
						this.getRenderers()[i].setVolume(this.getVolume());
						this.getRenderers()[i].play();
						return i;
					}
					
				}
				else if (policy == BaseAudio.SINGLE_REPLAY) {
					// replay the sound
					if (this.isExclusive()) {
						this.stopAll();
					}
					else {
						this.getRenderers()[i].stop();
					}
					
					this.getRenderers()[i].setVolume(this.getVolume());
					this.getRenderers()[i].play();
					
					return i;
					
				}
				else {
					// single policy no replay OR
					// multiple policy and exclusive mode
					if (this.isExclusive()) {
						// stop all except this audio renderer
						this.stopAll(this.getRenderers()[i]);
					}
					
					if (this.getRenderers()[i].getStatus() != BaseAudioRenderer.PLAYING) {
						this.getRenderers()[i].setVolume(this.getVolume());
						this.getRenderers()[i].play();
					}
					
					return i;
				}
			}
			
			// replace this idle slot
			if (emptyslot == -2
			        && this.getRenderers()[i].getStatus() != BaseAudioRenderer.PLAYING) {
				emptyslot = i;
			}
		}
		
		// ////// attempt to play sound in new slot ////////
		
		// check for simultaneous sounds
		if (playedSound >= this.getMaxSimultaneous()) {
			// too many simultaneous sounds!
			return -1;
		}
		
		if (emptyslot < 0) {
			// no empty slot, expand the renderer array
			this.setRenderers((BaseAudioRenderer[]) Utility.expand(this
			        .getRenderers(), 1));
			this.setRendererFile((String[]) Utility.expand(this
			        .getRendererFile(), 1));
			emptyslot = this.getRenderers().length - 1;
		}
		
		if (this.getRenderers()[emptyslot] == null) {
			// create new renderer in the empty slot
			this.getRenderers()[emptyslot] = this.createRenderer();
			this.getRenderers()[emptyslot].setLoop(this.isLoop());
		}
		
		if (this.isExclusive()) {
			// in exclusive mode, only one clip can be played at a time
			this.stopAll();
		}
		else {
			// to be sure the renderer is not playing
			this.stop(emptyslot);
		}
		
		this.getRenderers()[emptyslot].setVolume(this.getVolume());
		this.getRenderers()[emptyslot].play(this
		        .retrieveAudiofileUrl(audiofile));
		this.getRendererFile()[emptyslot] = audiofile;
		
		return emptyslot;
	}
	
	public URL retrieveAudiofileUrl(String audiofile) {
		return this.getBase().getURL(audiofile);
	}
	
	public void stop(int slot) {
		if (this.getRenderers()[slot].getStatus() == BaseAudioRenderer.PLAYING) {
			this.getRenderers()[slot].stop();
		}
	}
	
	public void stop(String audiofile) {
		BaseAudioRenderer audio = this.getAudioRenderer(audiofile);
		
		if (audio != null) {
			audio.stop();
		}
	}
	
	public void stopAll() {
		int count = this.getRenderers().length;
		for (int i = 0; i < count; i++) {
			this.stop(i);
		}
	}
	
	public void stopAll(BaseAudioRenderer except) {
		int count = this.getRenderers().length;
		for (int i = 0; i < count; i++) {
			if (this.getRenderers()[i] != except) {
				this.stop(i);
			}
		}
	}
	
	/** ************************************************************************* */
	/** ********************* LOADED RENDERER TRACKER *************************** */
	/** ************************************************************************* */
	
	public BaseAudioRenderer getAudioRenderer(int slot) {
		return this.getRenderers()[slot];
	}
	
	public BaseAudioRenderer getAudioRenderer(String audiofile) {
		int count = this.getRenderers().length;
		for (int i = 0; i < count; i++) {
			// find renderer with specified audio file
			if (this.getRendererFile()[i].equals(audiofile)) {
				return this.getRenderers()[i];
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the last played audio file.
	 * <p>
	 * 
	 * This method is used for example when audio manager is set to active state
	 * from inactive state, if the game wish to play the last played audio, call
	 * {@link #play(String) play(getLastAudioFile())}.
	 * @return The last played audio file.
	 * @see #play(String)
	 */
	public String getLastAudioFile() {
		return this.lastAudioFile;
	}
	
	/**
	 * Returns total audio renderer created within this audio manager.
	 * @return The number of associated renderers.
	 * @see #getRenderers()
	 */
	public int getCountRenderers() {
		return this.getRenderers().length;
	}
	
	/** ************************************************************************* */
	/** ******************** SETTINGS AUDIO VOLUME ****************************** */
	/** ************************************************************************* */
	
	public float getVolume() {
		return this.volume;
	}
	
	public void setVolume(float volume) {
		if (volume < 0.0f) {
			volume = 0.0f;
		}
		if (volume > 1.0f) {
			volume = 1.0f;
		}
		
		if (this.getBaseRenderer().isVolumeSupported() == false
		        || this.volume == volume) {
			return;
		}
		
		this.volume = volume;
		
		int count = this.getRenderers().length;
		for (int i = 0; i < count; i++) {
			this.getRenderers()[i].setVolume(volume);
		}
	}
	
	public boolean isVolumeSupported() {
		return this.getBaseRenderer().isVolumeSupported();
	}
	
	/** ************************************************************************* */
	/** ************************ MANAGER PROPERTIES ***************************** */
	/** ************************************************************************* */
	
	public int getAudioPolicy() {
		return this.audioPolicy;
	}
	
	public void setAudioPolicy(int i) {
		this.audioPolicy = i;
	}
	
	public int getMaxSimultaneous() {
		return this.maxSimultaneous;
	}
	
	public void setMaxSimultaneous(int i) {
		this.maxSimultaneous = i;
	}
	
	public boolean isExclusive() {
		return this.exclusive;
	}
	
	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
		
		if (exclusive) {
			this.stopAll();
		}
	}
	
	public int getBuffer() {
		return this.buffer;
	}
	
	public void setBuffer(int i) {
		this.buffer = i;
	}
	
	public boolean isLoop() {
		return this.loop;
	}
	
	public void setLoop(boolean loop) {
		if (this.loop == loop) {
			return;
		}
		
		this.loop = loop;
		
		int count = this.getRenderers().length;
		for (int i = 0; i < count; i++) {
			this.getRenderers()[i].setLoop(loop);
		}
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean b) {
		this.active = (this.isAvailable()) ? b : false;
		
		if (!this.active) {
			this.stopAll();
		}
	}
	
	public boolean isAvailable() {
		return this.getBaseRenderer().isAvailable();
	}
	
	/** ************************************************************************* */
	/** *************************** BASE RENDERER ******************************* */
	/** ************************************************************************* */
	
	public BaseAudioRenderer getBaseRenderer() {
		return this.baseRenderer;
	}
	
	public void setBaseRenderer(BaseAudioRenderer renderer) {
		this.baseRenderer = renderer;
		
		if (this.isActive()) {
			this.setActive(this.baseRenderer.isAvailable());
		}
	}
	
	/**
	 * Constructs new audio renderer to play new audio sound.
	 * <p>
	 * 
	 * The new audio renderer is created using {@link Class#forName(String)}
	 * from the {@linkplain #getBaseRenderer() base renderer} class name.
	 * @return The new created renderer.
	 * @see #getBaseRenderer()
	 */
	protected BaseAudioRenderer createRenderer() {
		try {
			return (BaseAudioRenderer) Class.forName(
			        this.getBaseRenderer().getClass().getName()).newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(
			        "Unable to create new instance of audio renderer on "
			                + this
			                + " audio manager caused by: "
			                + e.getMessage()
			                + "\n"
			                + "Make sure the base renderer has one empty constructor!");
		}
	}
	
	public void setRenderers(BaseAudioRenderer[] renderers) {
		this.renderers = renderers;
	}
	
	public BaseAudioRenderer[] getRenderers() {
		return this.renderers;
	}
	
	public void setRendererFile(String[] rendererFile) {
		this.rendererFile = rendererFile;
	}
	
	public String[] getRendererFile() {
		return this.rendererFile;
	}
	
	public void setLastAudioFile(String lastAudioFile) {
		this.lastAudioFile = lastAudioFile;
	}
	
	public void setBase(BaseIO base) {
		this.base = base;
	}
	
	public BaseIO getBase() {
		return this.base;
	}
	
}
