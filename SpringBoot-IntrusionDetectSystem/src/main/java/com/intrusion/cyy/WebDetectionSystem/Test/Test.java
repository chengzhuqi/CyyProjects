package com.intrusion.cyy.WebDetectionSystem.Test;


import com.intrusion.cyy.WebDetectionSystem.Handler.PacketHandler;
import com.intrusion.cyy.WebDetectionSystem.utils.PacketUtils.NetworkInformation;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.*;



public class Test {
    public static void main(String[] args) throws PcapNativeException, IOException, NotOpenException {
        List<PcapNetworkInterface> networkInterfacesList = NetworkInformation.getNetworkInterfacesList();
        PcapNetworkInterface networkInterface = networkInterfacesList.get(0);
        PcapHandle handle = networkInterface.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10 * 1000);
        while (true){
            Packet packet = handle.getNextPacket();
            if (packet != null){

            }

        }


    }
}
