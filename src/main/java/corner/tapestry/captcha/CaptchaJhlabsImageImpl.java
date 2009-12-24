/* 
 * Copyright 2009 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.tapestry.captcha;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jhlabs.image.TwirlFilter;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class CaptchaJhlabsImageImpl implements CaptchaImage {
	private static final String mimeType = "image/jpeg";

	/** 生成的图片宽度 * */
	private final int width;
	/** 生成的图片高度* */
	private final int height;
	/** 字体大小 * */
	private final int fontSize;
	/** 字符在图片开始输出的X座标* */
	private final int fontX;
	/** 字符在图片开始输出的Y座标* */
	private final int fontY;

	public CaptchaJhlabsImageImpl(int width, int height, int fontSize,
			int fontX, int fontY) {
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		this.fontX = fontX;
		this.fontY = fontY;
	}

	/**
	 * @see corner.tapestry.captcha.CaptchaImage#converString2Image(java.lang.String)
	 */
	@Override
	public byte[] converString2Image(String cont) {
		// 将文字内容写入到图片中
		String content = cont.toUpperCase();
		Random random = new Random();
		BufferedImage image = new BufferedImage(width - 2, height - 2,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gra = (Graphics2D) image.getGraphics();
		gra.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		gra.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		gra.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gra.setColor(Color.white);
		gra.fillRect(0, 0, image.getWidth(), image.getHeight());
		// 得到随机的字符属性设置
		AttributedString attribute = getAttributedString(content, random);
		gra.drawString(attribute.getIterator(), fontX, fontY);
		AttributedCharacterIterator ir = attribute.getIterator();

		drawRandomPoint(random, gra, ir);
		gra.dispose();
		gra = null;

		/*
		 * 增加对图片的处理过滤器
		 */
		List<BufferedImageOp> filters = new LinkedList<BufferedImageOp>();
		// filters.add(genWaterFilter(random));
		filters.add(genTwirlFilter(random));
		for (BufferedImageOp filter : filters) {
			filter.filter(image, image);
		}

		BufferedImage clone = new BufferedImage(image.getWidth() + 2, image
				.getHeight() + 2, BufferedImage.TYPE_INT_RGB);
		Graphics2D cgra = (Graphics2D) clone.getGraphics();
		cgra.fillRect(1, 1, clone.getWidth() - 2, clone.getHeight() - 2);
		cgra.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
		cgra.setColor(Color.black);
		cgra.drawImage(image, 1, 1, null);
		cgra.dispose();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(clone, "jpg", out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

	private void drawRandomPoint(Random random, Graphics2D gra,
			AttributedCharacterIterator ir) {
		gra.setColor(Color.BLACK);
		Stroke _stroke = gra.getStroke();
		gra.setStroke(new BasicStroke(1.5f));
		for (int i = 0; i < 1; i++) {
			gra.drawOval(randomIntRange(random, 1, 10),randomIntRange(random, 1, 10), width, height/2);
		}
		gra.setStroke(_stroke);
	}

	/**
	 * @see corner.tapestry.captcha.CaptchaImage#getMimeType()
	 */
	@Override
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * 设置word的字体等属性
	 * 
	 * @param word
	 *            字符内容
	 * @param random
	 *            随机因子
	 * @return 经过随机设置的字体属性
	 */
	private AttributedString getAttributedString(String word, Random random) {
		AttributedString attributedWord = new AttributedString(word);
		for (int i = 0; i < word.length(); i++) {
			Font font = getRandFont(fontSize, random);
			Color color = getRandColor(random);
			attributedWord.addAttribute(TextAttribute.FONT, font, i, i + 1);
			attributedWord.addAttribute(TextAttribute.FOREGROUND, color, i,
					i + 1);
		}
		return attributedWord;
	}

	/**
	 * 取得随机字体
	 * 
	 * @param fontSize
	 *            字体尺寸
	 * @param random
	 *            随机因子
	 * @return 随机生成的字体
	 */
	private Font getRandFont(final int fontSize, final Random random) {
		final String fontName = fontNames[random.nextInt(fontNames.length)];
		final int fontStyle = Font.BOLD
				| (random.nextInt(2) == 0 ? Font.ITALIC : 0);
		return new Font(fontName, fontStyle, fontSize);
	}

	/**
	 * 取得随机色
	 * 
	 * @param random
	 * @return
	 */
	private Color getRandColor(final Random random) {
		return colors[random.nextInt(colors.length)];
	}

	private BufferedImageOp genTwirlFilter(final Random random) {
		TwirlFilter tw = new TwirlFilter();
		tw.setCentreX((float) randomRange(random, 10, 150) / (width + 0.0f));
		tw.setCentreY((float) randomRange(random, 10, 25) / (height + 0.0f));
		tw.setAngle((float) (randomRange(random, 3, 6) * 0.1f));
		tw.setRadius((float) randomRange(random, 100, 130));
		return tw;
	}

	/**
	 * 取得在范围在min和max之间的随机数
	 * 
	 * @param random
	 * @param min
	 * @param max
	 * @return
	 */
	private double randomRange(Random random, int min, int max) {
		int ran = random.nextInt(max);
		if (ran < min) {
			ran = (int) Math.round(min + Math.sqrt(random.nextInt(max - min)));
		}
		return ran;
	}

	private int randomIntRange(Random random, int min, int max) {
		int ran = random.nextInt(max);
		if (ran < min) {
			ran = (int) Math.round(min + Math.sqrt(random.nextInt(max)));
		}
		return ran;
	}

	private final String[] fontNames = new String[] { "URW Chancery L",
			"Purisa" };

	private final Color[] colors = new Color[] { Color.black, Color.blue,
			Color.red };

}
