package com.jw.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: 增加描述
 *
 */
public class QRUtils {
	/**
	 * @category 生成二维码二进制流
	 * @throws WriterException
	 * @throws IOException
	 */
	public static byte[] createQr(String url, int width, int height, String format) throws Exception {
		// 加密
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		bitMatrix = deleteWhite(bitMatrix, 10);
		ByteArrayOutputStream swapStream = null;
		byte[] data = null;

		try {
			swapStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, format, swapStream);
			// 得到二进制流
			data = swapStream.toByteArray();
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != swapStream) {
				try {
					swapStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @category 去除白邊
	 * @param matrix
	 * @param margin
	 * @return
	 */
	public static BitMatrix deleteWhite(BitMatrix matrix, int margin) {
		int tempM = margin * 2;
		int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
		resMatrix.clear();
		for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
			for (int j = margin; j < resHeight - margin; j++) {
				if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
					resMatrix.set(i, j);
				}
			}
		}
		return resMatrix;
	}
}
