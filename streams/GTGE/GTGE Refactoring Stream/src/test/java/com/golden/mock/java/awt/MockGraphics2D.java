/**
 * 
 */
package com.golden.mock.java.awt;

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
 * @author MetroidFan2002
 * 
 */
public class MockGraphics2D extends Graphics2D {
	
	public void addRenderingHints(Map arg0) {
	}
	
	public void clip(Shape s) {
	}
	
	public void draw(Shape s) {
	}
	
	public void drawGlyphVector(GlyphVector g, float x, float y) {
	}
	
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		return false;
	}
	
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
	}
	
	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
	}
	
	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
	}
	
	public void drawString(String str, int x, int y) {
	}
	
	public void drawString(String str, float x, float y) {
	}
	
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
	}
	
	public void drawString(AttributedCharacterIterator iterator, float x, float y) {
	}
	
	public void fill(Shape s) {
	}
	
	public Color getBackground() {
		return null;
	}
	
	public Composite getComposite() {
		return null;
	}
	
	public GraphicsConfiguration getDeviceConfiguration() {
		return null;
	}
	
	public FontRenderContext getFontRenderContext() {
		return null;
	}
	
	public Paint getPaint() {
		return null;
	}
	
	public Object getRenderingHint(Key hintKey) {
		return null;
	}
	
	public RenderingHints getRenderingHints() {
		return null;
	}
	
	public Stroke getStroke() {
		return null;
	}
	
	public AffineTransform getTransform() {
		return null;
	}
	
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		return false;
	}
	
	public void rotate(double theta) {
	}
	
	public void rotate(double theta, double x, double y) {
	}
	
	public void scale(double sx, double sy) {
	}
	
	public void setBackground(Color color) {
	}
	
	public void setComposite(Composite comp) {
	}
	
	public void setPaint(Paint paint) {
	}
	
	public void setRenderingHint(Key hintKey, Object hintValue) {
	}
	
	public void setRenderingHints(Map arg0) {
	}
	
	public void setStroke(Stroke s) {
	}
	
	public void setTransform(AffineTransform Tx) {
	}
	
	public void shear(double shx, double shy) {
	}
	
	public void transform(AffineTransform Tx) {
	}
	
	public void translate(int x, int y) {
	}
	
	public void translate(double tx, double ty) {
	}
	
	public void clearRect(int x, int y, int width, int height) {
	}
	
	public void clipRect(int x, int y, int width, int height) {
	}
	
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
	}
	
	public Graphics create() {
		return null;
	}
	
	public void dispose() {
	}
	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
	}
	
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		return false;
	}
	
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		return false;
	}
	
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		return false;
	}
	
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		return false;
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		return false;
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
		return false;
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
	}
	
	public void drawOval(int x, int y, int width, int height) {
	}
	
	public void drawPolygon(int[] points, int[] points2, int points3) {
	}
	
	public void drawPolyline(int[] points, int[] points2, int points3) {
	}
	
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
	}
	
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
	}
	
	public void fillOval(int x, int y, int width, int height) {
	}
	
	public void fillPolygon(int[] points, int[] points2, int points3) {
	}
	
	public void fillRect(int x, int y, int width, int height) {
	}
	
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
	}
	
	public Shape getClip() {
		return null;
	}
	
	public Rectangle getClipBounds() {
		return null;
	}
	
	public Color getColor() {
		return null;
	}
	
	public Font getFont() {
		return null;
	}
	
	public FontMetrics getFontMetrics(Font f) {
		return null;
	}
	
	public void setClip(Shape clip) {
	}
	
	public void setClip(int x, int y, int width, int height) {
	}
	
	public void setColor(Color c) {
	}
	
	public void setFont(Font font) {
	}
	
	public void setPaintMode() {
	}
	
	public void setXORMode(Color c1) {
	}
}
