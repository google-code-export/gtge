package com.golden.gamedev.engine;

import java.net.URL;

public interface BaseAudio {
	
	/**
	 * Audio clip with a same name only can be played once at a time. The audio
	 * clip is continued if the clip is currently playing.
	 * <p>
	 * 
	 * To force the clip to replay, set the audio policy to
	 * {@link #SINGLE_REPLAY} instead.
	 * 
	 * @see #setAudioPolicy(int)
	 * @see #play(String, int)
	 */
	public static final int SINGLE = 0;
	/**
	 * Multiple audio clips can be played at the same time (simultaneous). <br>
	 * Note: when using {@link #setExclusive(boolean) exclusive mode} (only
	 * <b>one</b> audio clip can be played at a time), <code>MULTIPLE</code>
	 * policy is obsolete, and automatically changed into {@link #SINGLE}.
	 * 
	 * @see #setAudioPolicy(int)
	 * @see #play(String, int)
	 */
	public static final int MULTIPLE = 1;
	/**
	 * Same as {@link #SINGLE} policy except the audio clip is force to replay.
	 * 
	 * @see #setAudioPolicy(int)
	 * @see #play(String, int)
	 */
	public static final int SINGLE_REPLAY = 2;
	
	/**
	 * Plays audio clip with {@link #getAudioPolicy() default policy}.
	 * @param audiofile Name of the audio file to play.
	 * @return Slot which the audio is played.
	 * @see #getAudioRenderer(int)
	 */
	public int play(String audiofile);
	
	/**
	 * Plays an audio clip based on specified policy ({@link #SINGLE},
	 * {@link #MULTIPLE}, {@link #SINGLE_REPLAY}).
	 * @param audiofile The audio file to play.
	 * @param policy The policy to use for playing.
	 * @return Slot which the audio is played.
	 * @see #getAudioRenderer(int)
	 */
	public int play(String audiofile, int policy);
	
	/**
	 * Retrieves the {@link URL} representing the audio file in the given
	 * String.
	 * @param audiofile The String representing the audio file to retrieve an
	 *        {@link URL} from.
	 * @return The {@link URL} representing the audio file in the given String.
	 */
	public URL retrieveAudiofileUrl(String audiofile);
	
	/**
	 * Returns the default audio policy used by this audio manager to play audio
	 * sound when no audio policy is specified.
	 * @return The default audio policy.
	 * @see #play(String)
	 */
	public int getAudioPolicy();
	
	/**
	 * Returns maximum simultaneous same audio sound can be played at a time.
	 * @return The maximum amount of sounds that can be played the same time.
	 */
	public int getMaxSimultaneous();
	
	/**
	 * Stops audio playback in specified slot.
	 * @param slot The slot to be stopped.
	 */
	public void stop(int slot);
	
	/**
	 * Stops audio playback with specified name.
	 * @param audiofile The audio file to stop.
	 */
	public void stop(String audiofile);
	
	/**
	 * Stops all played audio playbacks in this audio manager.
	 */
	public void stopAll();
	
	/**
	 * Stops all played audio playbacks in this audio manager except specified
	 * renderer.
	 * @param except The playback that shall not be stopped.
	 * @see #getAudioRenderer(String)
	 * @see #getAudioRenderer(int)
	 */
	public void stopAll(BaseAudioRenderer except);
	
	/**
	 * Returns audio renderer with specified audio file or null if not found.
	 * @param audiofile The audio file name of the renderer to return.
	 * @return The audion renderer of the given audio file.
	 */
	public BaseAudioRenderer getAudioRenderer(String audiofile);
	
	/**
	 * Returns audio renderer in specified slot.
	 * @param slot The slot of the audio renderer to return.
	 * @return The audio renderer of the given slot.
	 */
	public BaseAudioRenderer getAudioRenderer(int slot);
	
	/**
	 * Sets the default audio policy used by this audio manager to play audio
	 * sound when no audio policy is specified.
	 * 
	 * @param i the default audio policy, one of {@link #SINGLE},
	 *        {@link #MULTIPLE}, {@link #SINGLE_REPLAY}
	 * @see #play(String)
	 */
	public void setAudioPolicy(int i);
	
	/**
	 * Returns audio manager volume.
	 * @return The volume.
	 * @see #setVolume(float)
	 */
	public float getVolume();
	
	/**
	 * Sets audio manager volume range in [0.0f - 1.0f].
	 * <p>
	 * 
	 * If setting volume of {@linkplain #getBaseRenderer() base renderer} is not
	 * supported, this method will return immediately.
	 * @param volume The new volume.
	 * @see #getVolume()
	 */
	public void setVolume(float volume);
	
