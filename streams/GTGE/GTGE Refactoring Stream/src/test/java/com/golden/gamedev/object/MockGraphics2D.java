/**
 * 
 */
package com.golden.gamedev.object;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * The {@link MockGraphics2D} class provides a mock {@link Graphics2D}
 * implementation to be used for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public class MockGraphics2D extends Graphics2D {
	
	/**
	 * Flag to set whether or not draw image methods return false. The default
	 * behavior is to return true from these methods.
	 */
	public boolean returnFalseFromDrawImage;
	
	/**
	 * The last {@link Image} drawn via draw image methods in this
	 * {@link MockGraphics2D} instance.
	 */
	public Image lastDrawnImage;
	
	/**
	 * The last {@link ImageObserver} sent to draw image methods in this
	 * {@link MockGraphics2D} instance.
	 */
	public ImageObserver lastDrawnImageObserver;
	
	/**
	 * The last x-coordinate of draw image methods.
	 */
	public int lastDrawnX;
	
	/**
	 * The last y-coordinate of draw image methods.
	 */
	public int lastDrawnY;
	
	/**
	 * The {@link Color} instance that is both {@link #setColor(Color) stored}
	 * and {@link #getColor() retrieved}.
	 */
	private Color color;
	
	/**
	 * The last x-coordinate specified in a fill operation.
	 */
	public int lastFillX;
	
	/**
	 * The last y-coordinate specified in a fill operation.
	 */
	public int lastFillY;
	
	/**
	 * The last height specified in a fill operation.
	 */
	public int lastFillHeight;
	
	/**
	 * The last width specified in a fill operation.
	 */
	public int lastFillWidth;
	
	/**
	 * Creates a new {@link MockGraphics2D} instance.
	 */
	public MockGraphics2D() {
		super();
	}
	
	public void addRenderingHints(Map arg0) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void clip(Shape s) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void draw(Shape s) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawGlyphVector(GlyphVector g, float x, float y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawString(String str, int x, int y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawString(String str, float x, float y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawString(AttributedCharacterIterator iterator, float x, float y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void fill(Shape s) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Color getBackground() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Composite getComposite() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public GraphicsConfiguration getDeviceConfiguration() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public FontRenderContext getFontRenderContext() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Paint getPaint() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Object getRenderingHint(Key hintKey) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public RenderingHints getRenderingHints() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Stroke getStroke() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public AffineTransform getTransform() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void rotate(double theta) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void rotate(double theta, double x, double y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void scale(double sx, double sy) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setBackground(Color color) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setComposite(Composite comp) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setPaint(Paint paint) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setRenderingHint(Key hintKey, Object hintValue) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setRenderingHints(Map arg0) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setStroke(Stroke s) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setTransform(AffineTransform Tx) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void shear(double shx, double shy) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void transform(AffineTransform Tx) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void translate(int x, int y) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void translate(double tx, double ty) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void clearRect(int x, int y, int width, int height) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void clipRect(int x, int y, int width, int height) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Graphics create() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void dispose() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		this.lastDrawnImage = img;
		this.lastDrawnX = x;
		this.lastDrawnY = y;
		this.lastDrawnImageObserver = observer;
		return !returnFalseFromDrawImage;
	}
	
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawOval(int x, int y, int width, int height) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void fillOval(int x, int y, int width, int height) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void fillRect(int x, int y, int width, int height) {
		this.lastFillX = x;
		this.lastFillY = y;
		this.lastFillWidth = width;
		this.lastFillHeight = height;
	}
	
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Shape getClip() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Rectangle getClipBounds() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public Color getColor() {
		return color;
	}
	
	public Font getFont() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public FontMetrics getFontMetrics(Font f) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setClip(Shape clip) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setClip(int x, int y, int width, int height) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public void setFont(Font font) {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setPaintMode() {
		throw new UnsupportedOperationException("To be implemented.");
	}
	
	public void setXORMode(Color c1) {
		throw new UnsupportedOperationException("To be implemented.");
	}
}
