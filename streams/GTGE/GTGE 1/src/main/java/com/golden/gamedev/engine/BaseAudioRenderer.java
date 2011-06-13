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

// JFC
import java.net.URL;

import org.apache.commons.lang.Validate;

/**
 * A simple abstraction for playing audio sound.
 * <p>
 * 
 * <code>BaseAudioRenderer</code> <b>must</b> have one empty constructor. <br>
 * For example :
 * 
 * <pre>
 * public class MP3AudioRenderer extends BaseAudioRenderer {
 * 	
 * 	public MP3Renderer() { // you should provide an empty constructor
 * 		// init the class here
 * 	}
 * }
 * </pre>
 * 
 * <p>
 * 
 * The empty constructor is used by <code>BaseAudio</code> to create a new renderer instance to play new sound using
 * <code>Class.newInstance()</code>.
 */
public abstract class BaseAudioRenderer {
	
	/** ********************** RENDERER STATUS CONSTANTS ************************ */
	
	/**
	 * Audio renderer status indicates that the audio is currently playing.
	 */
	public static final int PLAYING = 1;
	
	/**
	 * Audio renderer status indicates that the audio is currently stopped.
	 */
	public static final int PAUSED = 2;
	
	/**
	 * Audio renderer status indicates that the audio has finished played.
	 */
	public static final int END_OF_SOUND = 3;
	
	// REVIEW-HIGH: Get rid of this status, exceptions should be thrown, not suppressed.
	/**
	 * Audio renderer status indicates that the audio is failed to play.
	 */
	public static final int ERROR = 4;
	
	/** ************************* RENDERER VARIABLES **************************** */
	
	/**
	 * The possibly-null {@link URL} instance which points to the audio file to be played by this renderer.
	 */
	private URL audiofile;
	
	// REVIEW-HIGH: This field should be deleted and its accessors deleted as well, it is pointless.
	private boolean loop;
	
	// REVIEW-MEDIUM: if the status should be kept, this should be an enumeration.
	// REVIEW-HIGH: If the status should be kept, it shouldn't be writable for subclasses. Need to provide protected
	// method to indicate the sound has stopped (if status needs to be kept).
	/**
	 * The audio renderer status.
	 * <p>
	 * 
	 * Use this to manage renderer's {@link #END_OF_SOUND} status when the audio has finished played or {@link #ERROR}
	 * status if the audio is failed to play in {@link #playSound()} method.
	 */
	protected int status = BaseAudioRenderer.PAUSED;
	
	/**
	 * The audio sound volume, which is in the inclusive range of 0.0f and 1.0f.
	 */
	private float volume = 1.0f;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	// REVIEW-MEDIUM: Add a second constructor that allows setting all options (volume and audio file). Default the
	// volume to 1.0 if none present (null), throw IllegalArgumentException for null URL like the setter.
	// REVIEW-HIGH: A chain of events should occur to allow any BaseAudioRenderer implementation to check hardware for
	// the ability to set the volume, etc, and not be constructed if hardware would make this class useless.
	/**
	 * Creates new audio renderer.
	 * 
	 * @see #play(URL)
	 * @see #setLoop(boolean)
	 */
	public BaseAudioRenderer() {
		super();
	}
	
	/**
	 * Plays sound with specified audio file.
	 */
	protected abstract void playSound();
	
	/**
	 * Replays last played sound.
	 */
	protected abstract void resumeSound();
	
	/**
	 * Stops any playing sound.
	 */
	protected abstract void stopSound();
	
	// REVIEW-HIGH: make abstract.
	/**
	 * Sets audio sound volume.
	 * 
	 * @param volume
	 *            The new volume. The volume can be a value between 0.0f (min volume) and 1.0f (max volume).
	 */
	protected void setSoundVolume(float volume) {
	}
	
	/** ************************************************************************* */
	/** ********************** AUDIO PLAYBACK FUNCTION ************************** */
	/** ************************************************************************* */
	
