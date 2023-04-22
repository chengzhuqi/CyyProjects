package com.intrusion.cyy.WebDetectionSystem.service;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.Packet;

public interface Pcap4jGetPacketService {
    void sendPacket(String name) throws PcapNativeException, NotOpenException;
}
