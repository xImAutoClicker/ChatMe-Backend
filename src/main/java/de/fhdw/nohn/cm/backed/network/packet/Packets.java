package de.fhdw.nohn.cm.backed.network.packet;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import de.fhdw.nohn.cm.backed.network.packet.in.InPacket;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInLogin;

public class Packets {
	
	private static final Map<Integer, Class<? extends InPacket>> packets = Maps.newHashMap();
	
	static {
		registerPacket(0, PacketInLogin.class);
	}
	
	public static InPacket newPacketInstance(final int packetId) throws Exception {
		Class<? extends InPacket> packetClass = Packets.getPacketClass(packetId);
		
		Preconditions.checkNotNull(packetClass);
		
		return packetClass.getDeclaredConstructor().newInstance();
	}
	
	public static void registerPacket(final int packetId, final Class<? extends InPacket> packetClass) {
		packets.put(packetId, packetClass);
	}
	
	public static Class<? extends InPacket> getPacketClass(final int packetId) {
		return packets.get(packetId);
	}
}
