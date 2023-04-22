package com.intrusion.cyy.WebDetectionSystem.service.impl;


import com.intrusion.cyy.WebDetectionSystem.service.Pcap4jGetPacketService;
import org.pcap4j.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Pcap4jGetPacketServiceimpl implements Pcap4jGetPacketService {

    @Override
    public void sendPacket(String name) throws PcapNativeException, NotOpenException {

    }
}
