package de.fhdw.nohn.cm.backed.network.packet;

import java.io.IOException;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;

public class PacketUtil {
	
	public static int readVarInt(ByteBuf buffer) {
		int number = 0;
		int round = 0;
		byte currentByte;

		do {
			currentByte = buffer.readByte();
			number |= (currentByte & 127) << round++ * 7;

			if (round > 5) {
				throw new RuntimeException("VarInt is too big");
			}
		} while ((currentByte & 128) == 128);

		return number;
	}

	public static void writeVarInt(ByteBuf buffer, int number) {
		while ((number & -128) != 0) {
			buffer.writeByte(number & 127 | 128);
			number >>>= 7;
		}

		buffer.writeByte(number);
	}

	public static int getVarIntLength(int number) {
		if ((number & 0xFFFFFF80) == 0) {
			return 1;
		} else if ((number & 0xFFFFC000) == 0) {
			return 2;
		} else if ((number & 0xFFE00000) == 0) {
			return 3;
		} else if ((number & 0xF0000000) == 0) {
			return 4;
		}
		return 5;
	}
	
	public static String readString(ByteBuf buffer) throws IOException {
		int lenght = buffer.readInt();
		
		if(lenght == -1) {
			return "";
		}
		byte[] data = new byte[lenght];
		buffer.readBytes(data);
		
		String var3 = new String(data, Charset.forName("UTF-8"));
		return var3;
	}
	
	public static void writeString(ByteBuf buffer, String string) throws IOException {
		if(string == null || string.length() == 0) {
			buffer.writeInt(-1);
			return;
		}
		byte[] data = string.getBytes(Charset.forName("UTF-8"));
		
		if (data.length > 32767) {
			throw new IOException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
		} else {
			buffer.writeInt(data.length);
			buffer.writeBytes(data);
		}
	}
}