	/**
	 * Returns whether setting audio volume is supported or not.
	 * @return If changing the volume is supported.
	 */
	public boolean isVolumeSupported();
	
	/**
	 * Sets maximum simultaneous same audio sound can be played at a time.
	 * @param i The maximum of amount of sounds that can be played the same
	 *        time.
	 */
	public void setMaxSimultaneous(int i);
	
	/**
	 * Returns true, if only one clip is allowed to play at a time.
	 * @return IF only one clip can be played at a time.
	 * @see #setExclusive(boolean)
	 */
	public boolean isExclusive();
	
	/**
	 * Sets whether only one clip is allowed to play at a time or not.
	 * 
	 * @param exclusive true, only one clip is allowed to play at a time
	 * @see #isExclusive()
	 */
	public void setExclusive(boolean exclusive);
	
	/**
	 * Returns total renderer allowed to create before audio manager attempt to
	 * replace idle renderer.
	 * @return The renderer buffer size.
	 * @see #setBuffer(int)
	 */
	public int getBuffer();
	
	/**
	 * Sets total renderer allowed to create before audio manager attempt to
	 * replace idle renderer.
	 * @param i The new renderer buffer size.
	 * @see #getBuffer()
	 */
	public void setBuffer(int i);
	
	/**
	 * Returns true, if all the audio sounds are played continously.
	 * @return If all sounds are played continously.
	 * @see #setLoop(boolean)
	 */
	public boolean isLoop();
	
	/**
	 * Sets whether all the audio sounds should be played continously or not.
	 * @param loop If all sounds shall be played continously.
	 * @see #isLoop()
	 */
	public void setLoop(boolean loop);
	
	/**
	 * Returns true, if this audio manager is fully functional.
	 * @return If the audion manager is fully functional.
	 * @see #setActive(boolean)
	 */
	public boolean isActive();
	
	/**
	 * Turn on/off this audio manager.
	 * <p>
	 * 
	 * Note: {@linkplain #isAvailable() unavailable} audio manager can't be
	 * switch to on.
	 * 
	 * @param b true, turn on this audio manager
	 * @see #isActive()
	 * @see #isAvailable()
	 */
	public void setActive(boolean b);
	
	/**
	 * Returns whether this audio manager is available to use or not.
	 * <p>
	 * 
	 * Unavailable audio manager is caused by
	 * {@link BaseAudioRenderer#isAvailable() unavailable}
	 * {@link #getBaseRenderer() base renderer}.
	 * @return If the manager is available.
	 */
	public boolean isAvailable();
	
	/**
	 * Gets an array of {@link BaseAudioRenderer audio renderers} to be used in
	 * this <tt>BaseAudio</tt> instance.
	 * @return An array of {@link BaseAudioRenderer audio renderers} to be used
	 *         in this <tt>BaseAudio</tt> instance.
	 */
	public BaseAudioRenderer[] getRenderers();
	
	/**
	 * Sets an array of Strings representing the audio files being managed by
	 * this <tt></tt> instance.
	 * @param rendererFile An array of Strings representing the audio files
	 *        being managed by this <tt></tt> instance.
	 */
	public void setRendererFile(String[] rendererFile);
	
	/**
	 * Gets an array of Strings representing the audio files being managed by
	 * this <tt></tt> instance.
	 * @return An array of Strings representing the audio files being managed by
	 *         this <tt></tt> instance.
	 */
	public String[] getRendererFile();
	
	/**
	 * Sets the last played audio file.
	 * @param lastAudioFile The last played audio file.
	 */
	public void setLastAudioFile(String lastAudioFile);
	
	/**
	 * Returns the base renderer of this audio manager.
	 * @return The base renderer.
	 * @see #setBaseRenderer(BaseAudioRenderer)
	 */
	public BaseAudioRenderer getBaseRenderer();
	
	/**
	 * Sets specified audio renderer as this audio manager base renderer.
	 * 
	 * All renderers in this audio manager are created based on this base
	 * renderer.
	 * @param renderer The base renderer used to create renderers.
	 * @see #getBaseRenderer()
	 */
	public void setBaseRenderer(BaseAudioRenderer renderer);
	
	/**
	 * Sets an array of {@link BaseAudioRenderer audio renderers} to be used in
	 * this <tt>BaseAudio</tt> instance.
	 * @param renderers An array of {@link BaseAudioRenderer audio renderers} to
	 *        be used in this <tt>BaseAudio</tt> instance.
	 */
	public void setRenderers(BaseAudioRenderer[] renderers);
	
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
	public String getLastAudioFile();
	
}
