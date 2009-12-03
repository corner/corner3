/* 
 * Copyright 2008 The Corner Team.
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


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
/**
 * 简单的验证码图片生成
 * @author dong
 * @version $Revision: 2956 $
 * @since 0.2
 */
public class CaptchaImageImpl implements CaptchaImage {
	private static final String mimeType = "image/jpeg";

	/* (non-Javadoc)
	 * @see ganshane.services.challengecode.ChallengeImage#getMimeType()
	 */
	public String getMimeType() {
		return mimeType;
	}

	/* (non-Javadoc)
	 * @see ganshane.services.challengecode.ChallengeImage#converString2Image(java.lang.String)
	 */
	public byte[] converString2Image(String content) {
		final Random random = new Random();
		final Font font = getRandFont(random, 15);
		BufferedImage image = new BufferedImage(55, 30,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gra = image.createGraphics();
		gra.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gra.setColor(Color.white);
		gra.fillRect(0, 0, image.getWidth(), image.getHeight());
		gra.setFont(font);
		gra.setColor(getRandColor(random));
		int x = 1;
		int y = 15;
		double px = random.nextDouble()/(random.nextInt(3)+1);
		double py = 0.1;
		AffineTransform ct = AffineTransform.getShearInstance(px, py);
		gra.transform(ct);
		for (int i = 0; i < content.length(); i++) {
			final String s = content.substring(i, i + 1);
			Rectangle2D bounds = font.getStringBounds(s, gra
					.getFontRenderContext());
			int fy = (random.nextInt(5) + y);
			gra.drawString(s, x, fy);
			x += bounds.getWidth() - random.nextInt(4);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	private Font getRandFont(final Random random, final int fontSize) {
		final String fontName = fontNames[random.nextInt(fontNames.length)];
		return new Font(fontName, Font.PLAIN, fontSize);
	}

	private Color getRandColor(final Random random) {
		return colors[random.nextInt(colors.length)];
	}

	private final String[] fontNames = new String[] { "Ravie",
			"Antique Olive Compact", "Forte", "Wide Latin",
			"Gill Sans Ultra Bold" };

	private final Color[] colors = new Color[] { Color.black, Color.blue,
			Color.red };
}