	// REVIEW-HIGH: This should be made into a setter, and play made distinct.
	/**
	 * Stops currently played audio and begins playback of specified audio file.
	 * 
	 * @param audiofile
	 *            the audio file to be played by this renderer.
	 */
	public void play(URL audiofile) {
		this.status = BaseAudioRenderer.PLAYING;
		if (this.audiofile == audiofile) {
			this.resumeSound();
			return;
		}
		
		this.audiofile = audiofile;
		this.playSound();
	}
	
	/**
	 * Restarts last or currently played audio.
	 */
	public void resumePlayback() {
		this.status = BaseAudioRenderer.PLAYING;
		this.resumeSound();
	}
	
	/**
	 * Stops currently played audio.
	 */
	public void stop() {
		if (this.audiofile != null && this.status == BaseAudioRenderer.PLAYING) {
			this.stopSound();
			this.status = BaseAudioRenderer.PAUSED;
		}
	}
	
	// REVIEW-HIGH: Delete this pointless mutator.
	/**
	 * Sets whether the sound should be playing continuously until stop method is called or not.
	 * <p>
	 * 
	 * Note: {@linkplain com.golden.gamedev.engine.BaseAudio the sound manager} is the one that taking care the audio
	 * loop.
	 * 
	 * @param loop
	 *            true, the audio will be playing continously
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	// REVIEW-HIGH: Delete this pointless accessor.
	/**
	 * Returns whether the audio is playing continuosly or not.
	 * 
	 * @return If the audio is played continuosly.
	 */
	public boolean isLoop() {
		return this.loop;
	}
	
	/** ************************************************************************* */
	/** ************************ AUDIO VOLUME SETTINGS ************************** */
	/** ************************************************************************* */
	
	/**
	 * Sets audio volume.
	 * 
	 * @param volume
	 *            The new volume. The volume can be a value between 0.0f (min volume) and 1.0f (max volume).
	 */
	public final void setVolume(float volume) {
		Validate.isTrue(Float.compare(volume, 0.0f) >= 0 && Float.compare(volume, 1.0f) <= 0,
				"The volume must be between 0.0 and 1.0 inclusive!");
		if (!isVolumeSupported()) {
			throw new IllegalStateException("Setting the volume for this instance of " + getClass()
					+ " is not supported!");
		}
		this.volume = volume;
		this.setSoundVolume(volume);
	}
	
	/**
	 * Returns audio volume.
	 * 
	 * @return The volume. The value can lay between 0.0f and 1.0f
	 * @see #setVolume(float)
	 */
	public final float getVolume() {
		return this.volume;
	}
	
	// REVIEW-HIGH - make abstract.
	/**
	 * Returns whether setting audio volume is supported or not.
	 * 
	 * @return If setting the volume is supported.
	 */
	public boolean isVolumeSupported() {
		return true;
	}
	
	/** ************************************************************************* */
	/** ************************* AUDIO PROPERTIES ****************************** */
	/** ************************************************************************* */
	
	/**
	 * Returns the audio resource URL associated with this audio renderer.
	 * 
	 * @return The URL of the audio resource associated.
	 */
	public final URL getAudioFile() {
		return this.audiofile;
	}
	
	// REVIEW-MEDIUM: Return the enumeration.
	/**
	 * Returns the audio renderer status.
	 * 
	 * @return The status.
	 * @see #PLAYING
	 * @see #PAUSED
	 * @see #END_OF_SOUND
	 * @see #ERROR
	 */
	public int getStatus() {
		return this.status;
	}
	
	// REVIEW-HIGH: remove this method, all base audio renderer implementations will be available upon construction.
	/**
	 * Returns true, if this audio renderer is available to use or false if this renderer is not available to use
	 * (failed to initialized).
	 * 
	 * @return If the renderer is available for use or not.
	 */
	public abstract boolean isAvailable();
}
